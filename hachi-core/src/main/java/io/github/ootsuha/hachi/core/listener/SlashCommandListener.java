package io.github.ootsuha.hachi.core.listener;

import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.command.loader.*;
import io.github.ootsuha.hachi.core.command.request.slash.*;
import lombok.*;
import net.dv8tion.jda.api.events.*;
import net.dv8tion.jda.api.events.interaction.command.*;
import net.dv8tion.jda.api.hooks.*;

import javax.annotation.*;

/**
 * Responds to <code>SlashCommandEvent</code>s.
 */
@AllArgsConstructor
public final class SlashCommandListener implements EventListener {
    private final HachiCommandLoader loader;

    @Override
    public void onEvent(@Nonnull final GenericEvent event) {
        if (event instanceof SlashCommandInteractionEvent sEvent) {
            HachiCommand command = this.loader.getCommand(sEvent);
            if (command != null) {
                new HachiSlashCommandRequest(sEvent, command).fulfill();
            } else {
                sEvent.reply(String.format("Command `%s` does not exist...", sEvent.getName())).setEphemeral(true)
                        .queue();
            }
        }
    }
}
