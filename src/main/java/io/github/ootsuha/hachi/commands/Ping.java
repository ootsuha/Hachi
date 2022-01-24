package io.github.ootsuha.hachi.commands;

import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.command.request.*;
import org.springframework.stereotype.*;

@Component
public final class Ping extends HachiStringCommand {
    public Ping() {
        super("ping", "Pings the user.");
    }

    @Override protected String output(final HachiCommandRequest r) {
        return r.getUser().getAsMention();
    }
}
