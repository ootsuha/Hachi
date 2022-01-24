package io.github.ootsuha.hachi.core.parser;

import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.command.request.*;
import io.github.ootsuha.hachi.core.command.request.message.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.interactions.commands.build.*;

import javax.annotation.*;
import java.util.*;

/**
 * Parses text into <code>HachiCommandRequest</code>s.
 */
public final class Parser {
    private final HachiCommandLoader loader;
    private final String prefix;

    public Parser(final HachiCommandLoader loader, final String prefix) {
        this.loader = loader;
        this.prefix = prefix;
    }

    private boolean parseBoolean(final String s) {
        return switch (s.toLowerCase()) {
            case "true" -> true;
            case "false" -> false;
            default -> throw new NumberFormatException();
        };
    }

    /**
     * Parse a message for a <code>HachiCommandRequest</code>.
     *
     * @param message message to be parsed
     * @return hachi command request, or null if invalid
     */
    @Nullable public HachiCommandRequest parse(final Message message) {
        String input = message.getContentRaw().trim();
        if (!input.startsWith(this.prefix)) {
            return null;
        }
        StringBuilder b = new StringBuilder(input);
        b.delete(0, this.prefix.length());
        String name = getToken(b);
        HachiCommand comm = this.loader.getCommand(name);
        if (comm == null) {
            return null;
        }
        HachiCommandOptions options = parseOptions(b, comm.getCommandData());
        if (options == null) {
            return null;
        }
        return new HachiMessageCommandRequest(message, comm, options);
    }

    /**
     * Parses options.
     *
     * @param b    input
     * @param data command data
     * @return hachi command option, or null if invalid options for the given command data
     */
    @Nullable public HachiCommandOptions parseOptions(final StringBuilder b, final CommandData data) {
        return parseOptions(b, 0, new HashMap<>(), data);
    }

    /**
     * Parses options recursively.
     *
     * @param b          string
     * @param ind        index to start at for list of option data
     * @param optionsMap map of current options
     * @param data       command data
     * @return hachi command option, or null if invalid options for the given command data
     */
    @Nullable public HachiCommandOptions parseOptions(final StringBuilder b, final int ind,
            final Map<String, Object> optionsMap, final CommandData data) {
        List<OptionData> options = data.getOptions();
        for (int i = ind; i < options.size(); i++) {
            OptionData option = options.get(i);
            String token = getToken(b);
            boolean success = addOption(optionsMap, option, token);
            if (!success) {
                // if option is not required, reset b and try to parse again by skipping current option
                if (!option.isRequired()) {
                    if (!b.isEmpty()) {
                        b.insert(0, ' ');
                    }
                    b.insert(0, token);
                    return parseOptions(b, i + 1, optionsMap, data);
                }
                return null;
            }
        }
        if (!b.isEmpty()) {
            return null;
        }
        return new HachiCommandOptionsImpl(optionsMap, data);
    }

    /**
     * Adds an option to <code>options</code>.
     *
     * @param options    map to add option to
     * @param optionData option data for the option being added
     * @param s          token from message
     * @return whether the command succeeded or not
     */
    private boolean addOption(final Map<String, Object> options, final OptionData optionData, final String s) {
        try {
            if (s.isBlank()) {
                return false;
            }
            Object o = switch (optionData.getType()) {
                case STRING -> s;
                case NUMBER -> Double.parseDouble(s);
                case BOOLEAN -> parseBoolean(s);
                case INTEGER -> Integer.parseInt(s);
                default -> null;
            };
            if (o == null) {
                return false;
            }
            options.put(optionData.getName(), o);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Gets the next token and removes it.
     *
     * @param b string builder
     * @return token
     */
    private String getToken(final StringBuilder b) {
        int i = b.indexOf(" ");
        if (i == -1) {
            i = b.length();
        }
        String s = b.substring(0, i).trim();
        b.delete(0, i + 1);
        return s;
    }
}
