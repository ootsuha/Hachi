package io.github.ootsuha.hachi.command.usable;

import io.github.ootsuha.hachi.command.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
public final class UpdateCommands extends HachiCommandImpl {
    /**
     * List of all commands.
     */
    private List<HachiCommand> commands;

    public UpdateCommands() {
        super("updatecommands", "Updates guild commands.");
        setAliases("uc");
    }

    @Autowired private void setCommands(final List<HachiCommand> commands) {
        this.commands = commands;
    }

    @Override public void run(final HachiCommandRequest r) {
        commands.forEach(e -> r.getChannel().getGuild().upsertCommand(e.getCommandData()).queue());
        r.replyEphemeral("Updated commands.");
    }
}
