package io.github.ootsuha.hachi.command.usable;

import io.github.ootsuha.hachi.command.HachiCommandImpl;
import io.github.ootsuha.hachi.command.HachiCommandRequest;

public final class Disconnect extends HachiCommandImpl {
    /**
     * Initialize command settings.
     */
    public Disconnect() {
        super("disconnect", "Disconnect Hachi from Discord.");
    }

    @Override public void run(final HachiCommandRequest r) {
        r.reply("cya");
        System.exit(0);
    }
}
