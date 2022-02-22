package io.github.ootsuha.hachi.commands;

import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.command.request.*;
import io.github.ootsuha.hachi.core.command.specialized.*;
import io.github.ootsuha.hachi.service.*;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.interactions.commands.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
public final class Alias extends HachiSubcommandContainerImpl {
    private final UserService userService;

    @Autowired
    public Alias(final UserService userService) {
        super("alias", "Set an alias for a command.");
        addSubcommands(Add.class, Remove.class, List.class);
        this.userService = userService;
    }

    public final class Add extends HachiCommandImpl implements HachiEmbedCommand {
        public Add() {
            super("add", "Add a new alias.");
            addOption(OptionType.STRING, "alias", "The text to be aliased.", true);
            addOption(OptionType.STRING, "replacement", "The text to replace the alias.", true);
        }

        @Override
        public MessageEmbed output(final HachiCommandRequest r) {
            var options = r.getOptions();
            var userData = userService.findByRequest(r);
            var b = new EmbedBuilder();
            b.setTitle("Alias");
            String alias = options.getString("alias");
            String replacement = options.getString("replacement");
            userData.getAliasMap().put(alias, replacement);
            b.setDescription(String.format("Aliased `%s` to `%s`.", alias, replacement));
            userService.save(userData);
            return b.build();
        }
    }

    public final class Remove extends HachiCommandImpl implements HachiEmbedCommand {
        public Remove() {
            super("remove", "Removes an alias.");
            addOption(OptionType.STRING, "alias", "The alias to remove.", true);
        }

        @Override
        public MessageEmbed output(final HachiCommandRequest r) {
            var options = r.getOptions();
            var userData = userService.findByRequest(r);
            var b = new EmbedBuilder();
            b.setTitle("Alias");
            String alias = options.getString("alias");
            userData.getAliasMap().remove(alias);
            b.setDescription(String.format("Removed alias `%s`.", alias));
            userService.save(userData);
            return b.build();
        }
    }

    public final class List extends HachiCommandImpl implements HachiEmbedCommand {
        public List() {
            super("list", "Lists all aliases.");
        }

        @Override
        public MessageEmbed output(final HachiCommandRequest r) {
            var userData = userService.findByRequest(r);
            var b = new EmbedBuilder();
            b.setTitle("Alias");
            for (Map.Entry<String, String> alias : userData.getAliasMap().entrySet()) {
                b.appendDescription(String.format("`%s` -> `%s`%n", alias.getKey(), alias.getValue()));
            }
            return b.build();
        }
    }
}
