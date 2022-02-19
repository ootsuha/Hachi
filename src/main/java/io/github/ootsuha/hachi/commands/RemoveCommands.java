package io.github.ootsuha.hachi.commands;

import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.command.request.*;
import org.springframework.stereotype.*;

@Component
public final class RemoveCommands extends HachiCommandImpl {
    public RemoveCommands() {
        super("remove-commands", "Removes all guild commands.");
        setAliases("rc");
    }

    @Override
    public void run(final HachiCommandRequest r) {
        r.getChannel().getGuild().updateCommands().queue();
        r.reply("Removed commands.").setEphemeral().queue();
    }
}
