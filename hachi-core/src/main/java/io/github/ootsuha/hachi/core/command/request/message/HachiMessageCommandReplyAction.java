package io.github.ootsuha.hachi.core.command.request.message;

import io.github.ootsuha.hachi.core.command.request.*;
import lombok.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.interactions.components.*;
import net.dv8tion.jda.api.requests.restaction.*;

import java.io.*;
import java.util.*;
import java.util.function.*;

/**
 * Represents reply actions for commands requested from a message.
 */
@EqualsAndHashCode
public final class HachiMessageCommandReplyAction implements HachiCommandReplyAction {
    private MessageAction action;
    private boolean ephemeral;

    /**
     * Create a reply action with a string.
     *
     * @param message message to reply to
     * @param content reply text
     */
    public HachiMessageCommandReplyAction(final Message message, final String content) {
        this.action = message.reply(content);
        this.ephemeral = false;
    }

    /**
     * Create a reply action with an embed.
     *
     * @param message message to reply to
     * @param embed   message embed
     */
    public HachiMessageCommandReplyAction(final Message message, final MessageEmbed embed) {
        this.action = message.replyEmbeds(embed);
        this.ephemeral = false;
    }

    public HachiMessageCommandReplyAction(final Message message, final InputStream data, final String name) {
        this.action = message.reply(data, name);
    }

    @Override
    public void queue() {
        this.action.queue();
    }

    @Override
    public void queue(final Consumer<HachiCommandReply> callback) {
        this.action.queue(e -> callback.accept(new HachiMessageCommandReply(e)));
    }

    @Override
    public HachiCommandReply complete() {
        return new HachiMessageCommandReply(this.action.complete());
    }

    @Override
    public HachiCommandReplyAction setEphemeral() {
        this.ephemeral = true;
        return this;
    }

    @Override
    public HachiCommandReplyAction addFile(final InputStream data, final String name) {
        this.action = this.action.addFile(data, name);
        return this;
    }

    @Override
    public HachiCommandReplyAction setActionRows(final List<ActionRow> rows) {
        this.action = this.action.setActionRows(rows);
        return this;
    }

    @Override
    public HachiCommandReplyAction setEmbed(final MessageEmbed embed) {
        this.action = this.action.setEmbeds(embed);
        return this;
    }
}
