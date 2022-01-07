package io.github.ootsuha.hachi;

import io.github.ootsuha.hachi.command.HachiCommandClassSet;
import io.github.ootsuha.hachi.command.HachiCommandLoader;
import io.github.ootsuha.hachi.listener.MessageListener;
import io.github.ootsuha.hachi.listener.SlashCommandListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;
import java.util.Set;

public final class Hachi {
    /**
     * Private constructor so class cannot be instantiated.
     */
    private Hachi() {
    }

    /**
     * Main method.
     * @param args command line arguments
     */
    public static void main(final String[] args) throws LoginException {
        Set<Class<?>> commands = HachiCommandClassSet.getSet();
        HachiCommandLoader.load(commands);

        JDABuilder builder = JDABuilder.createDefault(Constant.DISCORD_TOKEN);

        builder.addEventListeners(new SlashCommandListener());
        builder.addEventListeners(new MessageListener());

        JDA api = builder.build();
        // close jda api immediately when closing program
        Runtime.getRuntime().addShutdownHook(new Thread(api::shutdownNow));
    }
}
