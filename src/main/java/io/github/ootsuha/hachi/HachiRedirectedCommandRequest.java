package io.github.ootsuha.hachi;

import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.command.request.*;
import lombok.*;
import net.dv8tion.jda.api.entities.*;

/**
 * Command request that redirects its response to a different request.
 */
@AllArgsConstructor
public final class HachiRedirectedCommandRequest implements HachiCommandRequest {
    private final HachiCommandRequest target;
    @Getter
    private final User user;
    @Getter
    private final HachiCommand command;
    @Getter
    private final HachiCommandOptions options;

    @Override
    public TextChannel getChannel() {
        return this.target.getChannel();
    }

    @Override
    public void deferReply() {
        this.target.deferReply();
    }

    @Override
    public HachiCommandReplyAction reply(final String content) {
        return this.target.reply(content);
    }

    @Override
    public HachiCommandReplyAction replyEmbed(final MessageEmbed embed) {
        return this.target.replyEmbed(embed);
    }
}
