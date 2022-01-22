package io.github.ootsuha.hachi.command.request.slash;

import io.github.ootsuha.hachi.command.request.*;
import net.dv8tion.jda.api.events.interaction.*;
import net.dv8tion.jda.api.interactions.commands.*;

import java.util.*;

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
        if (m == null) {
            return null;
        }
        return m.getAsString();
    }

    @Override public Integer getInteger(final String optionName) {
        OptionMapping m = this.event.getOption(optionName);
        if (m == null) {
            return null;
        }
        return (int) (m.getAsLong());
    }

    @Override public Double getDouble(final String optionName) {
        OptionMapping m = this.event.getOption(optionName);
        if (m == null) {
            return null;
        }
        return m.getAsDouble();
    }

    @Override public Boolean getBoolean(final String optionName) {
        OptionMapping m = this.event.getOption(optionName);
        if (m == null) {
            return null;
        }
        return m.getAsBoolean();
    }

    @Override public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HachiSlashCommandOptions that)) {
            return false;
        }
        return Objects.equals(this.event, that.event);
    }

    @Override public int hashCode() {
        return Objects.hash(this.event);
    }
}
