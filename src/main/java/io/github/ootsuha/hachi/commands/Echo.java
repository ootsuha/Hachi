package io.github.ootsuha.hachi.commands;

import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.command.request.*;
import io.github.ootsuha.hachi.core.command.specialized.*;
import net.dv8tion.jda.api.interactions.commands.*;
import org.springframework.stereotype.*;

@Component
public final class Echo extends HachiCommandImpl implements HachiStringCommand {
    public Echo() {
        super("echo", "Repeats what is given.");
        addOption(OptionType.STRING, "text", "Message to say.", true);
    }

    @Override
    public String output(final HachiCommandRequest r) {
        return r.getOptions().getString("text");
    }
}
