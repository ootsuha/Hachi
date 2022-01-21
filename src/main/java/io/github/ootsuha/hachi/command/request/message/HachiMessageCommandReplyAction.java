package io.github.ootsuha.hachi.command.request.message;

import io.github.ootsuha.hachi.command.request.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.requests.restaction.*;

/**
 * Represents reply actions for commands requested from a message.
 */
public final class HachiMessageCommandReplyAction implements HachiCommandReplyAction {
    private final Message message;
    private final boolean isEmbed;
    private String content;
    private MessageEmbed embed;
    //private boolean ephemeral;

    /**
     * Create a reply action with a string.
     *
     * @param message message to reply to
     * @param content reply text
     */
    public HachiMessageCommandReplyAction(final Message message, final String content) {
        this.message = message;
        this.isEmbed = false;
        this.content = content;
        //this.ephemeral = false;
    }

    /**
     * Create a reply action with an embed.
     *
     * @param message message to reply to
     * @param embed   message embed
     */
    public HachiMessageCommandReplyAction(final Message message, final MessageEmbed embed) {
        this.message = message;
        this.isEmbed = true;
        this.embed = embed;
        //this.ephemeral = false;
    }

    private MessageAction reply() {
        if (this.isEmbed) {
            return this.message.replyEmbeds(this.embed);
        }
        return this.message.reply(this.content);
    }

    @Override public void queue() {
        reply().queue();
    }

    @Override public void complete() {
        reply().complete();
    }

    @Override public HachiCommandReplyAction setEphemeral() {
        //this.ephemeral = true;
        return this;
    }
}
