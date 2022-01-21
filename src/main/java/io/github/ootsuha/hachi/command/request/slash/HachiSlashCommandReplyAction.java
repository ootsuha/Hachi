package io.github.ootsuha.hachi.command.request.slash;

import io.github.ootsuha.hachi.command.request.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.*;
import net.dv8tion.jda.api.requests.restaction.interactions.*;

/**
 * Represents reply actions for commands requested from a slash command.
 */
public final class HachiSlashCommandReplyAction implements HachiCommandReplyAction {
    private final SlashCommandEvent event;
    private final boolean isEmbed;
    private String content;
    private MessageEmbed embed;
    private boolean ephemeral;

    /**
     * Create a reply action with a string.
     *
     * @param event   slash command to reply to
     * @param content reply text
     */
    public HachiSlashCommandReplyAction(final SlashCommandEvent event, final String content) {
        this.event = event;
        this.isEmbed = false;
        this.content = content;
        this.ephemeral = false;
    }

    /**
     * Create a reply action with an embed.
     *
     * @param event slash command to reply to
     * @param embed reply text
     */
    public HachiSlashCommandReplyAction(final SlashCommandEvent event, final MessageEmbed embed) {
        this.event = event;
        this.isEmbed = true;
        this.embed = embed;
        this.ephemeral = false;
    }

    private ReplyAction reply() {
        if (this.isEmbed) {
            return this.event.replyEmbeds(this.embed).setEphemeral(this.ephemeral);
        }
        return this.event.reply(this.content).setEphemeral(this.ephemeral);
    }

    @Override public void queue() {
        reply().queue();
    }

    @Override public void complete() {
        reply().complete();
    }

    @Override public HachiCommandReplyAction setEphemeral() {
        this.ephemeral = true;
        return this;
    }
}
