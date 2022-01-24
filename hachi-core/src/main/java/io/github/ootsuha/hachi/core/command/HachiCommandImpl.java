package io.github.ootsuha.hachi.core.command;

import io.github.ootsuha.hachi.core.*;
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
     * List of aliases for the command.
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

    public HachiCommandImpl(final String name, final String description) {
        super(name, description);
        this.aliases = new ArrayList<>();
        this.example = new ArrayList<>();
    }

    /**
     * Turns an <code>OptionData</code> into a string for help embeds.
     *
     * @param data option data
     * @return string
     */
    private static String optionToString(final OptionData data) {
        return String.format("<%s%s>", data.getName(), data.isRequired() ? "" : '?');
    }

    /**
     * Adds spaces to the left of an option until it reaches the specified length.
     *
     * @param s      string to align
     * @param length end length
     * @return left-aligned string
     */
    private static String alignOption(final String s, final int length) {
        StringBuilder spaces = new StringBuilder();
        while (spaces.length() <= length - s.length()) {
            spaces.append(' ');
        }
        return spaces.toString() + '<' + s + '>';
    }

    /**
     * Returns a description of a command's options.
     *
     * @param data command data
     * @return string
     */
    private static String commandOptionDescription(final CommandData data) {
        int maxLen = Collections.max(
                data.getOptions().stream().map(OptionData::getName).map(String::length).collect(Collectors.toList()));
        Stream<String> mapped = data.getOptions().stream()
                .map(e -> String.format("`%s` - %s", alignOption(e.getName(), maxLen), e.getDescription()));
        return mapped.collect(Collectors.joining("\n"));
    }

    @Override @Nonnull public final List<String> getAliases() {
        return new ArrayList<>(this.aliases);
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
        return this.helpEmbed;
    }

    @Override public final void setHelpEmbed(final HachiConfig config) {
        EmbedBuilder b = new EmbedBuilder();
        b.setTitle(String.format("Help: `%s`", this.name));
        b.setDescription(this.description);
        if (getOptions().size() > 0) {
            Stream<String> m = getOptions().stream().map(HachiCommandImpl::optionToString);
            String syntax =
                    String.format("```%s%s %s```", config.getPrefix(), this.name, m.collect(Collectors.joining(" ")));
            b.addField("Usage", syntax + commandOptionDescription(this), false);
        }
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
                    value.append(String.format("`%s%s`%n", config.getPrefix(), this.name));
                } else {
                    value.append(String.format("`%s%s`%n", config.getPrefix(), example));
                }
            }
            b.addField("Example", value.toString(), false);
        } else {
            b.addField("Example", String.format("`%s%s`", config.getPrefix(), this.name), false);
        }
        b.setColor(config.getEmbedColor());
        this.helpEmbed = b.build();
    }

    @Override public final CommandData getCommandData() {
        return this;
    }

    @Override public final String toString() {
        return "HachiCommandImpl{" + "name='" + name + '\'' + '}';
    }
}
