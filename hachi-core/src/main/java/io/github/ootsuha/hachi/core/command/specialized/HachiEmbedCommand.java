package io.github.ootsuha.hachi.core.command.specialized;

import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.command.request.*;
import net.dv8tion.jda.api.entities.*;

/**
 * A <code>HachiCommand</code> that replies with an embed.
 */
public interface HachiEmbedCommand extends HachiCommand {
    /**
     * Returns the message embed to reply with.
     *
     * @param r command request
     * @return message embed
     */
    MessageEmbed output(HachiCommandRequest r);

    @Override
    default void run(final HachiCommandRequest r) {
        r.replyEmbed(output(r)).queue();
    }
}
