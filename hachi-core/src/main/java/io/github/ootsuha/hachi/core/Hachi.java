package io.github.ootsuha.hachi.core;

import io.github.ootsuha.hachi.core.command.*;
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
    private final HachiCommandLoader loader;
    private final Parser parser;
    @Getter
    private JDA jda;

    /**
     * Creates the JDA instance and connects to Discord.
     */
    public void login() throws LoginException, InterruptedException {
        JDABuilder builder = JDABuilder.createDefault(this.config.getToken());
        List<EventListener> listeners =
                List.of(new MessageListener(this.parser), new SlashCommandListener(this.loader));
        for (EventListener listener : listeners) {
            builder.addEventListeners(listener);
        }
        this.jda = builder.build().awaitReady();

        MessageAction.setDefaultMentionRepliedUser(false);
    }
}
