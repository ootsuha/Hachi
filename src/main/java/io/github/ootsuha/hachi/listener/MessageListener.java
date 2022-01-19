package io.github.ootsuha.hachi.listener;

import io.github.ootsuha.hachi.command.*;
import io.github.ootsuha.hachi.parser.*;
import net.dv8tion.jda.api.events.message.*;
import net.dv8tion.jda.api.hooks.*;
import org.jetbrains.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * Responds to <code>MessageReceivedEvent</code>s.
 */
@Component
public final class MessageListener extends ListenerAdapter {
    private Parser parser;
    private HachiCommandLoader loader;

    @Autowired private void setLoader(final HachiCommandLoader loader) {
        this.loader = loader;
    }

    @Autowired private void setParser(final Parser parser) {
        this.parser = parser;
    }

    @Override public void onMessageReceived(@NotNull final MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }
        HachiCommandRequest r = this.parser.parse(event.getMessage().getContentRaw());
        if (r != null) {
            HachiCommand c = this.loader.getCommand(r.getName());
            r.setMessage(event.getMessage());
            r.setChannel(event.getTextChannel());
            r.setUser(event.getAuthor());
            assert c != null;
            c.run(r);
        }
    }
}
