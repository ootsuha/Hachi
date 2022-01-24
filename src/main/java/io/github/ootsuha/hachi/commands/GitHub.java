package io.github.ootsuha.hachi.commands;

import io.github.ootsuha.hachi.core.*;
import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.command.request.*;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.interactions.commands.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
public final class GitHub extends HachiEmbedCommand {
    /**
     * Embed to use when no class option given.
     */
    private MessageEmbed defaultEmbed;
    private HachiConfig config;

    public GitHub() {
        super("github", "Links to Hachi's GitHub repo.");
        addOption(OptionType.STRING, "class", "Link to a specific class. (case sensitive)");
    }

    @Autowired private void setDefaultEmbed(final HachiConfig config) {
        this.config = config;
        EmbedBuilder b = new EmbedBuilder();
        b.setTitle("Hachi", config.getGithubRepo());
        b.setDescription("View Hachi's GitHub repository.");
        b.setColor(config.getEmbedColor());
        b.setThumbnail(config.getIconUrl());
        this.defaultEmbed = b.build();
    }

    /**
     * Get a class from its full name, but certain package names can be omitted.
     *
     * @param name name of class
     * @return class if it exists, null otherwise
     */
    private Class<?> getClass(final String name) {
        List<String> omittablePrefixes = List.of("", "io.github.ootsuha.hachi.", "io.github.ootsuha.hachi.commands.");
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
        HachiCommandOptions o = r.getOptions();
        if (o.hasOption("class")) {
            Class<?> clazz = getClass(r.getOptions().getString("class"));
            if (clazz != null) {
                String url = this.config.getGithubRepo() + "/blob/master/src/main/java/" + clazz.getName()
                        .replaceAll("\\.", "/") + ".java";
                EmbedBuilder b = new EmbedBuilder();
                b.setTitle("Hachi", url);
                b.setDescription(String.format("View `%s` in Hachi's GitHub repository.", clazz.getName()));
                b.setColor(this.config.getEmbedColor());
                b.setThumbnail(this.config.getIconUrl());
                return b.build();
            } else {
                return this.defaultEmbed;
            }
        } else {
            return this.defaultEmbed;
        }
    }
}
