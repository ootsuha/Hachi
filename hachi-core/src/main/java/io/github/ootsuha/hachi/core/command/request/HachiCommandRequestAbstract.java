package io.github.ootsuha.hachi.core.command.request;

import io.github.ootsuha.hachi.core.command.*;
import lombok.*;
import net.dv8tion.jda.api.entities.*;

/**
 * Abstract class that implements the methods in <code>HachiCommandRequest</code> that will be the same for all
 * implementations.
 */
@RequiredArgsConstructor
@ToString(exclude = { "user", "channel" })
@EqualsAndHashCode
public abstract class HachiCommandRequestAbstract implements HachiCommandRequest {
    @Getter
    private final HachiCommand command;
    @Getter
    private final HachiCommandOptions options;
    @Getter
    private final User user;
    @Getter
    private final TextChannel channel;
}
