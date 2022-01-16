package io.github.ootsuha.hachi.test;

import io.github.ootsuha.hachi.Constant;
import io.github.ootsuha.hachi.command.HachiCommandLoader;
import io.github.ootsuha.hachi.command.HachiCommandRequest;
import io.github.ootsuha.hachi.parser.Parser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Tests for {@link io.github.ootsuha.hachi.parser.Parser}
 */
public class TestParser {
    /**
     * Adds the bot prefix to the start of a string
     *
     * @param s string
     * @return prefixed string
     */
    public static String prefix(final String s) {
        return Constant.BOT_PREFIX + s;
    }

    /**
     * Load test commands into <code>HachiCommandLoader</code>.
     */
    @BeforeAll public static void setup() {
        HachiCommandLoader.load(HachiCommandTestSet.classes);
    }

    @Test public void emptyString() {
        String input = "";
        HachiCommandRequest r = Parser.parse(input);
        assertNull(r);
    }

    @Test public void garbageInput() {
        String input = "alskdjfiefjosefjlxkcv";
        HachiCommandRequest r = Parser.parse(input);
        assertNull(r);
    }

    @Test public void garbageInput2() {
        String input = "       ";
        HachiCommandRequest r = Parser.parse(input);
        assertNull(r);
    }

    @Test public void justPrefix() {
        String input = prefix("");
        HachiCommandRequest r = Parser.parse(input);
        assertNull(r);
    }

    @Test public void invalidCommand() {
        String input = prefix("doesntexist");
        HachiCommandRequest r = Parser.parse(input);
        assertNull(r);
    }

    @Test public void validC1() {
        String input = prefix("c1");
        HachiCommandRequest r = Parser.parse(input);

        String nameExpected = "c1";
        Map<String, Object> optionsExpected = new HashMap<>();
        HachiCommandRequest rExpected = new HachiCommandRequest(nameExpected, optionsExpected);

        assertEquals(rExpected, r);
    }

    @Test public void notEnoughOptions() {
        String input = prefix("c2");
        HachiCommandRequest r = Parser.parse(input);
        assertNull(r);
    }

    @Test public void validC2() {
        String input = prefix("c2 hi");
        HachiCommandRequest r = Parser.parse(input);

        String nameExpected = "c2";
        Map<String, Object> optionsExpected = new HashMap<>();
        optionsExpected.put("o1", "hi");
        HachiCommandRequest rExpected = new HachiCommandRequest(nameExpected, optionsExpected);

        assertEquals(rExpected, r);
    }

    @Test public void tooManyOptions() {
        String input = prefix("c2 hi hi2");
        HachiCommandRequest r = Parser.parse(input);
        assertNull(r);
    }

    @Test public void validC3() {
        String input = prefix("c3 first second third");
        HachiCommandRequest r = Parser.parse(input);

        String nameExpected = "c3";
        Map<String, Object> optionsExpected = new HashMap<>();
        optionsExpected.put("o1", "first");
        optionsExpected.put("o2", "second");
        optionsExpected.put("o3", "third");
        HachiCommandRequest rExpected = new HachiCommandRequest(nameExpected, optionsExpected);

        assertEquals(rExpected, r);
    }

    @Test public void validC4() {
        String input = prefix("c4 str true -5 8.4");
        HachiCommandRequest r = Parser.parse(input);

        String nameExpected = "c4";
        Map<String, Object> optionsExpected = new HashMap<>();
        optionsExpected.put("o1", "str");
        optionsExpected.put("o2", true);
        optionsExpected.put("o3", -5);
        optionsExpected.put("o4", 8.4);
        HachiCommandRequest rExpected = new HachiCommandRequest(nameExpected, optionsExpected);

        assertEquals(rExpected, r);
    }
}

