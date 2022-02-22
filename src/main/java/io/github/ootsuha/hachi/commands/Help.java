package io.github.ootsuha.hachi.commands;

import edu.umd.cs.findbugs.annotations.*;
import io.github.ootsuha.hachi.core.*;
import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.command.loader.*;
import io.github.ootsuha.hachi.core.command.request.*;
import io.github.ootsuha.hachi.core.command.specialized.*;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.interactions.commands.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
public final class Help extends HachiCommandImpl implements HachiSelectMenuCommand {
    private static final String S1 = "Using Hachi";
    private static final String S2 = "Command Information";
    private static final String S3 = "Aliases";
    private static final List<String> SELECTIONS = List.of(S1, S2, S3);
    private final HachiCommandLoader loader;
    /**
     * Default help embeds when no command is given.
     */
    private Map<String, MessageEmbed> defaultEmbeds;

    @Autowired
    public Help(final HachiCommandLoader loader) {
        super("help", "Shows information about a command.");
        addOption(OptionType.STRING, "command", "Command name.");
        setAliases("h");

        this.loader = loader;
    }

    @SuppressFBWarnings("VA_FORMAT_STRING_USES_NEWLINE")
    @Autowired
    private void setDefaultEmbed(final HachiConfig config) {
        var b = new EmbedBuilder();
        this.defaultEmbeds = new HashMap<>();
        b.setTitle("Hachi");
        b.setColor(config.getEmbedColor());
        b.setThumbnail(config.getIconUrl());
        b.addField(S1, String.format("""
                You can use commands with slash commands.
                `    /command                    `
                `    /command option1 option2    `

                You can also use commands with a message.
                Hachi's prefix is "%s".
                `    %<scommand                    `
                `    %<scommand option1 option2    `
                `    %<scommand.subcommand         `
                `    %<scommand.group.subcommand   `
                """, config.getPrefix()), false);
        this.defaultEmbeds.put(S1, b.build());
        b.clear();
        b.setTitle("Hachi");
        b.setColor(config.getEmbedColor());
        b.setThumbnail(config.getIconUrl());
        b.addField(S2, """
                For a list of all commands, use `/commands`.
                For more information about a command, use `/help <command>`.
                To view Hachi's GitHub repository, use `/github`.
                """, false);
        this.defaultEmbeds.put(S2, b.build());
        b.clear();
        b.setTitle("Hachi");
        b.setColor(config.getEmbedColor());
        b.setThumbnail(config.getIconUrl());
        b.addField(S3, String.format("""
                Some commands have aliases, which can be used instead of the full name in messages.
                For example, `%shelp` is identical to `%<sh`.
                You can see the aliases of a command with `/help <command>`.

                You can also define custom aliases using `/alias`.
                Custom aliases can be used at the start of a message or surrounded with `%%`.
                `    %<salias.set p ping           `
                `    %<sp                   (%<sping)`
                `    %<secho %%p%%       (%<secho ping)`
                """, config.getPrefix()), false);
        this.defaultEmbeds.put(S3, b.build());
    }

    @Override
    public MessageEmbed output(final HachiCommandRequest r, final String selection) {
        HachiCommandOptions o = r.getOptions();
        if (o.hasOption("command")) {
            HachiCommand c = this.loader.getCommand(o.getString("command"));
            if (c != null) {
                return c.getHelpEmbed();
            }
        }
        return this.defaultEmbeds.get(selection);
    }

    @Override
    public List<String> menuItems(final HachiCommandRequest r) {
        return r.getOptions().hasOption("command") ? List.of() : SELECTIONS;
    }
}
