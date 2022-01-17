package io.github.ootsuha.hachi.command.usable;

import io.github.ootsuha.hachi.command.*;
import io.github.ootsuha.hachi.utility.*;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;

public final class Invite extends HachiEmbedCommand {
    public Invite() {
        super("invite", "Gets Hachi's invite link.");
    }

    @Override protected MessageEmbed output(final HachiCommandRequest r) {
        JDA jda = r.getUser().getJDA();
        String url = jda.setRequiredScopes("applications.commands").getInviteUrl(Permission.ADMINISTRATOR);
        EmbedBuilder b = new EmbedBuilder();
        String avatar = jda.getSelfUser().getAvatarUrl();
        b.setColor(Constant.COLOR);
        b.setTitle("Invite", url);
        b.setDescription("Invite Hachi to another server.");
        b.setThumbnail(avatar);
        return b.build();
    }
}
