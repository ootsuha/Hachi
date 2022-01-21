package io.github.ootsuha.hachi.command.request.slash;

import io.github.ootsuha.hachi.command.request.*;
import net.dv8tion.jda.api.events.interaction.*;
import net.dv8tion.jda.api.interactions.commands.*;

/**
 * Implementation of <code>HachiCommandOptions</code> using a <code>SlashCommandEvent</code>.
 * *
 * Constructor.
 *
 * @param event slash command event
 */
public record HachiSlashCommandOptions(SlashCommandEvent event) implements HachiCommandOptions {
    @Override public boolean hasOption(final String optionName) {
        return this.event.getOption(optionName) != null;
    }

    @Override public String getString(final String optionName) {
        OptionMapping m = this.event.getOption(optionName);
        assert m != null;
        return m.getAsString();
    }

    @Override public Integer getInteger(final String optionName) {
        OptionMapping m = this.event.getOption(optionName);
        assert m != null;
        return (int) m.getAsLong();
    }

    @Override public Double getDouble(final String optionName) {
        OptionMapping m = this.event.getOption(optionName);
        assert m != null;
        return m.getAsDouble();
    }

    @Override public Boolean getBoolean(final String optionName) {
        OptionMapping m = this.event.getOption(optionName);
        assert m != null;
        return m.getAsBoolean();
    }
}
