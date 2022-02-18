package io.github.ootsuha.hachi.core.command.help;

import io.github.ootsuha.hachi.core.*;
import io.github.ootsuha.hachi.core.command.*;

/**
 * Interface for generating help embeds of commands.
 */
public interface HelpEmbedGenerator {
    /**
     * Sets the help embed of a <code>HachiCommand</code>.
     *
     * @param command command to set help embed
     * @param config  hachi config
     */
    void setHelpEmbed(HachiCommand command, HachiConfig config);
}
