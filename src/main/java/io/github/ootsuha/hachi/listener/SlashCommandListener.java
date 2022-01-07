package io.github.ootsuha.hachi.listener;

import io.github.ootsuha.hachi.command.HachiCommand;
import io.github.ootsuha.hachi.command.HachiCommandLoader;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public final class SlashCommandListener extends ListenerAdapter {
    @Override public void onSlashCommand(@NotNull final SlashCommandEvent event) {
        HachiCommand command = HachiCommandLoader.getCommand(event.getName());
        if (command != null) {
            command.run(event);
        }
    }
}
