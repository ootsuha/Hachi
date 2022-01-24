package io.github.ootsuha.hachi.core.listener;

import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.command.request.slash.*;
import net.dv8tion.jda.api.events.interaction.*;
import net.dv8tion.jda.api.hooks.*;
import org.jetbrains.annotations.*;

/**
 * Responds to <code>SlashCommandEvent</code>s.
 */
public final class SlashCommandListener extends ListenerAdapter {
    private final HachiCommandLoader loader;

    public SlashCommandListener(final HachiCommandLoader loader) {
        this.loader = loader;
    }

    @Override public void onSlashCommand(@NotNull final SlashCommandEvent event) {
        HachiCommand command = this.loader.getCommand(event.getName());
        if (command != null) {
            command.run(new HachiSlashCommandRequest(event, command));
        } else {
            event.reply(String.format("Command `%s` does not exist...", event.getName())).setEphemeral(true).queue();
        }
    }
}
