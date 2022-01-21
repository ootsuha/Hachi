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
    /**
     * Default help embed when no command is given.
     */
    private MessageEmbed defaultEmbed;
    private HachiCommandLoader loader;

    public Help() {
        super("help", "Shows information about a command.");
        addOption(OptionType.STRING, "command", "Command name.");
    }

    @Autowired private void setLoader(final HachiCommandLoader loader) {
        this.loader = loader;
    }

    @Autowired private void setDefaultEmbed(final HachiConfig config) {
        EmbedBuilder b = new EmbedBuilder();
        b.setTitle("Hachi");
        b.appendDescription("");
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
