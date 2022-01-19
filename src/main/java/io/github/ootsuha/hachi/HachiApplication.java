package io.github.ootsuha.hachi;

import io.github.ootsuha.hachi.command.*;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.requests.restaction.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.*;

import javax.security.auth.login.*;
import java.util.*;

/**
 * Main class.
 */
@SpringBootApplication
public class HachiApplication {
    /**
     * Main method.
     *
     * @param args command line args
     */
    public static void main(final String[] args) {
        SpringApplication.run(HachiApplication.class, args);
    }

    /**
     * Load all commands.
     *
     * @param loader   command loader
     * @param commands list of commands
     */
    @Autowired public void loadCommands(final HachiCommandLoader loader, final List<HachiCommand> commands) {
        for (HachiCommand command : commands) {
            loader.loadCommand(command);
        }
    }

    /**
     * Creates the JDA instance.
     *
     * @param config    bot config
     * @param listeners event listeners to add
     * @return jda instance
     */
    @Bean @Autowired public JDA login(final HachiConfig config, final List<EventListener> listeners)
            throws LoginException, InterruptedException {
        JDABuilder builder = JDABuilder.createDefault(config.getToken());
        for (EventListener listener : listeners) {
            builder.addEventListeners(listener);
        }
        JDA api = builder.build().awaitReady();

        MessageAction.setDefaultMentionRepliedUser(false);
        // close jda api immediately when closing program
        Runtime.getRuntime().addShutdownHook(new Thread(api::shutdownNow));
        return api;
    }

}
