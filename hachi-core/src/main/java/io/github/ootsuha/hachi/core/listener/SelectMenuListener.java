package io.github.ootsuha.hachi.core.listener;

import lombok.*;
import net.dv8tion.jda.api.events.*;
import net.dv8tion.jda.api.events.interaction.component.*;
import net.dv8tion.jda.api.hooks.EventListener;

import javax.annotation.*;
import java.util.*;
import java.util.function.*;

@AllArgsConstructor
public final class SelectMenuListener implements EventListener {
    private static final Map<String, Consumer<SelectMenuInteractionEvent>> MAP = new HashMap<>();

    public static void addMenu(final String id, final Consumer<SelectMenuInteractionEvent> callback) {
        assert !MAP.containsKey(id);
        MAP.put(id, callback);
    }

    public static void removeMenu(final String id) {
        assert MAP.containsKey(id);
        MAP.remove(id);
    }

    @Override
    public void onEvent(@Nonnull final GenericEvent event) {
        if (event instanceof SelectMenuInteractionEvent sEvent) {
            String id = sEvent.getComponentId();
            if (MAP.containsKey(id)) {
                MAP.get(id).accept(sEvent);
            } else {
                sEvent.reply("This select menu already expired...").setEphemeral(true).queue();
                sEvent.getMessage().editMessageComponents().queue();
            }
        }
    }
}
