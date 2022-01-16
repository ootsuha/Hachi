package io.github.ootsuha.hachi.command.usable;

import io.github.ootsuha.hachi.command.HachiCommand;
import io.github.ootsuha.hachi.command.HachiCommandImpl;
import io.github.ootsuha.hachi.command.HachiCommandLoader;
import io.github.ootsuha.hachi.command.HachiCommandRequest;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

public final class Source extends HachiCommandImpl {
    /**
     * Link.
     */
    public static final String REPO = "https://raw.githubusercontent.com/ootsuha/Hachi/master/src/main/java/";

    /**
     * Initialize command settings.
     */
    public Source() {
        super("source", "Gets the source code for a command.");
        addOption(OptionType.STRING, "name", "Name of the command", true);
    }

    private InputStream getInputStream(final String name) {
        HachiCommand c = HachiCommandLoader.getCommand(name);
        if (c == null) {
            return null;
        }
        String path = c.getClass().getName().replaceAll("\\.", "/");
        try {
            return new URL(REPO + path + ".java").openStream();
        } catch (IOException e) {
            return null;
        }
    }

    @Override public void run(final HachiCommandRequest r) {
        String name = Objects.requireNonNull(r.getString("name")).toLowerCase();
        InputStream file = getInputStream(name);
        if (file != null) {
            r.reply(String.format("Source code for `%s`:", name));
            r.getChannel().sendFile(file, name + ".java").queue();
        } else {
            r.replyEphemeral(String.format("Command `%s` does not exist.", name));
        }
    }
}
