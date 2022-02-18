package io.github.ootsuha.hachi.commands;

import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.command.request.*;
import io.github.ootsuha.hachi.service.*;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.interactions.commands.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Component
public final class Alias extends HachiCommandImpl implements HachiEmbedCommand {
    private final UserService userService;

    @Autowired public Alias(final UserService userService) {
        super("alias", "Set an alias for a command.");
        addOption(OptionType.STRING, "alias", "The text to be aliased.", true);
        addOption(OptionType.STRING, "replacement", "The text to replace the alias. Ignore to remove alias.", false);

        this.userService = userService;
    }

    @Override public MessageEmbed output(final HachiCommandRequest r) {
        var options = r.getOptions();
        var userData = this.userService.findByRequest(r);
        var b = new EmbedBuilder();
        b.setTitle("Alias");
        String alias = options.getString("alias");
        if (options.hasOption("replacement")) {
            String replacement = options.getString("replacement");
            userData.getAliasMap().put(alias, replacement);
            b.setDescription(String.format("Aliased `%s` to `%s`.", alias, replacement));
        } else {
            userData.getAliasMap().remove(alias);
            b.setDescription(String.format("Removed alias `%s`.", alias));
        }
        this.userService.save(userData);
        return b.build();
    }
}
