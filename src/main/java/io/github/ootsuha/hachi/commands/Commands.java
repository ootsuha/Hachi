package io.github.ootsuha.hachi.commands;

import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.command.request.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.stream.*;

@Component
public final class Commands extends HachiCommandImpl implements HachiStringCommand {
    private final HachiCommandLoader loader;

    @Autowired
    public Commands(final HachiCommandLoader loader) {
        super("commands", "Lists all commands.");
        this.loader = loader;
    }

    @Override
    public String output(final HachiCommandRequest r) {
        return this.loader.getCommands().stream().map(e -> '`' + e.getName() + '`').collect(Collectors.joining(", "));
    }
}
