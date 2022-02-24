package io.github.ootsuha.hachi.core.command.request;

import io.github.ootsuha.hachi.core.command.*;
import net.dv8tion.jda.api.entities.*;

import javax.annotation.*;
import java.io.*;

/**
 * Represents a user's request for a command.
 */
public interface HachiCommandRequest {
    /**
     * Respond to the request.
     */
    default void fulfill() {
        getCommand().run(this);
    }

    /**
     * Get the <code>HachiCommand</code> that the request is associated with.
     *
     * @return hachi command
     */
    HachiCommand getCommand();

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
    @CheckReturnValue
    HachiCommandReplyAction reply(String content);

    /**
     * Respond to the request with an embed.
     *
     * @param embed message embed
     * @return reply action
     */
    @CheckReturnValue
    HachiCommandReplyAction replyEmbed(MessageEmbed embed);

    /**
     * Respond to the request with an attachment.
     *
     * @param data input stream of attachment
     * @param name file name
     * @return reply action
     */
    @CheckReturnValue
    HachiCommandReplyAction replyFile(InputStream data, String name);

    /**
     * Respond to the request with an attachment.
     *
     * @param data byte array of attachment
     * @param name file name
     * @return reply action
     */
    @CheckReturnValue
    default HachiCommandReplyAction replyFile(final byte[] data, String name) {
        return replyFile(new ByteArrayInputStream(data), name);
    }

    /**
     * Respond to the request with an attachment.
     *
     * @param file attachment file
     * @param name file name
     * @return reply action
     */
    @CheckReturnValue
    default HachiCommandReplyAction replyFile(final File file, String name) {
        try {
            return replyFile(new FileInputStream(file), name);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
