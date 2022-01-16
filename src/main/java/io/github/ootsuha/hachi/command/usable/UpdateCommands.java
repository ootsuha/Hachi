package io.github.ootsuha.hachi.command.usable;

import io.github.ootsuha.hachi.command.HachiCommandImpl;
import io.github.ootsuha.hachi.command.HachiCommandLoader;
import io.github.ootsuha.hachi.command.HachiCommandRequest;

public final class UpdateCommands extends HachiCommandImpl {
    /**
     * Initialize command settings.
     */
    public UpdateCommands() {
        super("updatecommands", "Updates guild commands.");
    }

    @Override public void run(final HachiCommandRequest r) {
        HachiCommandLoader.createGuildCommands(r.getChannel().getGuild());
        r.replyEphemeral("Updated commands.");
    }
}
