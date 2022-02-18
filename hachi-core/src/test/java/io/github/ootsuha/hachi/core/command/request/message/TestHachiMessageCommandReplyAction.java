package io.github.ootsuha.hachi.core.command.request.message;

import io.github.ootsuha.hachi.core.command.request.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.requests.restaction.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestHachiMessageCommandReplyAction {
    @Test
    public void queueString() {
        String content = "";
        Message message = mock(Message.class);
        MessageAction messageAction = mock(MessageAction.class);
        when(message.reply(content)).thenReturn(messageAction);

        HachiCommandReplyAction ra = new HachiMessageCommandReplyAction(message, content);
        ra.queue();
        verify(messageAction).queue();
    }

    @Test
    public void queueEmbed() {
        MessageEmbed embed = mock(MessageEmbed.class);
        Message message = mock(Message.class);
        MessageAction messageAction = mock(MessageAction.class);
        when(message.replyEmbeds(embed)).thenReturn(messageAction);

        HachiCommandReplyAction ra = new HachiMessageCommandReplyAction(message, embed);
        ra.queue();
        verify(messageAction).queue();
    }

    @Test
    public void completeString() {
        String content = "";
        Message message = mock(Message.class);
        MessageAction messageAction = mock(MessageAction.class);
        when(message.reply(content)).thenReturn(messageAction);

        HachiCommandReplyAction ra = new HachiMessageCommandReplyAction(message, content);
        ra.complete();
        verify(messageAction).complete();
    }

    @Test
    public void completeEmbed() {
        MessageEmbed embed = mock(MessageEmbed.class);
        Message message = mock(Message.class);
        MessageAction messageAction = mock(MessageAction.class);
        when(message.replyEmbeds(embed)).thenReturn(messageAction);

        HachiCommandReplyAction ra = new HachiMessageCommandReplyAction(message, embed);
        ra.complete();
        verify(messageAction).complete();
    }

    @Test
    public void equals() {
        String content = "";
        MessageEmbed embed = mock(MessageEmbed.class);
        Message message = mock(Message.class);
        HachiCommandReplyAction ra = new HachiMessageCommandReplyAction(message, content);
        HachiCommandReplyAction raExpected = new HachiMessageCommandReplyAction(message, content);
        HachiCommandReplyAction ra2 = new HachiMessageCommandReplyAction(message, embed);
        HachiCommandReplyAction ra2Expected = new HachiMessageCommandReplyAction(message, embed);

        assertEquals(raExpected, ra);
        assertEquals(ra2Expected, ra2);
    }
}
