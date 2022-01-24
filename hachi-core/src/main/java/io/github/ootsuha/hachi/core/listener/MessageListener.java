package io.github.ootsuha.hachi.core.listener;

import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.command.request.*;
import io.github.ootsuha.hachi.core.parser.*;
import net.dv8tion.jda.api.events.message.*;
import net.dv8tion.jda.api.hooks.*;
import org.jetbrains.annotations.*;

/**
 * Responds to <code>MessageReceivedEvent</code>s.
 */
public final class MessageListener extends ListenerAdapter {
    private final Parser parser;

    public MessageListener(final Parser parser) {
        this.parser = parser;
    }

    @Override public void onMessageReceived(@NotNull final MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }
        HachiCommandRequest r = this.parser.parse(event.getMessage());
        if (r != null) {
            HachiCommand c = r.getRequestedCommand();
            c.run(r);
        }
    }
}
