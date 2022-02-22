package io.github.ootsuha.hachi.core.command.request.slash;

import io.github.ootsuha.hachi.core.command.request.*;
import lombok.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.interactions.*;
import net.dv8tion.jda.api.requests.*;

@RequiredArgsConstructor
public final class HachiSlashCommandReply implements HachiCommandReply {
    private final InteractionHook hook;

    @Override
    public RestAction<Message> removeComponents() {
        return this.hook.editOriginalComponents();
    }

    @Override
    public void editContent(final String content) {
        this.hook.editOriginal(content).queue();
    }

    @Override
    public void editEmbed(final MessageEmbed embed) {
        this.hook.editOriginalEmbeds(embed).queue();
    }

    @Override
    public String getId() {
        return this.hook.retrieveOriginal().complete().getId();
    }
}
