package io.github.ootsuha.hachi.commands;

import io.github.ootsuha.hachi.core.*;
import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.command.request.*;
import io.github.ootsuha.hachi.core.listener.*;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.interactions.commands.*;
import org.springframework.stereotype.*;

@SuppressWarnings("checkstyle:MagicNumber")
@Component
public final class Counter extends HachiCommandImpl {
    private static final String COUNTER_HANDLER = "counter";

    public Counter() {
        super("counter", "Creates a counter message.");
        addOption(OptionType.STRING, "name", "Title of the counter.");
        addOption(OptionType.BOOLEAN, "private", "Do not let other users update the counter.");
        addOption(OptionType.INTEGER, "start", "Number to start the counter at.");

        ButtonListener.addHandler(COUNTER_HANDLER, (bEvent, value) -> {
            var embedList = bEvent.getMessage().getEmbeds();
            if (embedList.isEmpty()) {
                return;
            }
            var embed = embedList.get(0);
            var desc = embed.getDescription();
            if (desc == null) {
                return;
            }
            try {
                int n = Integer.parseInt(desc);
                int newN = switch (value) {
                    case "-10" -> n - 10;
                    case "-1" -> n - 1;
                    case "0" -> 0;
                    case "+1" -> n + 1;
                    case "+10" -> n + 10;
                    default -> n;
                };
                var newEmbed = new EmbedBuilder(embed);
                newEmbed.setDescription(String.valueOf(newN));
                bEvent.editMessageEmbeds(newEmbed.build()).queue();
            } catch (NumberFormatException ignored) {
            }
        });
    }

    @Override
    public void run(final HachiCommandRequest r) {
        var o = r.getOptions();
        var b = new EmbedBuilder();
        if (o.hasOption("name")) {
            b.setTitle(o.getString("name"));
        } else {
            b.setTitle(String.format("%s", r.getUser().getAsTag()));
        }
        var start = o.hasOption("start") ? o.getInteger("start") : 0;
        b.setDescription(String.valueOf(start));
        var handler = COUNTER_HANDLER;
        if (o.hasOption("private") && o.getBoolean("private")) {
            handler = ButtonRow.joinWithDelimiter(ButtonListener.USER_CHECK_HANDLER, r.getUser().getId(),
                    COUNTER_HANDLER);
        }
        var row = new ButtonRow(handler, 5);
        row.setValues("-10", "-1", "0", "+1", "+10");
        row.setLabels("-10", "-1", "Reset", "+1", "+10");
        r.replyEmbed(b.build()).setActionRow(row.toRow()).queue();
    }
}
