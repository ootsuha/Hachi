package io.github.ootsuha.hachi.command.request;

import io.github.ootsuha.hachi.command.*;
import net.dv8tion.jda.api.entities.*;

import javax.annotation.*;

/**
 * Represents a user's request for a command.
 */
public interface HachiCommandRequest {
    /**
     * Get the <code>HachiCommand</code> that the request is associated with.
     *
     * @return hachi command
     */
    HachiCommand getRequestedCommand();

    /**
     * Get the options given by the user.
     *
     * @return hachi command options
     */
    HachiCommandOptions getOptions();

    /**
     * Get the user that requested this request.
     *
     * @return user
     */
    User getUser();

    /**
     * Get the channel where the request was sent from.
     *
     * @return text channel
     */
    TextChannel getChannel();

    /**
     * Indicate to the user that the request has been received, but will take time to complete.
     */
    void deferReply();

    /**
     * Respond to the request with a message.
     *
     * @param content message
     * @return reply action
     */
    @CheckReturnValue HachiCommandReplyAction reply(String content);

    /**
     * Respond to the request with an embed.
     *
     * @param embed message embed
     * @return reply action
     */
    @CheckReturnValue HachiCommandReplyAction replyEmbed(MessageEmbed embed);
}
