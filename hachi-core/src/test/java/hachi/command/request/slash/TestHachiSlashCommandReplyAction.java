package hachi.command.request.slash;

import io.github.ootsuha.hachi.core.command.request.*;
import io.github.ootsuha.hachi.core.command.request.slash.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.*;
import net.dv8tion.jda.api.requests.restaction.interactions.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestHachiSlashCommandReplyAction {
    @Test public void queueString() {
        String content = "";
        SlashCommandEvent event = mock(SlashCommandEvent.class);
        ReplyAction replyAction = mock(ReplyAction.class);
        when(event.reply(content)).thenReturn(replyAction);
        when(replyAction.setEphemeral(false)).thenReturn(replyAction);
        HachiCommandReplyAction ra = new HachiSlashCommandReplyAction(event, content);

        ra.queue();

        verify(replyAction).queue();
    }

    @Test public void queueEmbed() {
        MessageEmbed embed = mock(MessageEmbed.class);
        SlashCommandEvent event = mock(SlashCommandEvent.class);
        ReplyAction replyAction = mock(ReplyAction.class);
        when(event.replyEmbeds(embed)).thenReturn(replyAction);
        when(replyAction.setEphemeral(false)).thenReturn(replyAction);
        HachiCommandReplyAction ra = new HachiSlashCommandReplyAction(event, embed);

        ra.queue();

        verify(replyAction).queue();
    }

    @Test public void completeString() {
        String content = "";
        SlashCommandEvent event = mock(SlashCommandEvent.class);
        ReplyAction replyAction = mock(ReplyAction.class);
        when(event.reply(content)).thenReturn(replyAction);
        when(replyAction.setEphemeral(false)).thenReturn(replyAction);
        HachiCommandReplyAction ra = new HachiSlashCommandReplyAction(event, content);

        ra.complete();

        verify(replyAction).complete();
    }

    @Test public void completeEmbed() {
        MessageEmbed embed = mock(MessageEmbed.class);
        SlashCommandEvent event = mock(SlashCommandEvent.class);
        ReplyAction replyAction = mock(ReplyAction.class);
        when(event.replyEmbeds(embed)).thenReturn(replyAction);
        when(replyAction.setEphemeral(false)).thenReturn(replyAction);
        HachiCommandReplyAction ra = new HachiSlashCommandReplyAction(event, embed);

        ra.complete();

        verify(replyAction).complete();
    }

    @Test public void ephemeral() {
        MessageEmbed embed = mock(MessageEmbed.class);
        SlashCommandEvent event = mock(SlashCommandEvent.class);
        ReplyAction replyAction = mock(ReplyAction.class);
        when(event.replyEmbeds(embed)).thenReturn(replyAction);
        when(replyAction.setEphemeral(true)).thenReturn(replyAction);
        HachiCommandReplyAction ra = new HachiSlashCommandReplyAction(event, embed);

        ra.setEphemeral().complete();

        verify(replyAction).setEphemeral(true);
        verify(replyAction).complete();
    }

    @Test public void equals() {
        String content = "";
        MessageEmbed embed = mock(MessageEmbed.class);
        SlashCommandEvent event = mock(SlashCommandEvent.class);
        HachiCommandReplyAction ra = new HachiSlashCommandReplyAction(event, content);
        HachiCommandReplyAction raExpected = new HachiSlashCommandReplyAction(event, content);
        HachiCommandReplyAction ra2 = new HachiSlashCommandReplyAction(event, embed);
        HachiCommandReplyAction ra2Expected = new HachiSlashCommandReplyAction(event, embed);

        assertEquals(raExpected, ra);
        assertEquals(ra2Expected, ra2);
    }
}
