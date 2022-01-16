package io.github.ootsuha.hachi.command.usable;

import io.github.ootsuha.hachi.command.HachiCommandRequest;
import io.github.ootsuha.hachi.command.HachiStringCommand;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class Echo extends HachiStringCommand {
    /**
     * Initialize command settings.
     */
    public Echo() {
        super("echo", "Repeats what is given.");
        addOption(OptionType.STRING, "text", "Message to say.", true);
    }

    @NotNull @Override protected String output(final HachiCommandRequest r) {
        return Objects.requireNonNull(r.getString("text"));
    }
}
