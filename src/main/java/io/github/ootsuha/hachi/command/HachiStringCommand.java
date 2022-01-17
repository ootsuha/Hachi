package io.github.ootsuha.hachi.command;

import javax.annotation.*;

/**
 * A <code>HachiCommand</code> that replies with a string.
 */
public abstract class HachiStringCommand extends HachiCommandImpl {
    /**
     * @param name        The command name, 1-32 lowercase alphanumeric characters
     * @param description The command description, 1-100 characters
     * @throws IllegalArgumentException If any of the following requirements are not met
     *                                  <ul>
     *                                      <li>The name must be lowercase alphanumeric (with dash), 1-32 characters
     *                                      long</li>
     *                                      <li>The description must be 1-100 characters long</li>
     *                                  </ul>
     */
    public HachiStringCommand(final String name, final String description) {
        super(name, description);
    }

    protected abstract String output(HachiCommandRequest r);

    @Override public final void run(final HachiCommandRequest r) {
        r.reply(output(r));
    }
}
