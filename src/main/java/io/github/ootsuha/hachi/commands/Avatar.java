package io.github.ootsuha.hachi.commands;

import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.command.request.*;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.interactions.commands.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.net.*;

@Component
public final class Avatar extends HachiCommandImpl {
    public Avatar() {
        super("avatar", "Gets a user's avatar.");
        addOption(OptionType.USER, "user", "User to get the avatar of. Defaults to your avatar.");
    }

    private static String getExtension(final String name) {
        return name.substring(name.lastIndexOf('.'));
    }

    @Override
    public void run(final HachiCommandRequest r) {
        var o = r.getOptions();
        User u = o.hasOption("user") ? o.getUser("user") : r.getUser();
        var b = new EmbedBuilder();
        b.setTitle(u.getAsTag());
        String url = u.getEffectiveAvatarUrl();
        String fileName = u.getName() + getExtension(url);
        b.setImage("attachment://" + fileName);
        try {
            r.replyFile(new URL(url + "?size=4096").openStream(), fileName).setEmbed(b.build()).queue();
        } catch (IOException e) {
            r.reply(e.getMessage()).queue();
        }
    }
}
