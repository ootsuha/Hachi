package io.github.ootsuha.hachi.listener;

import io.github.ootsuha.hachi.command.*;
import net.dv8tion.jda.api.events.interaction.*;
import net.dv8tion.jda.api.hooks.*;
import org.jetbrains.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * Responds to <code>SlashCommandEvent</code>s.
 */
@Component
public final class SlashCommandListener extends ListenerAdapter {
    private HachiCommandLoader loader;

    @Autowired private void setLoader(final HachiCommandLoader loader) {
        this.loader = loader;
    }

    @Override public void onSlashCommand(@NotNull final SlashCommandEvent event) {
        HachiCommand command = this.loader.getCommand(event.getName());
        if (command != null) {
            command.run(new HachiCommandRequest(command, event));
        } else {
            event.reply(String.format("Command `%s` does not exist...", event.getName())).setEphemeral(true).queue();
        }
    }
}
