package io.github.ootsuha.hachi.commands;

import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.command.request.*;
import io.github.ootsuha.hachi.core.command.specialized.*;
import org.springframework.stereotype.*;

@Component
public final class Ping extends HachiCommandImpl implements HachiStringCommand {
    public Ping() {
        super("ping", "Pings the user.");
    }

    @Override
    public String output(final HachiCommandRequest r) {
        return r.getUser().getAsMention();
    }
}
