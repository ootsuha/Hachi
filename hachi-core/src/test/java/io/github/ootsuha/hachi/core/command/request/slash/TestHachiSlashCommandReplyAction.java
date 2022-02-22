package io.github.ootsuha.hachi.core.command.request.slash;

import io.github.ootsuha.hachi.core.command.request.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.command.*;
import net.dv8tion.jda.api.requests.restaction.interactions.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestHachiSlashCommandReplyAction {
    private SlashCommandInteractionEvent mockEvent(String content, MessageEmbed embed) {
        var event = mock(SlashCommandInteractionEvent.class);
        var action = mock(ReplyCallbackAction.class);
        when(event.reply(content)).thenReturn(action);
        when(event.replyEmbeds(embed)).thenReturn(action);
        // surely there is a better way to do this?
        when(action.addActionRow()).thenReturn(action);
        when(action.addActionRows()).thenReturn(action);
        when(action.addActionRow(anyCollection())).thenReturn(action);
        when(action.addActionRows(anyCollection())).thenReturn(action);
        when(action.setEphemeral(false)).thenReturn(action);
        when(action.setEphemeral(true)).thenReturn(action);
        return event;
    }

    @Test
    public void queueString() {
        String content = "";
        var event = mockEvent(content, null);
        var ra = new HachiSlashCommandReplyAction(event, content);

        ra.queue();

        verify(event.reply(content)).queue();
    }

    @Test
    public void queueEmbed() {
        MessageEmbed embed = mock(MessageEmbed.class);
        var event = mockEvent(null, embed);
        var ra = new HachiSlashCommandReplyAction(event, embed);

        ra.queue();

        verify(event.replyEmbeds(embed)).queue();
    }

    @Test
    public void completeString() {
        String content = "";
        var event = mockEvent(content, null);
        var ra = new HachiSlashCommandReplyAction(event, content);

        ra.complete();

        verify(event.reply(content)).complete();
    }

    @Test
    public void completeEmbed() {
        MessageEmbed embed = mock(MessageEmbed.class);
        var event = mockEvent(null, embed);
        var ra = new HachiSlashCommandReplyAction(event, embed);

        ra.complete();

        verify(event.replyEmbeds(embed)).complete();
    }

    @Test
    public void ephemeral() {
        MessageEmbed embed = mock(MessageEmbed.class);
        var event = mockEvent(null, embed);
        var ra = new HachiSlashCommandReplyAction(event, embed);

        ra.setEphemeral().complete();

        verify(event.replyEmbeds(embed)).complete();
        verify(event.replyEmbeds(embed)).setEphemeral(true);
    }

    @Test
    public void equals() {
        String content = "";
        MessageEmbed embed = mock(MessageEmbed.class);
        SlashCommandInteractionEvent event = mock(SlashCommandInteractionEvent.class);
        HachiCommandReplyAction ra = new HachiSlashCommandReplyAction(event, content);
        HachiCommandReplyAction raExpected = new HachiSlashCommandReplyAction(event, content);
        HachiCommandReplyAction ra2 = new HachiSlashCommandReplyAction(event, embed);
        HachiCommandReplyAction ra2Expected = new HachiSlashCommandReplyAction(event, embed);

        assertEquals(raExpected, ra);
        assertEquals(ra2Expected, ra2);
    }
}
