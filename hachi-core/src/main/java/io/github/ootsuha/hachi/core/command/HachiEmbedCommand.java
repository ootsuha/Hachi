package io.github.ootsuha.hachi.core.command;

import io.github.ootsuha.hachi.core.command.request.*;
import net.dv8tion.jda.api.entities.*;

/**
 * A <code>HachiCommand</code> that replies with an embed.
 */
public abstract class HachiEmbedCommand extends HachiCommandImpl {
    public HachiEmbedCommand(final String name, final String description) {
        super(name, description);
    }

    protected abstract MessageEmbed output(HachiCommandRequest r);

    @Override public final void run(final HachiCommandRequest r) {
        r.replyEmbed(output(r)).queue();
    }
}
