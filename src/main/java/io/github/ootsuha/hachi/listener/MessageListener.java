package io.github.ootsuha.hachi.listener;

import io.github.ootsuha.hachi.command.HachiCommandLoader;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public final class MessageListener extends ListenerAdapter {
    @Override public void onMessageReceived(@NotNull final MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }
        HachiCommandLoader.createGuildCommands(event.getGuild());
    }
}
