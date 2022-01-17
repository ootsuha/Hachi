package io.github.ootsuha.hachi;

import io.github.ootsuha.hachi.command.*;
import io.github.ootsuha.hachi.listener.*;
import io.github.ootsuha.hachi.utility.*;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.requests.restaction.*;

import javax.security.auth.login.*;
import java.util.*;

public final class Hachi {
    /**
     * Private constructor so class cannot be instantiated.
     */
    private Hachi() {
    }

    /**
     * Main method.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) throws LoginException, InterruptedException {
        Set<Class<?>> commands = HachiCommandClassSet.getSet();
        HachiCommandLoader.load(commands);

        JDABuilder builder = JDABuilder.createDefault(Constant.DISCORD_TOKEN);
        builder.addEventListeners(new SlashCommandListener());
        builder.addEventListeners(new MessageListener());
        JDA api = builder.build().awaitReady();

        MessageAction.setDefaultMentionRepliedUser(false);
        // close jda api immediately when closing program
        Runtime.getRuntime().addShutdownHook(new Thread(api::shutdownNow));
    }
}
