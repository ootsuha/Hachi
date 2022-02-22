package io.github.ootsuha.hachi.core;

import io.github.ootsuha.hachi.core.command.loader.*;
import io.github.ootsuha.hachi.core.listener.*;
import io.github.ootsuha.hachi.core.parser.*;
import lombok.*;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.requests.restaction.*;

import javax.security.auth.login.*;
import java.util.*;

/**
 * Handles the setup of JDA.
 */
@RequiredArgsConstructor
public class Hachi {
    private final HachiConfig config;
    private final List<EventListener> listeners = new ArrayList<>();
    @Getter
    private JDA jda;

    public Hachi(final HachiConfig config, final HachiCommandLoader loader, final Parser parser) {
        this.config = config;
        this.listeners.add(new MessageListener(parser));
        this.listeners.add(new SlashCommandListener(loader));
        this.listeners.add(new SelectMenuListener());
        this.listeners.add(new ButtonListener());
    }

    /**
     * Creates the JDA instance and connects to Discord.
     */
    public void login() throws LoginException, InterruptedException {
        JDABuilder builder = JDABuilder.createDefault(this.config.getToken());
        for (EventListener listener : listeners) {
            builder.addEventListeners(listener);
        }
        this.jda = builder.build().awaitReady();

        MessageAction.setDefaultMentionRepliedUser(false);
    }

    /**
     * Adds an event listener.
     *
     * @param listener listener
     */
    public void addListener(final EventListener listener) {
        assert this.jda == null;
        this.listeners.add(listener);
    }

    /**
     * Gets the event listener of a certain type.
     *
     * @param type type of event listener to get
     * @return event listener or null
     */
    public EventListener getListener(final Class<? extends EventListener> type) {
        for (EventListener l : this.listeners) {
            if (l.getClass().equals(type)) {
                return l;
            }
        }
        return null;
    }
}
