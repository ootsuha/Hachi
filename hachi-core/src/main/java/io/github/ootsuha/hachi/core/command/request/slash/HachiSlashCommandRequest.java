package io.github.ootsuha.hachi.core.command.request.slash;

import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.command.request.*;
import lombok.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.*;

/**
 * Represents user command requests that come from a slash command.
 */
@EqualsAndHashCode(callSuper = true)
public final class HachiSlashCommandRequest extends HachiCommandRequestAbstract {
    @Getter
    private final SlashCommandEvent event;

    public HachiSlashCommandRequest(final SlashCommandEvent e, final HachiCommand c) {
        super(c, new HachiSlashCommandOptions(e), e.getUser(), e.getTextChannel());
        this.event = e;
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
