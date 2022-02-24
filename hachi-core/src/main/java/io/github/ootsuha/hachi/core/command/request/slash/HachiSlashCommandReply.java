package io.github.ootsuha.hachi.core.command.request.slash;

import io.github.ootsuha.hachi.core.command.request.*;
import lombok.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.interactions.*;
import net.dv8tion.jda.api.requests.*;

/**
 * Wraps an <code>InteractionHook</code>.
 */
@RequiredArgsConstructor
public final class HachiSlashCommandReply implements HachiCommandReply {
    private final InteractionHook hook;

    @Override
    public RestAction<Message> removeComponents() {
        return this.hook.editOriginalComponents();
    }

    @Override
    public RestAction<Message> editContent(final String content) {
        return this.hook.editOriginal(content);
    }

    @Override
    public RestAction<Message> editEmbed(final MessageEmbed embed) {
        return this.hook.editOriginalEmbeds(embed);
    }

    @Override
    public String getId() {
        return this.hook.retrieveOriginal().complete().getId();
    }
}
