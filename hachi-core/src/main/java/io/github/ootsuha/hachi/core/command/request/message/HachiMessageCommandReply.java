package io.github.ootsuha.hachi.core.command.request.message;

import io.github.ootsuha.hachi.core.command.request.*;
import lombok.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.requests.*;

/**
 * Wraps a <code>Message</code>.
 */
@RequiredArgsConstructor
public final class HachiMessageCommandReply implements HachiCommandReply {
    private final Message message;

    @Override
    public RestAction<Message> removeComponents() {
        return this.message.editMessageComponents();
    }

    @Override
    public RestAction<Message> editContent(final String content) {
        return this.message.editMessage(content);
    }

    @Override
    public RestAction<Message> editEmbed(final MessageEmbed embed) {
        return this.message.editMessageEmbeds(embed);
    }

    @Override
    public String getId() {
        return this.message.getId();
    }
}
