package io.github.ootsuha.hachi.core.command.request.message;

import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.command.request.*;
import net.dv8tion.jda.api.entities.*;

import java.util.*;

/**
 * Represents user command requests that come from a message.
 */
public final class HachiMessageCommandRequest extends HachiCommandRequestAbstract {
    private final Message message;

    /**
     * Constructor.
     *
     * @param m message
     * @param c hachi command
     * @param o hachi command options
     */
    public HachiMessageCommandRequest(final Message m, final HachiCommand c, final HachiCommandOptions o) {
        super(c, o);
        this.message = m;
    }

    @Override public User getUser() {
        return this.message.getAuthor();
    }

    @Override public TextChannel getChannel() {
        return this.message.getTextChannel();
    }

    @Override public void deferReply() {
        this.message.getTextChannel().sendTyping().queue();
    }

    @Override public HachiCommandReplyAction reply(final String content) {
        return new HachiMessageCommandReplyAction(this.message, content);
    }

    @Override public HachiCommandReplyAction replyEmbed(final MessageEmbed embed) {
        return new HachiMessageCommandReplyAction(this.message, embed);
    }

    @Override public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HachiMessageCommandRequest that)) {
            return false;
        }
        return Objects.equals(this.message, that.message) && Objects.equals(this.getRequestedCommand(),
                that.getRequestedCommand()) && Objects.equals(this.getOptions(), that.getOptions());
    }

    @Override public int hashCode() {
        return Objects.hash(this.message, this.getRequestedCommand(), this.getOptions());
    }
}
