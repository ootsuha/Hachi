package io.github.ootsuha.hachi;

import io.github.cdimascio.dotenv.Dotenv;

public final class Constant {
    /**
     * Discord bot token.
     */
    public static final String DISCORD_TOKEN;

    static {
        Dotenv dotenv = Dotenv.load();
        DISCORD_TOKEN = dotenv.get("DISCORD_TOKEN");
    }

    /**
     * Private constructor so class cannot be instantiated.
     */
    private Constant() {
    }

}
