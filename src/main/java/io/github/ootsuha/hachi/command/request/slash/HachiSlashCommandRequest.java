package io.github.ootsuha.hachi.command.request.slash;

import io.github.ootsuha.hachi.command.*;
import io.github.ootsuha.hachi.command.request.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.*;

/**
 * Represents user command requests that come from a slash command.
 */
public final class HachiSlashCommandRequest extends HachiCommandRequestAbstract {
    private final SlashCommandEvent event;

    public HachiSlashCommandRequest(final SlashCommandEvent e, final HachiCommand c) {
        super(c, new HachiSlashCommandOptions(e));
        this.event = e;
    }

    @Override public User getUser() {
        return this.event.getUser();
    }

    @Override public TextChannel getChannel() {
        return this.event.getTextChannel();
    }

    @Override public void deferReply() {
        this.event.deferReply().queue();
    }

    @Override public HachiCommandReplyAction reply(final String content) {
        return new HachiSlashCommandReplyAction(this.event, content);
    }

    @Override public HachiCommandReplyAction replyEmbed(final MessageEmbed embed) {
        return new HachiSlashCommandReplyAction(this.event, embed);
    }
}
