package io.github.ootsuha.hachi.listener;

import io.github.ootsuha.hachi.command.HachiCommand;
import io.github.ootsuha.hachi.command.HachiCommandLoader;
import io.github.ootsuha.hachi.command.HachiCommandRequest;
import io.github.ootsuha.hachi.parser.Parser;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public final class MessageListener extends ListenerAdapter {
    @Override public void onMessageReceived(@NotNull final MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }
        HachiCommandRequest r = Parser.parse(event.getMessage().getContentRaw());
        if (r != null) {
            HachiCommand c = HachiCommandLoader.getCommand(r.getName());
            assert c != null;
            r.setMessage(event.getMessage());
            r.setChannel(event.getTextChannel());
            r.setUser(event.getAuthor());
            c.run(r);
        }
    }
}
