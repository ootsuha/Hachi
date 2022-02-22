package io.github.ootsuha.hachi.core.command.request.message;

import io.github.ootsuha.hachi.core.command.request.*;
import lombok.*;
import lombok.experimental.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.interactions.components.*;
import net.dv8tion.jda.api.requests.restaction.*;

import java.util.*;
import java.util.function.*;

/**
 * Represents reply actions for commands requested from a message.
 */
@EqualsAndHashCode
@Accessors(chain = true)
public final class HachiMessageCommandReplyAction implements HachiCommandReplyAction {
    private final Message message;
    private final boolean isEmbed;
    @Setter
    private List<ActionRow> actionRows;
    private String content;
    private MessageEmbed embed;
    private boolean ephemeral;

    /**
     * Create a reply action with a string.
     *
     * @param message message to reply to
     * @param content reply text
     */
    public HachiMessageCommandReplyAction(final Message message, final String content) {
        this.message = message;
        this.isEmbed = false;
        this.content = content;
        this.ephemeral = false;
        this.actionRows = new ArrayList<>();
    }

    /**
     * Create a reply action with an embed.
     *
     * @param message message to reply to
     * @param embed   message embed
     */
    public HachiMessageCommandReplyAction(final Message message, final MessageEmbed embed) {
        this.message = message;
        this.isEmbed = true;
        this.embed = embed;
        this.ephemeral = false;
        this.actionRows = new ArrayList<>();
    }

    private MessageAction reply() {
        if (this.isEmbed) {
            return this.message.replyEmbeds(this.embed).setActionRows(this.actionRows);
        }
        return this.message.reply(this.content).setActionRows(this.actionRows);
    }

    @Override
    public void queue() {
        reply().queue();
    }

    @Override
    public void queue(final Consumer<HachiCommandReply> callback) {
        reply().queue(e -> callback.accept(new HachiMessageCommandReply(e)));
    }

    @Override
    public HachiCommandReply complete() {
        return new HachiMessageCommandReply(reply().complete());
    }

    @Override
    public HachiCommandReplyAction setEphemeral() {
        this.ephemeral = true;
        return this;
    }
}
