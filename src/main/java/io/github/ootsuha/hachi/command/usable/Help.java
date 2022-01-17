package io.github.ootsuha.hachi.command.usable;

import io.github.ootsuha.hachi.command.*;
import io.github.ootsuha.hachi.utility.*;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.interactions.commands.*;

public final class Help extends HachiEmbedCommand {
    /**
     * Default help embed.
     */
    private static final MessageEmbed DEFAULT;

    static {
        EmbedBuilder b = new EmbedBuilder();
        b.setTitle("Hachi");
        b.appendDescription("");
        b.setColor(Constant.COLOR);
        b.setThumbnail(Constant.ICON_URL);
        DEFAULT = b.build();
    }

    public Help() {
        super("help", "Shows information about a command.");
        addOption(OptionType.STRING, "command", "Command name.");
    }

    @Override protected MessageEmbed output(final HachiCommandRequest r) {
        if (r.hasOption("command")) {
            HachiCommand c = HachiCommandLoader.getCommand(r.getString("command"));
            if (c != null) {
                return c.getHelpEmbed();
            }
        }
        return DEFAULT;
    }
}
