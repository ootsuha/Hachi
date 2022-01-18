package io.github.ootsuha.hachi.command;

import io.github.ootsuha.hachi.command.usable.*;

import java.util.*;

/**
 * Utility class that holds a set of all <code>HachiCommand</code> classes.
 */
public final class HachiCommandClassSet {
    /**
     * Set of command classes.
     */
    private static final Set<Class<?>> SET;

    static {
        SET = new HashSet<>();
        SET.add(Disconnect.class);
        SET.add(Echo.class);
        SET.add(Flip.class);
        SET.add(GitHub.class);
        SET.add(Help.class);
        SET.add(Invite.class);
        SET.add(Ping.class);
        SET.add(Source.class);
        SET.add(Time.class);
        SET.add(UpdateCommands.class);
    }

    /**
     * Private constructor so class cannot be instantiated.
     */
    private HachiCommandClassSet() {
    }

    /**
     * Gets the command class set.
     *
     * @return set of command classes
     */
    public static Set<Class<?>> getSet() {
        return new HashSet<>(SET);
    }
}
