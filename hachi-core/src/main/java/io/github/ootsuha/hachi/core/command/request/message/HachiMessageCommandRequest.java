package io.github.ootsuha.hachi.core.command.request.message;

import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.command.request.*;
import lombok.*;
import net.dv8tion.jda.api.entities.*;

/**
 * Represents user command requests that come from a message.
 */
@EqualsAndHashCode(callSuper = true)
public final class HachiMessageCommandRequest extends HachiCommandRequestAbstract {
    @Getter
    private final Message message;

    public HachiMessageCommandRequest(final Message m, final HachiCommand c, final HachiCommandOptions o) {
        super(c, o, m.getAuthor(), m.getTextChannel());
        this.message = m;
    }

    @Override
    public void deferReply() {
        this.message.getTextChannel().sendTyping().queue();
    }

    @Override
    public HachiCommandReplyAction reply(final String content) {
        return new HachiMessageCommandReplyAction(this.message, content);
    }

    @Override
    public HachiCommandReplyAction replyEmbed(final MessageEmbed embed) {
        return new HachiMessageCommandReplyAction(this.message, embed);
    }
}
