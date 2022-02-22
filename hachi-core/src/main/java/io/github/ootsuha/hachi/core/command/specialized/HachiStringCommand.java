package io.github.ootsuha.hachi.core.command.specialized;

import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.command.request.*;

/**
 * A <code>HachiCommand</code> that replies with a string.
 */
public interface HachiStringCommand extends HachiCommand {
    /**
     * Returns the string to reply with.
     *
     * @param r command request
     * @return string
     */
    String output(HachiCommandRequest r);

    @Override
    default void run(final HachiCommandRequest r) {
        r.reply(output(r)).queue();
    }
}
