package io.github.ootsuha.hachi.command.usable;

import io.github.ootsuha.hachi.command.*;
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
