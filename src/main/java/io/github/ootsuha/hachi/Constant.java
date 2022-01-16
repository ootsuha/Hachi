package io.github.ootsuha.hachi;

import io.github.cdimascio.dotenv.Dotenv;

public final class Constant {
    /**
     * Discord bot token.
     */
    public static final String DISCORD_TOKEN;
    /**
     * Prefix for message commands.
     */
    public static final String BOT_PREFIX;
    /**
     * Color to use for certain embeds.
     */
    public static final int COLOR;

    static {
        Dotenv dotenv = Dotenv.load();
        DISCORD_TOKEN = dotenv.get("DISCORD_TOKEN");
        BOT_PREFIX = dotenv.get("BOT_PREFIX");
        COLOR = Integer.parseInt(dotenv.get("COLOR"));
    }

    /**
     * Private constructor so class cannot be instantiated.
     */
    private Constant() {
    }

}
