package io.github.ootsuha.hachi.commands;

import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.command.loader.*;
import io.github.ootsuha.hachi.core.command.request.*;
import net.dv8tion.jda.api.interactions.commands.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.io.*;

@Component
public final class Source extends HachiCommandImpl {
    private final HachiCommandLoader loader;

    @Autowired
    public Source(final HachiCommandLoader loader) {
        super("source", "Gets the source code for a command.");
        addOption(OptionType.STRING, "name", "Name of the command.", true);
        this.loader = loader;
    }

    private File getFile(final String name) {
        HachiCommand c = this.loader.getCommand(name);
        if (c == null) {
            return null;
        }
        String path = c.getClass().getName().replaceAll("\\.", "/");
        File f = new File("./src/main/java/" + path + ".java");
        if (f.canRead()) {
            return f;
        }
        return null;
    }

    @Override
    public void run(final HachiCommandRequest r) {
        HachiCommandOptions o = r.getOptions();
        String name = o.getString("name").toLowerCase();
        File file = getFile(name);
        if (file != null) {
            r.reply(String.format("Source code for `%s`:", name)).addFile(file, name + ".java").queue();
        } else {
            r.reply(String.format("Command `%s` does not exist.", name)).setEphemeral().queue();
        }
    }
}
