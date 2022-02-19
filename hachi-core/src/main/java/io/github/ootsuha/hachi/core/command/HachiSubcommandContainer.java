package io.github.ootsuha.hachi.core.command;

import io.github.ootsuha.hachi.core.command.request.*;

/**
 * Interface for commands that Hachi has.
 */
public interface HachiSubcommandContainer extends HachiCommand {
    @Override
    default void run(HachiCommandRequest r) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets a subcommand from its name.
     *
     * @param name name of subcommand
     * @return hachi command
     */
    HachiCommand getSubcommand(String name);

    /**
     * Gets a subcommand from its group and name.
     *
     * @param group group name
     * @param name  name of subcommand
     * @return hachi command
     */
    HachiCommand getSubcommand(String group, String name);
}
