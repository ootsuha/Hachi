package io.github.ootsuha.hachi.core.listener;

import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.command.request.slash.*;
import lombok.*;
import net.dv8tion.jda.api.events.interaction.*;
import net.dv8tion.jda.api.hooks.*;
import org.jetbrains.annotations.*;

/**
 * Responds to <code>SlashCommandEvent</code>s.
 */
@AllArgsConstructor
public final class SlashCommandListener extends ListenerAdapter {
    private final HachiCommandLoader loader;

    @Override public void onSlashCommand(@NotNull final SlashCommandEvent event) {
        HachiCommand command = this.loader.getCommand(event.getName());
        if (command != null) {
            new HachiSlashCommandRequest(event, command).fulfill();
        } else {
            event.reply(String.format("Command `%s` does not exist...", event.getName())).setEphemeral(true).queue();
        }
    }
}
