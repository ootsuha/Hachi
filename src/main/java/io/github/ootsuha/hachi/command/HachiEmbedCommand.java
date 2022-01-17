package io.github.ootsuha.hachi.command;

import net.dv8tion.jda.api.entities.*;

import javax.annotation.*;

/**
 * A <code>HachiCommand</code> that replies with an embed.
 */
public abstract class HachiEmbedCommand extends HachiCommandImpl {
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
    public HachiEmbedCommand(final String name, final String description) {
        super(name, description);
    }

    protected abstract MessageEmbed output(HachiCommandRequest r);

    @Override public final void run(final HachiCommandRequest r) {
        r.replyEmbed(output(r));
    }
}
