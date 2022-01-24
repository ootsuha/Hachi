package io.github.ootsuha.hachi.core.command.request;

import javax.annotation.*;

/**
 * Simulates a <code>RestAction</code> for responses to user command requests.
 * (doesn't implement <code>RestAction</code> because i'm lazy)
 */
public interface HachiCommandReplyAction {
    /**
     * Queue the reply to the command request.
     */
    void queue();

    /**
     * Reply to the command request and blocks until the reply goes through.
     */
    void complete();

    /**
     * Set the request as ephemeral.
     *
     * @return this
     */
    @CheckReturnValue HachiCommandReplyAction setEphemeral();
}
