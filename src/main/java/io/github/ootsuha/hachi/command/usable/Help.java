package io.github.ootsuha.hachi.command.usable;

import io.github.ootsuha.hachi.*;
import io.github.ootsuha.hachi.command.*;
import io.github.ootsuha.hachi.command.request.*;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.interactions.commands.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Component
public final class Help extends HachiEmbedCommand {
    private final HachiCommandLoader loader;
    /**
     * Default help embed when no command is given.
     */
    private MessageEmbed defaultEmbed;

    @Autowired public Help(final HachiCommandLoader loader) {
        super("help", "Shows information about a command.");
        addOption(OptionType.STRING, "command", "Command name.");

        this.loader = loader;
    }

    @Autowired private void setDefaultEmbed(final HachiConfig config) {
        EmbedBuilder b = new EmbedBuilder();
        b.setTitle("Hachi");
        b.addField("Using Hachi", String.format("""
                You can use commands with slash commands.
                `    /command                    `
                `    /command option1 option2    `

                You can also use commands with a message.
                Hachi's prefix is "%s".
                `    %<scommand                    `
                `    %<scommand option1 option2    `
                """, config.getPrefix()), false);
        b.addField("Command Information", """
                For a list of all commands, use `/commands`.
                For more information about a command, use `/help <name>`.
                """, false);
        b.setColor(config.getEmbedColor());
        b.setThumbnail(config.getIconUrl());
        this.defaultEmbed = b.build();
    }

    @Override protected MessageEmbed output(final HachiCommandRequest r) {
        HachiCommandOptions o = r.getOptions();
        if (o.hasOption("command")) {
            HachiCommand c = this.loader.getCommand(o.getString("command"));
            if (c != null) {
                return c.getHelpEmbed();
            }
        }
        return this.defaultEmbed;
    }
}
