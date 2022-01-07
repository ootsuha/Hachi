package io.github.ootsuha.hachi.command;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

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
     * Runs the command.
     *
     * @param e event that called the command
     */
    void run(SlashCommandEvent e);
}
