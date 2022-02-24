package io.github.ootsuha.hachi.core.command.request;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.requests.*;

import javax.annotation.*;

/**
 * Wraps <code>Message</code>s and <code>InteractionHook</code>s.
 */
public interface HachiCommandReply {
    /**
     * Removes all components in the reply.
     *
     * @return message rest action
     */
    @CheckReturnValue
    RestAction<Message> removeComponents();

    /**
     * Edits the content of the reply.
     *
     * @param content new content
     * @return message rest action
     */
    @CheckReturnValue
    RestAction<Message> editContent(String content);

    /**
     * Edits the embed of the reply.
     *
     * @param embed new embed
     * @return message rest action
     */
    @CheckReturnValue
    RestAction<Message> editEmbed(MessageEmbed embed);

    /**
     * Gets the id of the reply.
     *
     * @return id
     */
    String getId();
}
