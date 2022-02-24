package io.github.ootsuha.hachi.core.command.request;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.interactions.components.*;

import javax.annotation.*;
import java.io.*;
import java.util.*;
import java.util.function.*;

/**
 * Wraps <code>MessageAction</code>s and <code>ReplyCallbackAction</code>s to be used interchangeably.
 */
public interface HachiCommandReplyAction {
    void queue();

    void queue(Consumer<HachiCommandReply> callback);

    HachiCommandReply complete();

    HachiCommandReplyAction setEmbed(MessageEmbed embed);

    @CheckReturnValue
    HachiCommandReplyAction setEphemeral();

    HachiCommandReplyAction setActionRows(List<ActionRow> rows);

    default HachiCommandReplyAction setActionRow(ActionRow row) {
        return setActionRows(List.of(row));
    }

    HachiCommandReplyAction addFile(InputStream data, String name);

    default HachiCommandReplyAction addFile(final byte[] data, final String name) {
        return addFile(new ByteArrayInputStream(data), name);
    }

    default HachiCommandReplyAction addFile(final File file, final String name) {
        try {
            return addFile(new FileInputStream(file), name);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
