package io.github.ootsuha.hachi.utility;

import io.github.cdimascio.dotenv.*;

/**
 * Loads constants from <code>.env</code>.
 */
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
    /**
     * Icon URL to use for certain embeds.
     */
    public static final String ICON_URL;

    static {
        Dotenv dotenv = Dotenv.load();
        DISCORD_TOKEN = dotenv.get("DISCORD_TOKEN");
        BOT_PREFIX = dotenv.get("BOT_PREFIX");
        COLOR = Integer.parseInt(dotenv.get("COLOR"));
        ICON_URL = dotenv.get("ICON_URL");
    }

    /**
     * Private constructor so class cannot be instantiated.
     */
    private Constant() {
    }

}
