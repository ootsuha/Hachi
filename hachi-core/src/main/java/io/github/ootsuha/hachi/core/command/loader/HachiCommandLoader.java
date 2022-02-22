package io.github.ootsuha.hachi.core.command.loader;

import io.github.ootsuha.hachi.core.*;
import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.command.help.*;
import net.dv8tion.jda.api.events.interaction.command.*;

import javax.annotation.*;
import java.util.*;

/**
 * Holds <code>HachiCommand</code>s and can return commands from a command's name or alias.
 */
public final class HachiCommandLoader {
    /**
     * Maps command names to <code>HachiCommand</code>s.
     */
    private final Map<String, HachiCommand> hachiCommandMap = new HashMap<>();
    /**
     * Maps aliases to actual command names.
     */
    private final Map<String, String> aliasMap = new HashMap<>();

    /**
     * Load a <code>HachiCommand</code> into the loader.
     *
     * @param command command to add
     */
    public void loadCommand(final HachiCommand command) {
        String commandName = command.getName();
        mapUniqueAdd(this.hachiCommandMap, commandName, command);
        mapUniqueAdd(this.aliasMap, commandName, commandName);
        if (commandName.contains("-")) {
            mapUniqueAdd(this.aliasMap, commandName.replace("-", ""), commandName);
        }
        for (String alias : command.getAliases()) {
            mapUniqueAdd(this.aliasMap, alias, commandName);
        }
    }

    /**
     * Adds an entry to a map and asserts that the key is unique.
     *
     * @param map   map to add to
     * @param key   unique key
     * @param value value
     * @param <K>   type of key
     * @param <V>   type of value
     */
    private <K, V> void mapUniqueAdd(final Map<K, V> map, final K key, final V value) {
        assert !map.containsKey(key) : "Duplicate HachiCommand name/alias: " + key;
        map.put(key, value);
    }

    /**
     * Gets a <code>HachiCommand</code>.
     *
     * @param name name or alias or command
     * @return HachiCommand, or null
     */
    @Nullable
    public HachiCommand getCommand(final String name) {
        String converted = this.aliasMap.get(name);
        if (converted == null) {
            return null;
        }
        return this.hachiCommandMap.get(converted);
    }

    /**
     * Gets a <code>HachiCommand</code>.
     *
     * @param event slash command event
     * @return HachiCommand, or null
     */
    @Nullable
    public HachiCommand getCommand(final SlashCommandInteractionEvent event) {
        if (event.getSubcommandGroup() != null) {
            return getSubcommand(event.getName(), event.getSubcommandGroup(), event.getSubcommandName());
        } else if (event.getSubcommandName() != null) {
            return getSubcommand(event.getName(), event.getSubcommandName());
        } else {
            return getCommand(event.getName());
        }
    }

    /**
     * Gets a <code>HachiCommand</code> that is a subcommand.
     *
     * @param command command to search for subcommand in
     * @param name    name of the subcommand
     * @return hachi command, or null if not found
     */
    @Nullable
    public HachiCommand getSubcommand(final String command, final String name) {
        var c = getCommand(command);
        if (c instanceof HachiSubcommandContainer c2) {
            return c2.getSubcommand(name);
        }
        return null;
    }

    /**
     * Gets a <code>HachiCommand</code> that is a subcommand.
     *
     * @param command command to search for subcommand in
     * @param group   group of the subcommand
     * @param name    name of the subcommand
     * @return hachi command, or null if not found
     */
    @Nullable
    public HachiCommand getSubcommand(final String command, final String group, final String name) {
        var c = getCommand(command);
        if (c instanceof HachiSubcommandContainer c2) {
            return c2.getSubcommand(group, name);
        }
        return null;
    }

    /**
     * Gets all commands loaded.
     *
     * @return collection of hachi commands
     */
    public Collection<HachiCommand> getCommands() {
        return this.hachiCommandMap.values();
    }

    /**
     * Generates help embeds for all loaded commands.
     *
     * @param generator help embed generator
     * @param config    config
     */
    public void generateHelpEmbeds(final HelpEmbedGenerator generator, final HachiConfig config) {
        for (HachiCommand c : this.hachiCommandMap.values()) {
            generator.setHelpEmbed(c, config);
        }
    }
}
