package io.github.ootsuha.hachi.command;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;

/**
 * Interface for commands that Hachi has.
 */
public interface HachiCommand {
    /**
     * Gets the name of the command.
     *
     * @return name
     */
    String getName();

    /**
     * Gets the description of the command.
     *
     * @return description
     */
    String getDescription();

    /**
     * Gets the <code>CommandData</code> of the command.
     *
     * @return command data
     */
    CommandData getCommandData();

    /**
     * Runs the command.
     *
     * @param r event that called the command
     */
    void run(HachiCommandRequest r);
}
