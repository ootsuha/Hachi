package io.github.ootsuha.hachi.core.command;

import net.dv8tion.jda.api.interactions.commands.build.*;

import java.util.*;

public abstract class HachiSubcommandContainerImpl extends HachiCommandImpl implements HachiSubcommandContainer {
    private final Map<String, HachiCommand> subcommands;

    public HachiSubcommandContainerImpl(final String name, final String description) {
        super(name, description);
        this.subcommands = new HashMap<>();
    }

    @SafeVarargs
    public final void addSubcommands(final Class<? extends HachiCommandImpl>... classes) {
        try {
            for (Class<? extends HachiCommandImpl> clazz : classes) {
                var c = clazz.getConstructor(this.getClass()).newInstance(this);
                this.subcommands.put(c.getName(), c);
                addSubcommands(c.getSubcommandData());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    @SafeVarargs
    public final void addSubcommandGroup(final String name, final String description,
            final Class<? extends HachiCommand>... classes) {
        var group = new SubcommandGroupData(name, description);
        try {
            for (Class<? extends HachiCommand> clazz : classes) {
                var c = clazz.getConstructor(this.getClass()).newInstance(this);
                this.subcommands.put(String.format("%s.%s", name, c.getName()), c);
                group.addSubcommands(c.getSubcommandData());
            }
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
        addSubcommandGroups(group);
    }

    @Override
    public final HachiCommand getSubcommand(final String name) {
        return this.subcommands.get(name);
    }

    @Override
    public final HachiCommand getSubcommand(final String group, final String name) {
        return this.subcommands.get(String.format("%s.%s", group, name));
    }
}
