package io.github.ootsuha.hachi.commands;

import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.command.request.*;
import io.github.ootsuha.hachi.core.command.specialized.*;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.interactions.commands.*;
import org.springframework.stereotype.*;

@Component
public final class Avatar extends HachiCommandImpl implements HachiEmbedCommand {
    public Avatar() {
        super("avatar", "Gets a user's avatar.");
        addOption(OptionType.USER, "user", "User to get the avatar of. Defaults to your avatar");
    }

    @Override
    public MessageEmbed output(final HachiCommandRequest r) {
        var o = r.getOptions();
        User u = o.hasOption("user") ? o.getUser("user") : r.getUser();
        var b = new EmbedBuilder();
        b.setTitle(u.getAsTag());
        b.setImage(u.getEffectiveAvatarUrl() + "?size=4096");
        return b.build();
    }
}
