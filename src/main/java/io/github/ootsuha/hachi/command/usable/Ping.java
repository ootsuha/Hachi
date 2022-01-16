package io.github.ootsuha.hachi.command.usable;

import io.github.ootsuha.hachi.command.HachiCommandRequest;
import io.github.ootsuha.hachi.command.HachiStringCommand;
import org.jetbrains.annotations.NotNull;

public final class Ping extends HachiStringCommand {
    /**
     * Initialize command settings.
     */
    public Ping() {
        super("ping", "pong");
    }

    @Override protected @NotNull String output(final HachiCommandRequest r) {
        return "pong";
    }
}
