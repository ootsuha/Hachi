package io.github.ootsuha.hachi.command.request.message;

import io.github.ootsuha.hachi.command.*;
import io.github.ootsuha.hachi.command.request.*;
import net.dv8tion.jda.api.entities.*;

/**
 * Represents user command requests that come from a message.
 */
public final class HachiMessageCommandRequest extends HachiCommandRequestAbstract {
    private final Message message;

    /**
     * Constructor.
     *
     * @param m message
     * @param c hachi command
     * @param o hachi command options
     */
    public HachiMessageCommandRequest(final Message m, final HachiCommand c, final HachiCommandOptions o) {
        super(c, o);
        this.message = m;
    }

    @Override public User getUser() {
        return this.message.getAuthor();
    }

    @Override public TextChannel getChannel() {
        return this.message.getTextChannel();
    }

    @Override public void deferReply() {
        this.message.getTextChannel().sendTyping().queue();
    }

    @Override public HachiCommandReplyAction reply(final String content) {
        return new HachiMessageCommandReplyAction(this.message, content);
    }

    @Override public HachiCommandReplyAction replyEmbed(final MessageEmbed embed) {
        return new HachiMessageCommandReplyAction(this.message, embed);
    }

}
