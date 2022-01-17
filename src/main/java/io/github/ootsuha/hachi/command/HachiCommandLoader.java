package io.github.ootsuha.hachi.command;

import net.dv8tion.jda.api.entities.*;

import javax.annotation.*;
import java.util.*;

/**
 * Utility class to initialize <code>HachiCommand</code>s.
 */
public final class HachiCommandLoader {
    /**
     * Map of command names to <code>HachiCommand</code>s.
     */
    private static final Map<String, HachiCommand> COMMAND_MAP = new HashMap<>();

    /**
     * Private constructor so class cannot be instantiated.
     */
    private HachiCommandLoader() {
    }

    /**
     * Load a set of <code>HachiCommand</code>s.
     *
     * @param classes set of <code>HachiCommand</code>s
     */
    public static void load(final Set<Class<?>> classes) {
        Set<HachiCommand> commands = new HashSet<>();
        for (Class<?> c : classes) {
            assert HachiCommand.class.isAssignableFrom(c) : c.getName() + " is not a HachiCommand";
            try {
                HachiCommand comm = (HachiCommand) c.getConstructor().newInstance();
                commands.add(comm);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        addToCommandMap(commands);
    }

    /**
     * Adds commands to <code>COMMAND_MAP</code>.
     *
     * @param commands <code>HachiCommand</code>s to add
     */
    private static void addToCommandMap(final Set<HachiCommand> commands) {
        for (HachiCommand c : commands) {
            assert !COMMAND_MAP.containsKey(c.getName()) : "Duplicate HachiCommand: " + c.getName();
            COMMAND_MAP.put(c.getName(), c);
        }
    }

    /**
     * Creates slash commands in a guild for all commands in <code>COMMAND_MAP</code>.
     *
     * @param g guild to add slash commands to
     */
    public static void createGuildCommands(final Guild g) {
        for (HachiCommand c : COMMAND_MAP.values()) {
            g.upsertCommand(c.getCommandData()).queue();
        }
    }

    /**
     * Gets a <code>HachiCommand</code> from <code>COMMAND_MAP</code>.
     *
     * @param name name of command
     * @return HachiCommand, or null
     */
    @Nullable public static HachiCommand getCommand(final String name) {
        return COMMAND_MAP.get(name);
    }
}
