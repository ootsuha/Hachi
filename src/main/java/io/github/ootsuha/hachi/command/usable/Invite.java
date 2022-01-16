package io.github.ootsuha.hachi.command.usable;

import io.github.ootsuha.hachi.Constant;
import io.github.ootsuha.hachi.command.HachiCommandRequest;
import io.github.ootsuha.hachi.command.HachiEmbedCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;

import javax.annotation.Nonnull;

public final class Invite extends HachiEmbedCommand {
    /**
     * Initialize command settings.
     */
    public Invite() {
        super("invite", "Gets Hachi's invite link.");
    }

    @Nonnull @Override protected MessageEmbed output(final HachiCommandRequest r) {
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
