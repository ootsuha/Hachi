package io.github.ootsuha.hachi.command.usable;

import io.github.ootsuha.hachi.command.*;
import io.github.ootsuha.hachi.command.request.*;
import net.dv8tion.jda.api.interactions.commands.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.net.*;

@Component
public final class Source extends HachiCommandImpl {
    /**
     * Link to source code files.
     */
    private static final String REPO = "https://raw.githubusercontent.com/ootsuha/Hachi/master/src/main/java/";
    private HachiCommandLoader loader;

    public Source() {
        super("source", "Gets the source code for a command.");
        addOption(OptionType.STRING, "name", "Name of the command.", true);
    }

    @Autowired private void setLoader(final HachiCommandLoader loader) {
        this.loader = loader;
    }

    private InputStream getInputStream(final String name) {
        HachiCommand c = this.loader.getCommand(name);
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
        HachiCommandOptions o = r.getOptions();
        String name = o.getString("name").toLowerCase();
        InputStream file = getInputStream(name);
        if (file != null) {
            r.reply(String.format("Source code for `%s`:", name)).complete();
            r.getChannel().sendFile(file, name + ".java").queue();
        } else {
            r.reply(String.format("Command `%s` does not exist.", name)).setEphemeral().queue();
        }
    }
}
