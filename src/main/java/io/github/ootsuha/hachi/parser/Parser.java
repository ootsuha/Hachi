package io.github.ootsuha.hachi.parser;

import io.github.ootsuha.hachi.command.*;
import net.dv8tion.jda.api.interactions.commands.build.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import javax.annotation.*;
import java.util.*;

/**
 * Parses text into <code>HachiCommandRequest</code>s.
 */
@Component
public final class Parser {
    @Value("${hachi.prefix}") private String prefix;
    private HachiCommandLoader loader;

    @Autowired private void setLoader(final HachiCommandLoader loader) {
        this.loader = loader;
    }

    /**
     * Parse a string for a <code>HachiCommandRequest</code>.
     *
     * @param input input string
     * @return hachi command request, or null if invalid
     */
    @Nullable public HachiCommandRequest parse(final String input) {
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
        List<OptionData> op = comm.getCommandData().getOptions();
        Map<String, Object> options = new HashMap<>();
        for (OptionData optionData : op) {
            if (b.isEmpty()) {
                return null;
            }
            if (!addOption(options, optionData, getToken(b))) {
                return null;
            }
        }
        if (!b.isEmpty()) {
            return null;
        }
        return new HachiCommandRequest(comm, options);
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
            Object o = switch (optionData.getType()) {
                case STRING -> s;
                case NUMBER -> Double.parseDouble(s);
                case BOOLEAN -> Boolean.parseBoolean(s);
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
        String s = b.substring(0, i);
        b.delete(0, i + 1);
        return s;
    }
}
