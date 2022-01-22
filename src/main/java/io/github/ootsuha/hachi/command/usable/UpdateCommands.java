package io.github.ootsuha.hachi.command.usable;

import io.github.ootsuha.hachi.command.*;
import io.github.ootsuha.hachi.command.request.*;
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
