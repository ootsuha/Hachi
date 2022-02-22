package io.github.ootsuha.hachi.core;

import io.github.ootsuha.hachi.core.types.*;
import lombok.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.interactions.components.*;
import net.dv8tion.jda.api.interactions.components.buttons.*;

import java.util.*;
import java.util.stream.*;

public class ButtonRow {
    private static final String DELIMITER = ":";
    @Getter
    private final String handler;
    private final int count;
    private final ButtonStyle[] styles;
    private final boolean[] disabled;
    private String[] values;
    private Object[] labels;

    public ButtonRow(final String handler, final int count) {
        this.handler = handler;
        this.count = count;
        this.styles = new ButtonStyle[count];
        Arrays.fill(this.styles, ButtonStyle.SECONDARY);
        this.disabled = new boolean[count];
    }

    public ButtonRow(final int count) {
        this(UUID.randomUUID().toString(), count);
    }

    public static Pair<String, String> parseHandlerAndValue(final String id) {
        int delimiterIndex = id.indexOf(DELIMITER);
        return Pair.of(id.substring(0, delimiterIndex), id.substring(delimiterIndex + 1));
    }

    public void setValues(final String... values) {
        this.values = values;
    }

    public void setLabels(final Object... labels) {
        this.labels = labels;
    }

    public void setStyle(final ButtonStyle style, final int i) {
        this.styles[i] = style;
    }

    public void disable(final int i) {
        this.disabled[i] = true;
    }

    public void enable(final int i) {
        this.disabled[i] = false;
    }

    private String getId(final int i) {
        return String.format("%s%s%s", this.handler, DELIMITER, this.values[i]);
    }

    private Button getButton(final int i) {
        if (this.labels[i] instanceof Emoji e) {
            return Button.of(this.styles[i], getId(i), e).withDisabled(this.disabled[i]);
        }
        return Button.of(this.styles[i], getId(i), this.labels[i].toString()).withDisabled(this.disabled[i]);
    }

    public ActionRow toRow() {
        return ActionRow.of(IntStream.range(0, this.count).mapToObj(this::getButton).toList());
    }
}
