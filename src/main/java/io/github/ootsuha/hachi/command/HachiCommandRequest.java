package io.github.ootsuha.hachi.command;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a user's request for a command.
 */
public final class HachiCommandRequest {
    /**
     * Name of the command.
     */
    private final String name;
    /**
     * Holds the user's options.
     */
    private final Map<String, Object> options;
    /**
     * User that the request belongs to.
     */
    private User user;
    /**
     * Channel the request was sent in.
     */
    private TextChannel channel;
    /**
     * Message that requested the command.
     */
    @Nullable private Message message;
    /**
     * Slash command that requested the command.
     */
    @Nullable private SlashCommandEvent event;

    /**
     * Constructor.
     *
     * @param name    command name
     * @param options options map
     */
    public HachiCommandRequest(final String name, final Map<String, Object> options) {
        this.name = name;
        this.options = new HashMap<>(options);
    }

    /**
     * Convert a <code>SlashCommandEvent</code> into a <code>HachiCommandRequest</code>.
     *
     * @param e slash command event
     */
    public HachiCommandRequest(final SlashCommandEvent e) {
        this.name = e.getName();
        this.options = new HashMap<>();
        this.user = e.getUser();
        this.channel = e.getTextChannel();
        this.event = e;
        for (OptionMapping m : e.getOptions()) {
            options.put(m.getName(), switch (m.getType()) {
                case STRING -> m.getAsString();
                case INTEGER -> m.getAsLong();
                case BOOLEAN -> m.getAsBoolean();
                case NUMBER -> m.getAsDouble();
                default -> null;
            });
        }
    }

    /**
     * Returns <code>this.name</code>.
     *
     * @return this.name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns <code>this.user</code>.
     *
     * @return this.user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets <code>this.user</code>.
     *
     * @param user new value
     */
    public void setUser(final User user) {
        this.user = user;
    }

    /**
     * Returns <code>this.channel</code>.
     *
     * @return this.channel
     */
    public TextChannel getChannel() {
        return channel;
    }

    /**
     * Sets <code>this.channel</code>.
     *
     * @param channel new value
     */
    public void setChannel(final TextChannel channel) {
        this.channel = channel;
    }

    /**
     * Returns <code>this.message</code>.
     *
     * @return this.message
     */
    @Nullable public Message getMessage() {
        return message;
    }

    /**
     * Sets <code>this.message</code>.
     *
     * @param message new value
     */
    public void setMessage(@Nullable final Message message) {
        this.message = message;
    }

    /**
     * Returns <code>this.event</code>.
     *
     * @return this.event
     */
    @Nullable public SlashCommandEvent getEvent() {
        return event;
    }

    /**
     * Sets <code>this.event</code>.
     *
     * @param event new value
     */
    public void setEvent(@Nullable final SlashCommandEvent event) {
        this.event = event;
    }

    /**
     * Gets the option with name <code>optionName</code> as a String, or null if <code>optionName</code> does not
     * exist or is not a String.
     *
     * @param optionName name
     * @return string, or null of no string option of that name exists
     */
    @Nullable public String getString(final String optionName) {
        if (this.options.get(optionName) instanceof String) {
            return (String) this.options.get(optionName);
        }
        return null;
    }

    /**
     * Gets the option with name <code>optionName</code> as a Boolean, or null if <code>optionName</code> does not
     * exist or is not a Boolean.
     *
     * @param optionName name
     * @return boolean, or null of no string option of that name exists
     */
    @Nullable public Boolean getBoolean(final String optionName) {
        if (this.options.get(optionName) instanceof Boolean) {
            return (Boolean) this.options.get(optionName);
        }
        return null;
    }

    /**
     * Gets the option with name <code>optionName</code> as a Double, or null if <code>optionName</code> does not
     * exist or is not a Double.
     *
     * @param optionName name
     * @return double, or null of no string option of that name exists
     */
    @Nullable public Double getNumber(final String optionName) {
        if (this.options.get(optionName) instanceof Double) {
            return (Double) this.options.get(optionName);
        }
        return null;
    }

    /**
     * Gets the option with name <code>optionName</code> as an Integer, or null if <code>optionName</code> does not
     * exist or is not an Integer.
     *
     * @param optionName name
     * @return integer, or null of no string option of that name exists
     */
    @Nullable public Integer getInteger(final String optionName) {
        if (this.options.get(optionName) instanceof Integer) {
            return (Integer) this.options.get(optionName);
        }
        return null;
    }

    @Override public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HachiCommandRequest that = (HachiCommandRequest) o;
        return Objects.equals(name, that.name) && Objects.equals(options, that.options);
    }

    @Override public int hashCode() {
        return Objects.hash(name, options);
    }

    /**
     * Defer reply for slash command interactions, does nothing when the request comes from a message.
     */
    public void deferReply() {
        if (this.event != null) {
            this.event.deferReply().complete();
        }
    }

    /**
     * Reply to the request.
     *
     * @param content string
     */
    public void reply(final String content) {
        if (this.event != null) {
            this.event.reply(content).complete();
        } else if (this.message != null) {
            this.message.reply(content).complete();
        }
    }

    /**
     * Reply to the request, with the reply being ephemeral if the request is from a slash command.
     *
     * @param content string
     */
    public void replyEphemeral(final String content) {
        if (this.event != null) {
            this.event.reply(content).setEphemeral(true).complete();
        } else if (this.message != null) {
            this.message.reply(content).complete();
        }
    }

    /**
     * Reply to the request.
     *
     * @param embed embed
     */
    public void replyEmbed(final MessageEmbed embed) {
        if (this.event != null) {
            this.event.replyEmbeds(embed).complete();
        } else if (this.message != null) {
            this.message.replyEmbeds(embed).complete();
        }
    }

    /**
     * Reply to the request, with the reply being ephemeral if the request is from a slash command.
     *
     * @param embed embed
     */
    public void replyEmbedEphemeral(final MessageEmbed embed) {
        if (this.event != null) {
            this.event.replyEmbeds(embed).setEphemeral(true).complete();
        } else if (this.message != null) {
            this.message.replyEmbeds(embed).complete();
        }
    }
}
