package io.github.ootsuha.hachi.test;

import io.github.ootsuha.hachi.command.HachiCommandImpl;
import io.github.ootsuha.hachi.command.HachiCommandRequest;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.util.Set;

/**
 * Set of <code>HachiCommand</code>s used for testing.
 */
public class HachiCommandTestSet {
    public static final Set<Class<?>> classes = Set.of(C1.class, C2.class, C3.class, C4.class);

    public static class Template extends HachiCommandImpl {
        public Template(final String name) {
            super(name, "test command");
        }

        @Override public void run(final HachiCommandRequest r) {

        }
    }

    public static class C1 extends Template {
        public C1() {
            super("c1");
        }
    }

    public static class C2 extends Template {
        public C2() {
            super("c2");
            addOption(OptionType.STRING, "o1", "option");
        }
    }

    public static class C3 extends Template {
        public C3() {
            super("c3");
            addOption(OptionType.STRING, "o1", "option");
            addOption(OptionType.STRING, "o2", "option");
            addOption(OptionType.STRING, "o3", "option");
        }
    }

    public static class C4 extends Template {
        public C4() {
            super("c4");
            addOption(OptionType.STRING, "o1", "option");
            addOption(OptionType.BOOLEAN, "o2", "option");
            addOption(OptionType.INTEGER, "o3", "option");
            addOption(OptionType.NUMBER, "o4", "option");
        }
    }

}

