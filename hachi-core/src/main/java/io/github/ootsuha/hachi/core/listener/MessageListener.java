package io.github.ootsuha.hachi.core.listener;

import io.github.ootsuha.hachi.core.command.request.*;
import io.github.ootsuha.hachi.core.parser.*;
import lombok.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.*;
import net.dv8tion.jda.api.events.message.*;
import net.dv8tion.jda.api.hooks.*;

import javax.annotation.*;
import java.io.*;

/**
 * Responds to <code>MessageReceivedEvent</code>s.
 */
@AllArgsConstructor
public final class MessageListener implements EventListener {
    private final Parser parser;

    @Override
    public void onEvent(@Nonnull final GenericEvent event) {
        if (event instanceof MessageReceivedEvent mEvent) {
            if (mEvent.getAuthor().isBot()) {
                return;
            }
            System.out.println(mEvent.getMessage().getContentRaw());
            try {
                HachiCommandRequest r = this.parser.parse(mEvent.getMessage());
                if (r != null) {
                    r.fulfill();
                }
            } catch (Throwable e) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                mEvent.getMessage().reply(sw.toString().substring(0, Message.MAX_CONTENT_LENGTH)).queue();
            }
        }
    }
}
