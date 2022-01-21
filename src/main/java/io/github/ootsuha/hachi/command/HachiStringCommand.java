package io.github.ootsuha.hachi.command;

import io.github.ootsuha.hachi.command.request.*;

/**
 * A <code>HachiCommand</code> that replies with a string.
 */
public abstract class HachiStringCommand extends HachiCommandImpl {
    public HachiStringCommand(final String name, final String description) {
        super(name, description);
    }

    protected abstract String output(HachiCommandRequest r);

    @Override public final void run(final HachiCommandRequest r) {
        r.reply(output(r)).queue();
    }
}
