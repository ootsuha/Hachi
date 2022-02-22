package io.github.ootsuha.hachi.core.command.request.message;

import io.github.ootsuha.hachi.core.command.request.*;
import lombok.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.requests.*;

@RequiredArgsConstructor
public final class HachiMessageCommandReply implements HachiCommandReply {
    private final Message message;

    @Override
    public RestAction<Message> removeComponents() {
        return this.message.editMessageComponents();
    }

    @Override
    public void editContent(final String content) {
        this.message.editMessage(content).queue();
    }

    @Override
    public void editEmbed(final MessageEmbed embed) {
        this.message.editMessageEmbeds(embed).queue();
    }

    @Override
    public String getId() {
        return this.message.getId();
    }
}
