package io.github.ootsuha.hachi.command.usable;

import io.github.ootsuha.hachi.command.HachiStringCommand;

public final class Ping extends HachiStringCommand {
    /**
     * Initialize command settings.
     */
    public Ping() {
        setDescription("pong");
    }
    @Override protected String output() {
        return "pong";
    }
}
