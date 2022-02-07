package io.github.ootsuha.hachi.core.command.request;

import io.github.ootsuha.hachi.core.command.*;

/**
 * Abstract class that implements the methods in <code>HachiCommandRequest</code> that will be the same for all
 * implementations.
 */
public abstract class HachiCommandRequestAbstract implements HachiCommandRequest {
    private final HachiCommand command;
    private final HachiCommandOptions options;

    public HachiCommandRequestAbstract(final HachiCommand c, final HachiCommandOptions o) {
        this.command = c;
        this.options = o;
    }

    @Override public final HachiCommand getRequestedCommand() {
        return this.command;
    }

    @Override public final HachiCommandOptions getOptions() {
        return this.options;
    }

    @Override public final String toString() {
        return "HCR{" + "command=" + this.command + ", options=" + this.options + '}';
    }
}