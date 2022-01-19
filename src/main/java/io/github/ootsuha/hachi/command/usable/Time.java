package io.github.ootsuha.hachi.command.usable;

import io.github.ootsuha.hachi.command.*;
import org.springframework.stereotype.*;

import java.text.*;
import java.util.*;

@Component
public final class Time extends HachiStringCommand {
    /**
     * Formatter.
     */
    private final DateFormat formatter;

    public Time() {
        super("time", "Gives the time in UTC.");
        setAliases("utc");
        this.formatter = DateFormat.getTimeInstance(DateFormat.DEFAULT, Locale.ROOT);
        this.formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    @Override protected String output(final HachiCommandRequest r) {
        return this.formatter.format(new Date());
    }
}
