package io.github.ootsuha.hachi.command.usable;

import edu.umd.cs.findbugs.annotations.*;
import io.github.ootsuha.hachi.command.*;
import org.springframework.stereotype.*;

@Component
public final class Disconnect extends HachiCommandImpl {
    public Disconnect() {
        super("disconnect", "Disconnect Hachi from Discord.");
        setAliases("dc");
    }

    @SuppressFBWarnings("DM_EXIT") @Override public void run(final HachiCommandRequest r) {
        r.reply("cya");
        System.exit(0);
    }
}
