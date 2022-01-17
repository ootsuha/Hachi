package io.github.ootsuha.hachi.command.usable;

import io.github.ootsuha.hachi.command.*;

public final class UpdateCommands extends HachiCommandImpl {
    public UpdateCommands() {
        super("updatecommands", "Updates guild commands.");
        setAliases("uc");
    }

    @Override public void run(final HachiCommandRequest r) {
        HachiCommandLoader.createGuildCommands(r.getChannel().getGuild());
        r.replyEphemeral("Updated commands.");
    }
}
