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
    private static final Map<String, BiConsumer<ButtonInteractionEvent, String>> HANDLER_MAP = new HashMap<>();

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

    @Override
    public void onEvent(@NotNull final GenericEvent event) {
        if (event instanceof ButtonInteractionEvent bEvent) {
            Pair<String, String> handlerValue = ButtonRow.parseHandlerAndValue(bEvent.getComponentId());
            if (HANDLER_MAP.containsKey(handlerValue.left())) {
                HANDLER_MAP.get(handlerValue.left()).accept(bEvent, handlerValue.right());
            } else {
                bEvent.reply("This button already expired...").setEphemeral(true).queue();
                bEvent.getMessage().editMessageComponents().queue();
            }
        }
    }
}
