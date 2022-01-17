package io.github.ootsuha.hachi.command;

import io.github.ootsuha.hachi.utility.*;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.interactions.commands.build.*;

import javax.annotation.*;
import java.util.*;
import java.util.stream.*;

/**
 * Implements the methods in <code>HachiCommand</code>.
 */
public abstract class HachiCommandImpl extends CommandData implements HachiCommand {
    /**
     * List of aliases for the command. Should be lowercase.
     */
    @Nonnull private List<String> aliases;
    /**
     * List of examples for the command.
     */
    @Nonnull private List<String> example;
    /**
     * Caches the embed created in <code>createHelpEmbed()</code>.
     */
    private MessageEmbed helpEmbed;

    /**
     * @param name        The command name, 1-32 lowercase alphanumeric characters
     * @param description The command description, 1-100 characters
     * @throws IllegalArgumentException If any of the following requirements are not met
     *                                  <ul>
     *                                      <li>The name must be lowercase alphanumeric (with dash), 1-32 characters
     *                                      long</li>
     *                                      <li>The description must be 1-100 characters long</li>
     *                                  </ul>
     */
    public HachiCommandImpl(final String name, final String description) {
        super(name, description);
        this.aliases = new ArrayList<>();
        this.example = new ArrayList<>();
    }

    /**
     * Sets <code>this.aliases</code>.
     *
     * @param aliases string var args
     */
    public void setAliases(final String... aliases) {
        this.aliases = Arrays.stream(aliases).collect(Collectors.toList());
    }

    /**
     * Sets <code>this.example</code>.
     *
     * @param example string var args
     */
    public void setExample(final String... example) {
        this.example = Arrays.stream(example).collect(Collectors.toList());
    }

    @Override public final MessageEmbed getHelpEmbed() {
        if (this.helpEmbed == null) {
            this.helpEmbed = createHelpEmbed();
        }
        return this.helpEmbed;
    }

    /**
     * Creates the help embed for the command.
     *
     * @return help embed
     */
    public MessageEmbed createHelpEmbed() {
        EmbedBuilder b = new EmbedBuilder();
        b.setTitle(String.format("Help: `%s`", this.name));
        b.setDescription(this.description);
        if (this.aliases.size() > 0) {
            StringBuilder value = new StringBuilder();
            for (String alias : this.aliases) {
                value.append(String.format("`%s`, ", alias));
            }
            value = new StringBuilder(value.substring(0, value.length() - 2));
            b.addField("Aliases", value.toString(), false);
        }
        if (this.example.size() > 0) {
            StringBuilder value = new StringBuilder();
            for (String example : this.example) {
                if (example.isEmpty()) {
                    value.append(String.format("`%s%s`%n", Constant.BOT_PREFIX, this.name));
                } else {
                    value.append(String.format("`%s%s`%n", Constant.BOT_PREFIX, example));
                }
            }
            b.addField("Example", value.toString(), false);
        } else {
            b.addField("Example", String.format("`%s%s`", Constant.BOT_PREFIX, this.name), false);
        }
        b.setColor(Constant.COLOR);
        return b.build();
    }

    @Override public final CommandData getCommandData() {
        return this;
    }
}
