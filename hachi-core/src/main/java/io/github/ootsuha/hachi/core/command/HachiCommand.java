package io.github.ootsuha.hachi.core.command;

import io.github.ootsuha.hachi.core.*;
import io.github.ootsuha.hachi.core.command.request.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.interactions.commands.build.*;

import java.util.*;

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
     * Gets the aliases of the command.
     *
     * @return list of string
     */
    List<String> getAliases();

    /**
     * Gets the help embed of the command.
     *
     * @return embed
     */
    MessageEmbed getHelpEmbed();

    /**
     * Sets the help embed of the command.
     *
     * @param config hachi config
     */
    void setHelpEmbed(HachiConfig config);

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
