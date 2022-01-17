package io.github.ootsuha.hachi.test;

import io.github.ootsuha.hachi.command.*;
import net.dv8tion.jda.api.interactions.commands.*;

import java.util.*;

/**
 * Set of <code>HachiCommand</code>s used for testing.
 */
public class HachiCommandTestSet {
    public static final Set<Class<?>> classes =
            Set.of(NoOption.class, StringOption.class, StringOption3.class, AllOption.class);

    public static class Template extends HachiCommandImpl {
        public Template(final String name) {
            super(name, "test command");
        }

        @Override public void run(final HachiCommandRequest r) {

        }
    }

    public static class NoOption extends Template {
        public NoOption() {
            super("nooption");
        }
    }

    public static class StringOption extends Template {
        public StringOption() {
            super("stringoption");
            addOption(OptionType.STRING, "string", "option");
        }
    }

    public static class StringOption3 extends Template {
        public StringOption3() {
            super("stringoption3");
            addOption(OptionType.STRING, "string1", "option");
            addOption(OptionType.STRING, "string2", "option");
            addOption(OptionType.STRING, "string3", "option");
        }
    }

    public static class AllOption extends Template {
        public AllOption() {
            super("alloption");
            addOption(OptionType.STRING, "string", "option");
            addOption(OptionType.BOOLEAN, "boolean", "option");
            addOption(OptionType.INTEGER, "integer", "option");
            addOption(OptionType.NUMBER, "double", "option");
        }
    }

}

