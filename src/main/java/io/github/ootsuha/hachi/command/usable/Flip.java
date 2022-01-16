package io.github.ootsuha.hachi.command.usable;

import io.github.ootsuha.hachi.Utility;
import io.github.ootsuha.hachi.command.HachiCommandRequest;
import io.github.ootsuha.hachi.command.HachiStringCommand;
import org.jetbrains.annotations.NotNull;

public final class Flip extends HachiStringCommand {
    /**
     * Initialize command settings.
     */
    public Flip() {
        super("flip", "Flips a coin.");
    }

    @NotNull @Override protected String output(final HachiCommandRequest r) {
        return Utility.randInt(0, 2) == 0 ? "Heads" : "Tails";
    }
}
