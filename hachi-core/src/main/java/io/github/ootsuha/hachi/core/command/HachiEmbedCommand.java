package io.github.ootsuha.hachi.core.command;

import io.github.ootsuha.hachi.core.command.request.*;
import net.dv8tion.jda.api.entities.*;

/**
 * A <code>HachiCommand</code> that replies with an embed.
 */
public interface HachiEmbedCommand extends HachiCommand {
    MessageEmbed output(HachiCommandRequest r);

    @Override
    default void run(final HachiCommandRequest r) {
        r.replyEmbed(output(r)).queue();
    }
}
