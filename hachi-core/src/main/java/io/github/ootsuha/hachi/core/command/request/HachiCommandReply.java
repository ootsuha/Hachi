package io.github.ootsuha.hachi.core.command.request;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.requests.*;

/**
 * Interface for a reply to a command.
 */
public interface HachiCommandReply {
    RestAction<Message> removeComponents();

    void editContent(String content);

    void editEmbed(MessageEmbed embed);

    String getId();
}
