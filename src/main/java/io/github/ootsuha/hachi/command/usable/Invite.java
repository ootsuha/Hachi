package io.github.ootsuha.hachi.command.usable;

import io.github.ootsuha.hachi.*;
import io.github.ootsuha.hachi.command.*;
import io.github.ootsuha.hachi.command.request.*;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Component
public final class Invite extends HachiEmbedCommand {
    private MessageEmbed embed;

    @Autowired public Invite(final HachiConfig config) {
        super("invite", "Gets Hachi's invite link.");

        String url = config.getInviteLink();
        EmbedBuilder b = new EmbedBuilder();
        b.setTitle("Invite", url);
        b.setDescription("Invite Hachi to another server.");
        b.setColor(config.getEmbedColor());
        b.setThumbnail(config.getIconUrl());
        this.embed = b.build();
    }

    @Override protected MessageEmbed output(final HachiCommandRequest r) {
        return this.embed;
    }
}
