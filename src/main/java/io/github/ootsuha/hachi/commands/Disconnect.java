package io.github.ootsuha.hachi.commands;

import edu.umd.cs.findbugs.annotations.*;
import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.command.request.*;
import org.springframework.stereotype.*;

@Component
public final class Disconnect extends HachiCommandImpl {
    public Disconnect() {
        super("disconnect", "Disconnect Hachi from Discord.");
        setAliases("dc");
    }

    @SuppressFBWarnings("DM_EXIT")
    @Override
    public void run(final HachiCommandRequest r) {
        r.reply("cya").complete();
        System.exit(0);
    }
}
