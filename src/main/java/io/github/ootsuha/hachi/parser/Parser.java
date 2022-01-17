package io.github.ootsuha.hachi.parser;

import io.github.ootsuha.hachi.command.*;
import io.github.ootsuha.hachi.utility.*;
import net.dv8tion.jda.api.interactions.commands.build.*;

import javax.annotation.*;
import java.util.*;

public final class Parser {
    /**
     * Private constructor so class cannot be instantiated.
     */
    private Parser() {
    }

    /**
     * Parse a string for a <code>HachiCommandRequest</code>.
     *
     * @param input input string
     * @return hachi command request, or null if invalid
     */
    @Nullable public static HachiCommandRequest parse(final String input) {
        if (!input.startsWith(Constant.BOT_PREFIX)) {
            return null;
        }
        StringBuilder b = new StringBuilder(input);
        b.delete(0, Constant.BOT_PREFIX.length());
        String name = getToken(b);
        HachiCommand comm = HachiCommandLoader.getCommand(name);
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

    private static boolean addOption(final Map<String, Object> options, final OptionData optionData, final String s) {
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

    private static String getToken(final StringBuilder b) {
        int i = b.indexOf(" ");
        if (i == -1) {
            i = b.length();
        }
        String s = b.substring(0, i);
        b.delete(0, i + 1);
        return s;
    }
}
