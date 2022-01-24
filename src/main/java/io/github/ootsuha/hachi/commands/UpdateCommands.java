package io.github.ootsuha.hachi.commands;

import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.command.request.*;
import net.dv8tion.jda.api.interactions.commands.build.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

@Component
public final class UpdateCommands extends HachiCommandImpl {
    /**
     * List of all commands.
     */
    private final HachiCommandLoader loader;

    @Autowired public UpdateCommands(final HachiCommandLoader loader) {
        super("updatecommands", "Updates guild commands.");
        setAliases("uc");

        this.loader = loader;
    }

    @Override public void run(final HachiCommandRequest r) {
        List<CommandData> data =
                this.loader.getCommands().stream().map(HachiCommand::getCommandData).collect(Collectors.toList());
        r.getChannel().getGuild().updateCommands().addCommands(data).queue();
        r.reply("Updated commands.").setEphemeral().queue();
    }
}
