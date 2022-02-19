package io.github.ootsuha.hachi.core.command;

import lombok.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.interactions.commands.build.*;
import net.dv8tion.jda.internal.interactions.*;

import java.util.*;

/**
 * Implements the methods in <code>HachiCommand</code>.
 */
public abstract class HachiCommandImpl extends CommandDataImpl implements HachiCommand {
    @Getter
    private List<String> aliases;
    @Getter
    private List<String> examples;
    @Getter
    @Setter
    private MessageEmbed helpEmbed;

    public HachiCommandImpl(final String name, final String description) {
        super(name, description);
        this.aliases = new ArrayList<>();
        this.examples = new ArrayList<>();
    }

    public final void setAliases(final String... aliases) {
        this.aliases = Arrays.stream(aliases).toList();
    }

    public final void setExamples(final String... examples) {
        this.examples = Arrays.stream(examples).toList();
    }

    @Override
    public final SlashCommandData getCommandData() {
        return this;
    }
}
