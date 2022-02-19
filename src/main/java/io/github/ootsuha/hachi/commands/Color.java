package io.github.ootsuha.hachi.commands;

import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.command.request.*;
import io.github.ootsuha.hachi.data.*;
import io.github.ootsuha.hachi.service.*;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.interactions.commands.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
public final class Color extends HachiSubcommandContainerImpl {
    private static final int HEX_RADIX = 16;
    private static final int HEX_CODE_LENGTH = 7;
    private static final String ROLE_TYPE = "color";
    private final GuildService guildService;
    private final HachiRoleService hachiRoleService;

    @Autowired
    public Color(final GuildService guildService, final HachiRoleService hachiRoleService) {
        super("color", "Sets user color.");
        addSubcommands(Set.class, Remove.class);

        this.guildService = guildService;
        this.hachiRoleService = hachiRoleService;
    }

    private int parseHex(final String s) {
        if (s.length() == HEX_CODE_LENGTH) {
            try {
                return Integer.parseInt(s.substring(1), HEX_RADIX);
            } catch (NumberFormatException ignored) {
            }
        }
        return -1;
    }

    private void removeColorRole(final Guild guild, final User user) {
        var member = guild.getMember(user);
        if (member == null) {
            return;
        }
        for (Role userRole : member.getRoles()) {
            Optional<HachiRole> role = this.hachiRoleService.findByRole(userRole);
            if (role.isPresent() && role.get().type.equals(ROLE_TYPE)) {
                role.get().count--;
                guild.removeRoleFromMember(member, userRole).queue();
                this.hachiRoleService.saveOrDeleteIfUnused(role.get(), userRole);
                return;
            }
        }
    }

    public final class Set extends HachiCommandImpl implements HachiEmbedCommand {
        public Set() {
            super("set", "Sets user color.");
            addOption(OptionType.STRING, "hex", "Hex color code.", true);
        }

        @Override
        public MessageEmbed output(final HachiCommandRequest r) {
            Guild guild = r.getChannel().getGuild();
            var guildData = guildService.findByRequest(r);
            var b = new EmbedBuilder();
            b.setTitle("Color");
            String hex = r.getOptions().getString("hex").toUpperCase();
            if (!hex.startsWith("#")) {
                hex = '#' + hex;
            }
            int color = parseHex(hex);
            if (color == -1) {
                return b.setDescription("Invalid hex code.").build();
            }
            removeColorRole(guild, r.getUser());
            HachiRole hachiRole = hachiRoleService.findByGuildAndName(guild, hex, ROLE_TYPE);
            var role = guild.getRoleById(hachiRole.id);
            assert role != null;
            role.getManager().setColor(color).queue();
            guild.addRoleToMember(r.getUser().getId(), role).queue();
            hachiRole.count++;
            guildService.save(guildData);
            hachiRoleService.save(hachiRole);
            return b.setColor(color).setDescription(String.format("Set color to `%s`.", hex)).build();
        }
    }

    public final class Remove extends HachiCommandImpl implements HachiEmbedCommand {
        public Remove() {
            super("remove", "Resets user color.");
        }

        @Override
        public MessageEmbed output(final HachiCommandRequest r) {
            Guild guild = r.getChannel().getGuild();
            var b = new EmbedBuilder();
            b.setTitle("Color");
            removeColorRole(guild, r.getUser());
            return b.setDescription("Cleared color.").build();
        }
    }
}
