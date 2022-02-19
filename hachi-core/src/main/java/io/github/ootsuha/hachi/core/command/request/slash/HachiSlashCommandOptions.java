package io.github.ootsuha.hachi.core.command.request.slash;

import io.github.ootsuha.hachi.core.command.request.*;
import lombok.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.command.*;
import net.dv8tion.jda.api.interactions.commands.*;

/**
 * Implementation of <code>HachiCommandOptions</code> using a <code>SlashCommandInteractionEvent</code>.
 */
@AllArgsConstructor
@EqualsAndHashCode
public final class HachiSlashCommandOptions implements HachiCommandOptions {
    private final SlashCommandInteractionEvent event;

    @Override
    public boolean hasOption(final String optionName) {
        return this.event.getOption(optionName) != null;
    }

    @Override
    public String getString(final String optionName) {
        OptionMapping m = this.event.getOption(optionName);
        if (m == null) {
            return null;
        }
        return m.getAsString();
    }

    @Override
    public Integer getInteger(final String optionName) {
        OptionMapping m = this.event.getOption(optionName);
        if (m == null) {
            return null;
        }
        return (int) (m.getAsLong());
    }

    @Override
    public Double getDouble(final String optionName) {
        OptionMapping m = this.event.getOption(optionName);
        if (m == null) {
            return null;
        }
        return m.getAsDouble();
    }

    @Override
    public Boolean getBoolean(final String optionName) {
        OptionMapping m = this.event.getOption(optionName);
        if (m == null) {
            return null;
        }
        return m.getAsBoolean();
    }

    @Override
    public User getUser(final String optionName) {
        OptionMapping m = this.event.getOption(optionName);
        if (m == null) {
            return null;
        }
        return m.getAsUser();
    }

    @Override
    public Role getRole(final String optionName) {
        OptionMapping m = this.event.getOption(optionName);
        if (m == null) {
            return null;
        }
        return m.getAsRole();
    }
}
