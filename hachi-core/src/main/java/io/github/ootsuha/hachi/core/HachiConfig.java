package io.github.ootsuha.hachi.core;

import lombok.*;

/**
 * Configuration for Hachi.
 */
@Getter
@Setter
public class HachiConfig {
    /**
     * Discord bot token.
     */
    private String token;
    /**
     * Prefix for message commands.
     */
    private String prefix;
    /**
     * Color to use for certain embeds.
     */
    private int embedColor;
    /**
     * Icon URL to use for certain embeds.
     */
    private String iconUrl;
    /**
     * Hachi invite link.
     */
    private String inviteLink;
    /**
     * Link to Hachi's GitHub repository.
     */
    private String githubRepo;
}
