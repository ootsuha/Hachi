package io.github.ootsuha.hachi.core.command.request.message;

import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.command.request.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.requests.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestHachiMessageCommandRequest {
    @Test
    public void getRequestedCommand() {
        Message m = mock(Message.class);
        HachiCommand cExpected = mock(HachiCommand.class);
        HachiCommandOptions o = mock(HachiCommandOptions.class);
        HachiCommandRequest r = new HachiMessageCommandRequest(m, cExpected, o);

        HachiCommand c = r.getCommand();

        assertEquals(cExpected, c);
    }

    @Test
    public void getOptions() {
        Message m = mock(Message.class);
        HachiCommand c = mock(HachiCommand.class);
        HachiCommandOptions oExpected = mock(HachiCommandOptions.class);
        HachiCommandRequest r = new HachiMessageCommandRequest(m, c, oExpected);

        HachiCommandOptions o = r.getOptions();

        assertEquals(oExpected, o);
    }

    @Test
    public void getUser() {
        Message m = mock(Message.class);
        User userExpected = mock(User.class);
        when(m.getAuthor()).thenReturn(userExpected);
        HachiCommand c = mock(HachiCommand.class);
        HachiCommandOptions o = mock(HachiCommandOptions.class);
        HachiCommandRequest r = new HachiMessageCommandRequest(m, c, o);

        User user = r.getUser();

        verify(m).getAuthor();
        assertEquals(userExpected, user);
    }

    @Test
    public void getChannel() {
        Message m = mock(Message.class);
        TextChannel channelExpected = mock(TextChannel.class);
        when(m.getTextChannel()).thenReturn(channelExpected);
        HachiCommand c = mock(HachiCommand.class);
        HachiCommandOptions o = mock(HachiCommandOptions.class);
        HachiCommandRequest r = new HachiMessageCommandRequest(m, c, o);

        TextChannel channel = r.getChannel();

        verify(m).getTextChannel();
        assertEquals(channelExpected, channel);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void deferReply() {
        Message m = mock(Message.class);
        HachiCommand c = mock(HachiCommand.class);
        HachiCommandOptions o = mock(HachiCommandOptions.class);
        HachiCommandRequest r = new HachiMessageCommandRequest(m, c, o);
        TextChannel ch = mock(TextChannel.class);
        RestAction<Void> ra = (RestAction<Void>) mock(RestAction.class);
        when(m.getTextChannel()).thenReturn(ch);
        when(ch.sendTyping()).thenReturn(ra);

        r.deferReply();

        verify(ch).sendTyping();
        verify(ra).queue();
    }

    @Test
    public void reply() {
        String content = "reply content";
        Message m = mock(Message.class);
        HachiCommand c = mock(HachiCommand.class);
        HachiCommandOptions o = mock(HachiCommandOptions.class);
        HachiCommandRequest r = new HachiMessageCommandRequest(m, c, o);

        HachiCommandReplyAction ra = r.reply(content);
        HachiCommandReplyAction raExpected = new HachiMessageCommandReplyAction(m, content);

        assertEquals(raExpected, ra);
    }

    @Test
    public void replyEmbed() {
        MessageEmbed em = mock(MessageEmbed.class);
        Message m = mock(Message.class);
        HachiCommand c = mock(HachiCommand.class);
        HachiCommandOptions o = mock(HachiCommandOptions.class);
        HachiCommandRequest r = new HachiMessageCommandRequest(m, c, o);

        HachiCommandReplyAction ra = r.replyEmbed(em);
        HachiCommandReplyAction raExpected = new HachiMessageCommandReplyAction(m, em);

        assertEquals(raExpected, ra);
    }

    @Test
    public void equals() {
        Message m = mock(Message.class);
        HachiCommand c = mock(HachiCommand.class);
        HachiCommandOptions o = mock(HachiCommandOptions.class);
        HachiCommandRequest r = new HachiMessageCommandRequest(m, c, o);
        HachiCommandRequest rExpected = new HachiMessageCommandRequest(m, c, o);

        assertEquals(rExpected, r);
    }
}
