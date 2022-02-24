package io.github.ootsuha.hachi.core.command.request.slash;

import io.github.ootsuha.hachi.core.command.request.*;
import lombok.*;
import lombok.experimental.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.command.*;
import net.dv8tion.jda.api.interactions.components.*;
import net.dv8tion.jda.api.requests.restaction.interactions.*;

import java.io.*;
import java.util.*;
import java.util.function.*;

/**
 * Wraps <code>ReplyCallbackAction</code>s.
 */
@EqualsAndHashCode
@Accessors(chain = true)
public final class HachiSlashCommandReplyAction implements HachiCommandReplyAction {
    private ReplyCallbackAction action;

    public HachiSlashCommandReplyAction(final SlashCommandInteractionEvent event, final String content) {
        this.action = event.reply(content);
    }

    public HachiSlashCommandReplyAction(final SlashCommandInteractionEvent event, final MessageEmbed embed) {
        this.action = event.replyEmbeds(embed);
    }

    public HachiSlashCommandReplyAction(final SlashCommandInteractionEvent event, final InputStream data,
            final String name) {
        this.action = event.replyFile(data, name);
    }

    @Override
    public void queue() {
        this.action.queue();
    }

    @Override
    public void queue(final Consumer<HachiCommandReply> callback) {
        this.action.queue(e -> callback.accept(new HachiSlashCommandReply(e)));
    }

    @Override
    public HachiCommandReply complete() {
        return new HachiSlashCommandReply(this.action.complete());
    }

    @Override
    public HachiCommandReplyAction setEphemeral() {
        this.action = this.action.setEphemeral(true);
        return this;
    }

    @Override
    public HachiCommandReplyAction setActionRows(final List<ActionRow> rows) {
        this.action = this.action.addActionRows(rows);
        return this;
    }

    @Override
    public HachiCommandReplyAction addFile(final InputStream data, final String name) {
        this.action = this.action.addFile(data, name);
        return this;
    }

    @Override
    public HachiCommandReplyAction setEmbed(final MessageEmbed embed) {
        this.action = this.action.addEmbeds(embed);
        return this;
    }
}
