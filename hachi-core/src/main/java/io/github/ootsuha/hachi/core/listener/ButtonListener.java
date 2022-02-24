package io.github.ootsuha.hachi.core.listener;

import io.github.ootsuha.hachi.core.*;
import io.github.ootsuha.hachi.core.types.*;
import net.dv8tion.jda.api.events.*;
import net.dv8tion.jda.api.events.interaction.component.*;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.function.*;

/**
 * Handles <code>ButtonInteractionEvent</code>s.
 */
public final class ButtonListener implements EventListener {
    public static final String USER_CHECK_HANDLER = "forUser";
    private static final Map<String, BiConsumer<ButtonInteractionEvent, String>> HANDLER_MAP = new HashMap<>();

    static {
        ButtonListener.addHandler(USER_CHECK_HANDLER, (bEvent, value) -> {
            var pair = ButtonRow.parseFirst(value);
            var id = pair.left();
            var nextHandlerAndValue = pair.right();
            if (bEvent.getUser().getId().equals(id)) {
                Pair<String, String> handlerValue = ButtonRow.parseFirst(nextHandlerAndValue);
                if (HANDLER_MAP.containsKey(handlerValue.left())) {
                    handle(bEvent, handlerValue.left(), handlerValue.right());
                } else {
                    bEvent.reply("This button already expired...").setEphemeral(true).queue();
                    bEvent.getMessage().editMessageComponents().queue();
                }
            } else {
                bEvent.reply("You cannot use this button.").setEphemeral(true).queue();
            }
        });
    }

    public static void addHandler(final String handler, final BiConsumer<ButtonInteractionEvent, String> callback) {
        assert !HANDLER_MAP.containsKey(handler);
        HANDLER_MAP.put(handler, callback);
    }

    public static void addRow(final ButtonRow row, final BiConsumer<ButtonInteractionEvent, String> callback) {
        assert !HANDLER_MAP.containsKey(row.getHandler());
        HANDLER_MAP.put(row.getHandler(), callback);
    }

    public static void removeRow(final ButtonRow row) {
        assert HANDLER_MAP.containsKey(row.getHandler());
        HANDLER_MAP.remove(row.getHandler());
    }

    public static void handle(final ButtonInteractionEvent bEvent, final String handler, final String value) {
        HANDLER_MAP.get(handler).accept(bEvent, value);
    }

    @Override
    public void onEvent(@NotNull final GenericEvent event) {
        if (event instanceof ButtonInteractionEvent bEvent) {
            Pair<String, String> handlerValue = ButtonRow.parseFirst(bEvent.getComponentId());
            if (HANDLER_MAP.containsKey(handlerValue.left())) {
                handle(bEvent, handlerValue.left(), handlerValue.right());
            } else {
                bEvent.reply("This button already expired...").setEphemeral(true).queue();
                bEvent.getMessage().editMessageComponents().queue();
            }
        }
    }
}
