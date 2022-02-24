package io.github.ootsuha.hachi.core.command;

import io.github.ootsuha.hachi.core.command.request.*;
import net.dv8tion.jda.api.interactions.commands.*;
import net.dv8tion.jda.api.interactions.commands.build.*;
import net.dv8tion.jda.internal.interactions.*;

import javax.annotation.*;
import java.lang.reflect.*;
import java.util.*;

public abstract class HachiSubcommandContainerImpl extends HachiCommandImpl implements HachiSubcommandContainer {
    private final Map<String, HachiCommand> subcommands;

    public HachiSubcommandContainerImpl(final String name, final String description) {
        super(name, description);
        this.subcommands = new HashMap<>();
    }

    @Deprecated
    @Override
    public final void run(final HachiCommandRequest r) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Nonnull
    @Override
    public final SlashCommandData addOption(@Nonnull final OptionType type, @Nonnull final String name,
            @Nonnull final String description) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Nonnull
    @Override
    public final SlashCommandData addOption(@Nonnull final OptionType type, @Nonnull final String name,
            @Nonnull final String description, final boolean required) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Nonnull
    @Override
    public final SlashCommandData addOptions(@Nonnull final Collection<? extends OptionData> options) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Nonnull
    @Override
    public final CommandDataImpl addOptions(@Nonnull final OptionData... options) {
        throw new UnsupportedOperationException();
    }

    @SafeVarargs
    public final void addSubcommands(final Class<? extends HachiCommandImpl>... classes) {
        try {
            for (Class<? extends HachiCommandImpl> clazz : classes) {
                HachiCommandImpl c;
                if (Modifier.isStatic(clazz.getModifiers())) {
                    c = clazz.getConstructor().newInstance();
                } else {
                    c = clazz.getConstructor(this.getClass()).newInstance(this);
                }
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
