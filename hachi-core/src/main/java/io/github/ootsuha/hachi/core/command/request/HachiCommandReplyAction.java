package io.github.ootsuha.hachi.core.command.request;

import net.dv8tion.jda.api.interactions.components.*;

import javax.annotation.*;
import java.util.*;
import java.util.function.*;

/**
 * Simulates a <code>RestAction</code> for responses to user command requests.
 * (doesn't implement <code>RestAction</code> because i'm lazy)
 */
public interface HachiCommandReplyAction {
    /**
     * Queue the reply to the command request.
     */
    void queue();

    void queue(Consumer<HachiCommandReply> callback);

    /**
     * Reply to the command request and blocks until the reply goes through.
     *
     * @return reply
     */
    HachiCommandReply complete();

    /**
     * Set the request as ephemeral.
     *
     * @return this
     */
    @CheckReturnValue
    HachiCommandReplyAction setEphemeral();

    HachiCommandReplyAction setActionRows(List<ActionRow> rows);

    default HachiCommandReplyAction setActionRow(ActionRow row) {
        return setActionRows(List.of(row));
    }
}
