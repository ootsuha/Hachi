package io.github.ootsuha.hachi.core.command.request.slash;

import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.command.request.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.command.*;
import net.dv8tion.jda.api.requests.restaction.interactions.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestHachiSlashCommandRequest {
    @Test
    public void getRequestedCommand() {
        SlashCommandInteractionEvent e = mock(SlashCommandInteractionEvent.class);
        HachiCommand cExpected = mock(HachiCommand.class);
        HachiCommandRequest r = new HachiSlashCommandRequest(e, cExpected);

        HachiCommand c = r.getCommand();

        assertEquals(cExpected, c);
    }

    @Test
    public void getOptions() {
        SlashCommandInteractionEvent e = mock(SlashCommandInteractionEvent.class);
        HachiCommand c = mock(HachiCommand.class);
        HachiCommandRequest r = new HachiSlashCommandRequest(e, c);

        HachiCommandOptions o = r.getOptions();
        HachiCommandOptions oExpected = new HachiSlashCommandOptions(e);

        assertEquals(oExpected, o);
    }

    @Test
    public void getUser() {
        SlashCommandInteractionEvent e = mock(SlashCommandInteractionEvent.class);
        HachiCommand c = mock(HachiCommand.class);
        User userExpected = mock(User.class);
        when(e.getUser()).thenReturn(userExpected);
        HachiCommandRequest r = new HachiSlashCommandRequest(e, c);

        User user = r.getUser();

        verify(e).getUser();
        assertEquals(userExpected, user);
    }

    @Test
    public void getChannel() {
        SlashCommandInteractionEvent e = mock(SlashCommandInteractionEvent.class);
        HachiCommand c = mock(HachiCommand.class);
        TextChannel channelExpected = mock(TextChannel.class);
        when(e.getTextChannel()).thenReturn(channelExpected);
        HachiCommandRequest r = new HachiSlashCommandRequest(e, c);

        TextChannel channel = r.getChannel();

        verify(e).getTextChannel();
        assertEquals(channelExpected, channel);
    }

    @Test
    public void deferReply() {
        SlashCommandInteractionEvent e = mock(SlashCommandInteractionEvent.class);
        HachiCommand c = mock(HachiCommand.class);
        HachiCommandRequest r = new HachiSlashCommandRequest(e, c);
        ReplyCallbackAction ra = mock(ReplyCallbackAction.class);
        when(e.deferReply()).thenReturn(ra);

        r.deferReply();

        verify(e).deferReply();
        verify(ra).queue();
    }

    @Test
    public void reply() {
        String content = "reply content";
        SlashCommandInteractionEvent e = mock(SlashCommandInteractionEvent.class);
        HachiCommand c = mock(HachiCommand.class);
        HachiCommandRequest r = new HachiSlashCommandRequest(e, c);

        HachiCommandReplyAction ra = r.reply(content);
        HachiCommandReplyAction raExpected = new HachiSlashCommandReplyAction(e, content);

        assertEquals(raExpected, ra);
    }

    @Test
    public void replyEmbed() {
        MessageEmbed em = mock(MessageEmbed.class);
        SlashCommandInteractionEvent e = mock(SlashCommandInteractionEvent.class);
        HachiCommand c = mock(HachiCommand.class);
        HachiCommandRequest r = new HachiSlashCommandRequest(e, c);

        HachiCommandReplyAction ra = r.replyEmbed(em);
        HachiCommandReplyAction raExpected = new HachiSlashCommandReplyAction(e, em);

        assertEquals(raExpected, ra);
    }

    @Test
    public void equals() {
        SlashCommandInteractionEvent e = mock(SlashCommandInteractionEvent.class);
        HachiCommand c = mock(HachiCommand.class);
        HachiCommandRequest r = new HachiSlashCommandRequest(e, c);
        HachiCommandRequest rExpected = new HachiSlashCommandRequest(e, c);

        assertEquals(rExpected, r);
    }
}
