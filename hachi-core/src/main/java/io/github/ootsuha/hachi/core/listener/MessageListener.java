package io.github.ootsuha.hachi.core.listener;

import io.github.ootsuha.hachi.core.command.request.*;
import io.github.ootsuha.hachi.core.parser.*;
import lombok.*;
import net.dv8tion.jda.api.events.message.*;
import net.dv8tion.jda.api.hooks.*;
import org.jetbrains.annotations.*;

/**
 * Responds to <code>MessageReceivedEvent</code>s.
 */
@AllArgsConstructor
public final class MessageListener extends ListenerAdapter {
    private final Parser parser;

    @Override public void onMessageReceived(@NotNull final MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }
        HachiCommandRequest r = this.parser.parse(event.getMessage());
        if (r != null) {
            r.fulfill();
        }
    }
}
