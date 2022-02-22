package io.github.ootsuha.hachi.core.command.request.slash;

import io.github.ootsuha.hachi.core.command.request.*;
import lombok.*;
import lombok.experimental.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.command.*;
import net.dv8tion.jda.api.interactions.components.*;
import net.dv8tion.jda.api.requests.restaction.interactions.*;

import java.util.*;
import java.util.function.*;

/**
 * Represents reply actions for commands requested from a slash command.
 */
@EqualsAndHashCode
@Accessors(chain = true)
public final class HachiSlashCommandReplyAction implements HachiCommandReplyAction {
    private final SlashCommandInteractionEvent event;
    private final boolean isEmbed;
    @Setter
    private List<ActionRow> actionRows;
    private String content;
    private MessageEmbed embed;
    private boolean ephemeral;

    /**
     * Create a reply action with a string.
     *
     * @param event   slash command to reply to
     * @param content reply text
     */
    public HachiSlashCommandReplyAction(final SlashCommandInteractionEvent event, final String content) {
        this.event = event;
        this.isEmbed = false;
        this.content = content;
        this.ephemeral = false;
        this.actionRows = new ArrayList<>();
    }

    /**
     * Create a reply action with an embed.
     *
     * @param event slash command to reply to
     * @param embed reply text
     */
    public HachiSlashCommandReplyAction(final SlashCommandInteractionEvent event, final MessageEmbed embed) {
        this.event = event;
        this.isEmbed = true;
        this.embed = embed;
        this.ephemeral = false;
        this.actionRows = new ArrayList<>();
    }

    private ReplyCallbackAction reply() {
        if (this.isEmbed) {
            return this.event.replyEmbeds(this.embed).setEphemeral(this.ephemeral).addActionRows(this.actionRows);
        }
        return this.event.reply(this.content).setEphemeral(this.ephemeral).addActionRows(this.actionRows);
    }

    @Override
    public void queue() {
        reply().queue();
    }

    @Override
    public void queue(final Consumer<HachiCommandReply> callback) {
        reply().queue(e -> callback.accept(new HachiSlashCommandReply(e)));
    }

    @Override
    public HachiCommandReply complete() {
        return new HachiSlashCommandReply(reply().complete());
    }

    @Override
    public HachiCommandReplyAction setEphemeral() {
        this.ephemeral = true;
        return this;
    }
}
