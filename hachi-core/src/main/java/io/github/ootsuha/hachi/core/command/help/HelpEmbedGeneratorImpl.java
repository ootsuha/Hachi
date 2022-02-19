package io.github.ootsuha.hachi.core.command.help;

import io.github.ootsuha.hachi.core.*;
import io.github.ootsuha.hachi.core.command.*;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.interactions.commands.build.*;

import java.util.*;
import java.util.stream.*;

/**
 * Implementation of <code>HelpEmbedGenerator</code>.
 */
public final class HelpEmbedGeneratorImpl implements HelpEmbedGenerator {
    /**
     * Turns an <code>OptionData</code> into a string for help embeds.
     *
     * @param data option data
     * @return string
     */
    private String optionToString(final OptionData data) {
        return String.format("<%s%s>", data.getName(), data.isRequired() ? "" : '?');
    }

    /**
     * Adds spaces to the left of an option until it reaches the specified length.
     *
     * @param s      string to align
     * @param length end length
     * @return left-aligned string
     */
    private String alignOption(final String s, final int length) {
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
    private String commandOptionDescription(final SlashCommandData data) {
        int maxLen = Collections.max(
                data.getOptions().stream().map(OptionData::getName).map(String::length).collect(Collectors.toList()));
        Stream<String> mapped = data.getOptions().stream()
                .map(e -> String.format("`%s` - %s", alignOption(e.getName(), maxLen), e.getDescription()));
        return mapped.collect(Collectors.joining("\n"));
    }

    @Override
    public void setHelpEmbed(final HachiCommand command, final HachiConfig config) {
        SlashCommandData data = command.getCommandData();
        EmbedBuilder b = new EmbedBuilder();
        b.setTitle(String.format("Help: `%s`", data.getName()));
        b.setDescription(data.getDescription());
        if (data.getOptions().size() > 0) {
            Stream<String> m = data.getOptions().stream().map(this::optionToString);
            String syntax = String.format("```%s%s %s```", config.getPrefix(), data.getName(),
                    m.collect(Collectors.joining(" ")));
            b.addField("Usage", syntax + commandOptionDescription(data), false);
        }
        if (command.getAliases().size() > 0) {
            StringBuilder value = new StringBuilder();
            for (String alias : command.getAliases()) {
                value.append(String.format("`%s`, ", alias));
            }
            value = new StringBuilder(value.substring(0, value.length() - 2));
            b.addField("Aliases", value.toString(), false);
        }
        if (command.getExamples().size() > 0) {
            StringBuilder value = new StringBuilder();
            for (String example : command.getExamples()) {
                if (example.isEmpty()) {
                    value.append(String.format("`%s%s`%n", config.getPrefix(), data.getName()));
                } else {
                    value.append(String.format("`%s%s`%n", config.getPrefix(), example));
                }
            }
            b.addField("Example", value.toString(), false);
        } else {
            b.addField("Example", String.format("`%s%s`", config.getPrefix(), command.getName()), false);
        }
        b.setColor(config.getEmbedColor());
        command.setHelpEmbed(b.build());
    }
}
