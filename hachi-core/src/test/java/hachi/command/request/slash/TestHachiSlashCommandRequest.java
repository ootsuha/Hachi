package hachi.command.request.slash;

import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.command.request.*;
import io.github.ootsuha.hachi.core.command.request.slash.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.*;
import net.dv8tion.jda.api.requests.restaction.interactions.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestHachiSlashCommandRequest {
    @Test public void getRequestedCommand() {
        SlashCommandEvent e = mock(SlashCommandEvent.class);
        HachiCommand cExpected = mock(HachiCommand.class);
        HachiCommandRequest r = new HachiSlashCommandRequest(e, cExpected);

        HachiCommand c = r.getRequestedCommand();

        assertEquals(cExpected, c);
    }

    @Test public void getOptions() {
        SlashCommandEvent e = mock(SlashCommandEvent.class);
        HachiCommand c = mock(HachiCommand.class);
        HachiCommandRequest r = new HachiSlashCommandRequest(e, c);

        HachiCommandOptions o = r.getOptions();
        HachiCommandOptions oExpected = new HachiSlashCommandOptions(e);

        assertEquals(oExpected, o);
    }

    @Test public void getUser() {
        SlashCommandEvent e = mock(SlashCommandEvent.class);
        HachiCommand c = mock(HachiCommand.class);
        HachiCommandRequest r = new HachiSlashCommandRequest(e, c);
        User userExpected = mock(User.class);
        when(e.getUser()).thenReturn(userExpected);

        User user = r.getUser();

        verify(e).getUser();
        assertEquals(userExpected, user);
    }

    @Test public void getChannel() {
        SlashCommandEvent e = mock(SlashCommandEvent.class);
        HachiCommand c = mock(HachiCommand.class);
        HachiCommandRequest r = new HachiSlashCommandRequest(e, c);
        TextChannel channelExpected = mock(TextChannel.class);
        when(e.getTextChannel()).thenReturn(channelExpected);

        TextChannel channel = r.getChannel();

        verify(e).getTextChannel();
        assertEquals(channelExpected, channel);
    }

    @Test public void deferReply() {
        SlashCommandEvent e = mock(SlashCommandEvent.class);
        HachiCommand c = mock(HachiCommand.class);
        HachiCommandRequest r = new HachiSlashCommandRequest(e, c);
        ReplyAction ra = mock(ReplyAction.class);
        when(e.deferReply()).thenReturn(ra);

        r.deferReply();

        verify(e).deferReply();
        verify(ra).queue();
    }

    @Test public void reply() {
        String content = "reply content";
        SlashCommandEvent e = mock(SlashCommandEvent.class);
        HachiCommand c = mock(HachiCommand.class);
        HachiCommandRequest r = new HachiSlashCommandRequest(e, c);

        HachiCommandReplyAction ra = r.reply(content);
        HachiCommandReplyAction raExpected = new HachiSlashCommandReplyAction(e, content);

        assertEquals(raExpected, ra);
    }

    @Test public void replyEmbed() {
        MessageEmbed em = mock(MessageEmbed.class);
        SlashCommandEvent e = mock(SlashCommandEvent.class);
        HachiCommand c = mock(HachiCommand.class);
        HachiCommandRequest r = new HachiSlashCommandRequest(e, c);

        HachiCommandReplyAction ra = r.replyEmbed(em);
        HachiCommandReplyAction raExpected = new HachiSlashCommandReplyAction(e, em);

        assertEquals(raExpected, ra);
    }

    @Test public void equals() {
        SlashCommandEvent e = mock(SlashCommandEvent.class);
        HachiCommand c = mock(HachiCommand.class);
        HachiCommandRequest r = new HachiSlashCommandRequest(e, c);
        HachiCommandRequest rExpected = new HachiSlashCommandRequest(e, c);

        assertEquals(rExpected, r);
    }
}
