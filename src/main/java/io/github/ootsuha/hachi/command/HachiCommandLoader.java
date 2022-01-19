package io.github.ootsuha.hachi.command;

import org.springframework.stereotype.*;

import javax.annotation.*;
import java.util.*;

/**
 * Holds <code>HachiCommand</code>s and can return commands from a command's name or alias.
 */
@Component
public final class HachiCommandLoader {
    /**
     * Maps command names to <code>HachiCommand</code>s.
     */
    private final Map<String, HachiCommand> hachiCommandMap;
    /**
     * Maps aliases to actual command names.
     */
    private final Map<String, String> aliasMap;

    public HachiCommandLoader() {
        this.hachiCommandMap = new HashMap<>();
        this.aliasMap = new HashMap<>();
    }

    /**
     * Load a <code>HachiCommand</code> into the loader.
     *
     * @param command command to add
     */
    public void loadCommand(final HachiCommand command) {
        assert !this.hachiCommandMap.containsKey(command.getName()) : "Duplicate HachiCommand name: "
                + command.getName();
        this.hachiCommandMap.put(command.getName(), command);
        this.aliasMap.put(command.getName(), command.getName());
        for (String alias : command.getAliases()) {
            assert !this.aliasMap.containsKey(alias) : "Duplicate HachiCommand alias: " + alias;
            this.aliasMap.put(alias, command.getName());
        }
    }

    /**
     * Gets a <code>HachiCommand</code>.
     *
     * @param name name or alias or command
     * @return HachiCommand, or null
     */
    @Nullable public HachiCommand getCommand(final String name) {
        String converted = this.aliasMap.get(name);
        if (converted == null) {
            return null;
        }
        return this.hachiCommandMap.get(converted);
    }
}
