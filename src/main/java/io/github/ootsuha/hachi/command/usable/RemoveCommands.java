package io.github.ootsuha.hachi.command.usable;

import io.github.ootsuha.hachi.command.*;
import io.github.ootsuha.hachi.command.request.*;
import org.springframework.stereotype.*;

@Component
public final class RemoveCommands extends HachiCommandImpl {
    public RemoveCommands() {
        super("removecommands", "Removes all guild commands.");
        setAliases("rc");
    }

    @Override public void run(final HachiCommandRequest r) {
        r.getChannel().getGuild().updateCommands().queue();
        r.reply("Removed commands.").setEphemeral().queue();
    }
}
