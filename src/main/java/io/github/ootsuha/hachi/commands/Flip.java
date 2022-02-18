package io.github.ootsuha.hachi.commands;

import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.command.request.*;
import org.springframework.stereotype.*;

@Component
public final class Flip extends HachiCommandImpl implements HachiStringCommand {
    public Flip() {
        super("flip", "Flips a coin.");
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    @Override
    public String output(final HachiCommandRequest r) {
        return Math.random() >= 0.5 ? "Heads" : "Tails";
    }
}
