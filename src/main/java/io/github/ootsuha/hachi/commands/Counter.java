package io.github.ootsuha.hachi.commands;

import io.github.ootsuha.hachi.core.*;
import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.command.request.*;
import io.github.ootsuha.hachi.core.listener.*;
import org.springframework.stereotype.*;

@SuppressWarnings("checkstyle:MagicNumber")
@Component
public final class Counter extends HachiCommandImpl {
    static {
        ButtonListener.addHandler("counter", (bEvent, value) -> {
            int n = Integer.parseInt(bEvent.getMessage().getContentRaw());
            int newN = switch (value) {
                case "-10" -> n - 10;
                case "-1" -> n - 1;
                case "0" -> 0;
                case "+1" -> n + 1;
                case "+10" -> n + 10;
                default -> n;
            };
            bEvent.editMessage("" + newN).queue();
        });
    }

    public Counter() {
        super("counter", "Creates a counter message.");
    }

    @Override
    public void run(final HachiCommandRequest r) {
        var row = new ButtonRow("counter", 5);
        row.setValues("-10", "-1", "0", "+1", "+10");
        row.setLabels("-10", "-1", "Reset", "+1", "+10");
        r.reply("0").setActionRow(row.toRow()).queue();
    }
}
