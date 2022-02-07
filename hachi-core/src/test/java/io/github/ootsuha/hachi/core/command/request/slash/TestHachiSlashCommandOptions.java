package io.github.ootsuha.hachi.core.command.request.slash;

import net.dv8tion.jda.api.events.interaction.*;
import net.dv8tion.jda.api.interactions.commands.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestHachiSlashCommandOptions {
    @Test public void hasOption() {
        SlashCommandEvent e = mock(SlashCommandEvent.class);
        OptionMapping m = mock(OptionMapping.class);
        when(e.getOption("opt")).thenReturn(m);
        HachiSlashCommandOptions o = new HachiSlashCommandOptions(e);

        boolean has = o.hasOption("opt");
        boolean doesNotHave = o.hasOption("no");

        assertTrue(has);
        assertFalse(doesNotHave);
    }

    @Test public void getString() {
        SlashCommandEvent e = mock(SlashCommandEvent.class);
        OptionMapping m = mock(OptionMapping.class);
        String sExpected = "value";
        when(m.getAsString()).thenReturn(sExpected);
        when(e.getOption("opt")).thenReturn(m);
        HachiSlashCommandOptions o = new HachiSlashCommandOptions(e);

        String s = o.getString("opt");

        assertEquals(sExpected, s);
    }

    @Test public void getInteger() {
        SlashCommandEvent e = mock(SlashCommandEvent.class);
        OptionMapping m = mock(OptionMapping.class);
        int lExpected = 8620;
        when(m.getAsLong()).thenReturn((long) lExpected);
        when(e.getOption("opt")).thenReturn(m);
        HachiSlashCommandOptions o = new HachiSlashCommandOptions(e);

        Integer l = o.getInteger("opt");

        assertEquals(lExpected, l);
    }

    @Test public void getDouble() {
        SlashCommandEvent e = mock(SlashCommandEvent.class);
        OptionMapping m = mock(OptionMapping.class);
        double dExpected = 8.6;
        when(m.getAsDouble()).thenReturn(dExpected);
        when(e.getOption("opt")).thenReturn(m);
        HachiSlashCommandOptions o = new HachiSlashCommandOptions(e);

        Double d = o.getDouble("opt");

        assertEquals(dExpected, d);
    }

    @Test public void getBoolean() {
        SlashCommandEvent e = mock(SlashCommandEvent.class);
        OptionMapping m = mock(OptionMapping.class);
        when(m.getAsBoolean()).thenReturn(false);
        when(e.getOption("opt")).thenReturn(m);
        HachiSlashCommandOptions o = new HachiSlashCommandOptions(e);

        Boolean b = o.getBoolean("opt");

        assertEquals(false, b);
    }

    @Test public void equals() {
        SlashCommandEvent e = mock(SlashCommandEvent.class);
        OptionMapping m = mock(OptionMapping.class);
        when(m.getAsBoolean()).thenReturn(false);
        when(e.getOption("opt")).thenReturn(m);
        HachiSlashCommandOptions o = new HachiSlashCommandOptions(e);
        HachiSlashCommandOptions oExpected = new HachiSlashCommandOptions(e);

        assertEquals(oExpected, o);
    }
}
