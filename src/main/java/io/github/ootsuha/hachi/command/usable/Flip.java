package io.github.ootsuha.hachi.command.usable;

import io.github.ootsuha.hachi.command.*;
import io.github.ootsuha.hachi.command.request.*;
import io.github.ootsuha.hachi.utility.*;
import org.springframework.stereotype.*;

@Component
public final class Flip extends HachiStringCommand {
    public Flip() {
        super("flip", "Flips a coin.");
    }

    @Override protected String output(final HachiCommandRequest r) {
        return Utility.randInt(0, 2) == 0 ? "Heads" : "Tails";
    }
}
