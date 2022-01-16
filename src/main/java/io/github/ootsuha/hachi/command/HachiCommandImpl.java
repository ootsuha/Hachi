package io.github.ootsuha.hachi.command;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implements the methods in <code>HachiCommand</code>.
 */
public abstract class HachiCommandImpl extends CommandData implements HachiCommand {
    /**
     * List of aliases for the command. Should be lowercase. Defaults to empty
     * list.
     */
    @Nonnull private List<String> aliases;
    /**
     * List of examples for the command.
     */
    @Nonnull private List<String> example;

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
    public HachiCommandImpl(@NotNull final String name, @NotNull final String description) {
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

    @Override public final CommandData getCommandData() {
        return this;
    }
}
