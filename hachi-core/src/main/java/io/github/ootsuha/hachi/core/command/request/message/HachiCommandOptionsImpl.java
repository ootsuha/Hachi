package io.github.ootsuha.hachi.core.command.request.message;

import io.github.ootsuha.hachi.core.command.request.*;
import lombok.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.interactions.commands.*;
import net.dv8tion.jda.api.interactions.commands.build.*;

import java.util.*;

/**
 * Implementation of <code>HachiCommandOptions</code> using a map.
 */
@AllArgsConstructor
@EqualsAndHashCode
public final class HachiCommandOptionsImpl implements HachiCommandOptions {
    private final Map<String, Object> options;
    private final SlashCommandData commandData;

    @Override
    public boolean hasOption(final String optionName) {
        return this.options.containsKey(optionName);
    }

    /**
     * Gets the option with the given name.
     *
     * @param optionName name
     * @return option data
     */
    private Optional<OptionData> getOption(final String optionName) {
        return this.commandData.getOptions().stream().filter(e -> e.getName().equals(optionName)).findAny();
    }

    @Override
    public String getString(final String optionName) {
        Optional<OptionData> option = getOption(optionName);
        assert option.isPresent() && option.get().getType() == OptionType.STRING;
        assert !option.get().isRequired() || (hasOption(optionName) && this.options.get(optionName) instanceof String);
        return (String) this.options.get(optionName);
    }

    @Override
    public Integer getInteger(final String optionName) {
        Optional<OptionData> option = getOption(optionName);
        assert option.isPresent() && option.get().getType() == OptionType.INTEGER;
        assert !option.get().isRequired() || (hasOption(optionName) && this.options.get(optionName) instanceof Integer);
        return (Integer) this.options.get(optionName);
    }

    @Override
    public Double getDouble(final String optionName) {
        Optional<OptionData> option = getOption(optionName);
        assert option.isPresent() && option.get().getType() == OptionType.NUMBER;
        assert !option.get().isRequired() || (hasOption(optionName) && this.options.get(optionName) instanceof Double);
        return (Double) this.options.get(optionName);
    }

    @Override
    public Boolean getBoolean(final String optionName) {
        Optional<OptionData> option = getOption(optionName);
        assert option.isPresent() && option.get().getType() == OptionType.BOOLEAN;
        assert !option.get().isRequired() || (hasOption(optionName) && this.options.get(optionName) instanceof Boolean);
        return (Boolean) this.options.get(optionName);
    }

    @Override
    public User getUser(final String optionName) {
        Optional<OptionData> option = getOption(optionName);
        assert option.isPresent() && option.get().getType() == OptionType.USER;
        assert !option.get().isRequired() || (hasOption(optionName) && this.options.get(optionName) instanceof User);
        return (User) this.options.get(optionName);
    }

    @Override
    public Role getRole(final String optionName) {
        Optional<OptionData> option = getOption(optionName);
        assert option.isPresent() && option.get().getType() == OptionType.ROLE;
        assert !option.get().isRequired() || (hasOption(optionName) && this.options.get(optionName) instanceof Role);
        return (Role) this.options.get(optionName);
    }
}
