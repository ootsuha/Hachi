package io.github.ootsuha.hachi.command.usable;

import io.github.ootsuha.hachi.command.*;
import net.dv8tion.jda.api.interactions.commands.*;

public final class Echo extends HachiStringCommand {
    public Echo() {
        super("echo", "Repeats what is given.");
        addOption(OptionType.STRING, "text", "Message to say.", true);
    }

    @Override protected String output(final HachiCommandRequest r) {
        return r.getString("text");
    }
}
