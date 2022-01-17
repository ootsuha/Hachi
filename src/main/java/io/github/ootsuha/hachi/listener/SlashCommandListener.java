package io.github.ootsuha.hachi.listener;

import io.github.ootsuha.hachi.command.*;
import net.dv8tion.jda.api.events.interaction.*;
import net.dv8tion.jda.api.hooks.*;
import org.jetbrains.annotations.*;

/**
 * Responds to <code>SlashCommandEvent</code>s.
 */
public final class SlashCommandListener extends ListenerAdapter {
    @Override public void onSlashCommand(@NotNull final SlashCommandEvent event) {
        HachiCommand command = HachiCommandLoader.getCommand(event.getName());
        if (command != null) {
            command.run(new HachiCommandRequest(command, event));
        }
    }
}
