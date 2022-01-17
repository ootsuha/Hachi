package io.github.ootsuha.hachi.command.usable;

import io.github.ootsuha.hachi.command.*;
import io.github.ootsuha.hachi.utility.*;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.interactions.commands.*;

import java.util.*;

public final class GitHub extends HachiEmbedCommand {
    /**
     * GitHub repo link.
     */
    private static final String REPO = "https://github.com/ootsuha/Hachi";
    /**
     * Embed to use when no class option given.
     */
    private static final MessageEmbed DEFAULT;

    static {
        EmbedBuilder b = new EmbedBuilder();
        b.setColor(Constant.COLOR);
        b.setTitle("Hachi", REPO);
        b.setDescription("View Hachi's GitHub repository.");
        b.setThumbnail(Constant.ICON_URL);
        DEFAULT = b.build();
    }

    public GitHub() {
        super("github", "Links to Hachi's GitHub repo.");
        addOption(OptionType.STRING, "class", "Link to a specific class. (case sensitive)");
    }

    /**
     * Get a class from its full name, but certain package names can be omitted.
     *
     * @param name name of class
     * @return class if it exists, null otherwise
     */
    private Class<?> getClass(final String name) {
        List<String> omittablePrefixes =
                List.of("", "io.github.ootsuha.hachi.", "io.github.ootsuha.hachi.command.usable");
        try {
            for (String prefix : omittablePrefixes) {
                Class<?> clazz = Class.forName(GitHub.class.getModule(), prefix + name);
                if (clazz != null) {
                    return clazz;
                }
            }
        } catch (Throwable e) {
            return null;
        }
        return null;
    }

    @Override protected MessageEmbed output(final HachiCommandRequest r) {
        if (r.hasOption("class")) {
            Class<?> clazz = getClass(r.getString("class"));
            if (clazz != null) {
                String url = REPO + "/blob/master/src/main/java/" + clazz.getName().replaceAll("\\.", "/") + ".java";
                EmbedBuilder b = new EmbedBuilder();
                b.setColor(Constant.COLOR);
                b.setTitle("Hachi", url);
                b.setDescription(String.format("View `%s` in Hachi's GitHub repository.", clazz.getName()));
                b.setThumbnail(Constant.ICON_URL);
                return b.build();
            } else {
                return DEFAULT;
            }
        } else {
            return DEFAULT;
        }
    }
}
