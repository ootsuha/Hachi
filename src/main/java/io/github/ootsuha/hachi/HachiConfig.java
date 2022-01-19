package io.github.ootsuha.hachi;

import org.springframework.boot.context.properties.*;
import org.springframework.context.annotation.*;

/**
 * Configuration for Hachi.
 */
@Configuration
@ConfigurationProperties(prefix = "hachi")
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

    /**
     * Returns <code>this.token</code>.
     *
     * @return this.token
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets <code>this.token</code>.
     *
     * @param token new value
     */
    public void setToken(final String token) {
        this.token = token;
    }

    /**
     * Returns <code>this.prefix</code>.
     *
     * @return this.prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Sets <code>this.prefix</code>.
     *
     * @param prefix new value
     */
    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }

    /**
     * Returns <code>this.color</code>.
     *
     * @return this.color
     */
    public int getEmbedColor() {
        return embedColor;
    }

    /**
     * Sets <code>this.color</code>.
     *
     * @param embedColor new value
     */
    public void setEmbedColor(final int embedColor) {
        this.embedColor = embedColor;
    }

    /**
     * Returns <code>this.icon</code>.
     *
     * @return this.icon
     */
    public String getIconUrl() {
        return iconUrl;
    }

    /**
     * Sets <code>this.icon</code>.
     *
     * @param iconUrl new value
     */
    public void setIconUrl(final String iconUrl) {
        this.iconUrl = iconUrl;
    }

    /**
     * Returns <code>this.inviteLink</code>.
     *
     * @return this.inviteLink
     */
    public String getInviteLink() {
        return inviteLink;
    }

    /**
     * Sets <code>this.inviteLink</code>.
     *
     * @param inviteLink new value
     */
    public void setInviteLink(final String inviteLink) {
        this.inviteLink = inviteLink;
    }

    /**
     * Returns <code>this.githubRepo</code>.
     *
     * @return this.githubRepo
     */
    public String getGithubRepo() {
        return githubRepo;
    }

    /**
     * Sets <code>this.githubRepo</code>.
     *
     * @param githubRepo new value
     */
    public void setGithubRepo(final String githubRepo) {
        this.githubRepo = githubRepo;
    }
}
