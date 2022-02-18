package io.github.ootsuha.hachi.commands;

import io.github.ootsuha.hachi.core.*;
import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.command.request.*;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Component
public final class Invite extends HachiCommandImpl implements HachiEmbedCommand {
    private final MessageEmbed embed;

    @Autowired
    public Invite(final HachiConfig config) {
        super("invite", "Gets Hachi's invite link.");

        String url = config.getInviteLink();
        EmbedBuilder b = new EmbedBuilder();
        b.setTitle("Invite", url);
        b.setDescription("Invite Hachi to another server.");
        b.setColor(config.getEmbedColor());
        b.setThumbnail(config.getIconUrl());
        this.embed = b.build();
    }

    @Override
    public MessageEmbed output(final HachiCommandRequest r) {
        return this.embed;
    }
}
