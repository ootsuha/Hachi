// ill fix this laterrr
//package io.github.ootsuha.hachi.test;
//
//import io.github.ootsuha.hachi.command.*;
//import io.github.ootsuha.hachi.parser.*;
//import io.github.ootsuha.hachi.utility.*;
//import org.junit.jupiter.api.*;
//
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// * Tests for {@link io.github.ootsuha.hachi.parser.Parser}
// */
//public class TestParser {
//    /**
//     * Adds the bot prefix to the start of a string
//     *
//     * @param s string
//     * @return prefixed string
//     */
//    public static String prefix(final String s) {
//        return Constant.BOT_PREFIX + s;
//    }
//
//    /**
//     * Load test commands into <code>HachiCommandLoader</code>.
//     */
//    @BeforeAll public static void setup() {
//        HachiCommandLoader.load(HachiCommandTestSet.classes);
//    }
//
//    @Test public void emptyString() {
//        String input = "";
//        HachiCommandRequest r = Parser.parse(input);
//
//        assertNull(r);
//    }
//
//    @Test public void garbageInput() {
//        String input = "alskdjfiefjosefjlxkcv";
//        HachiCommandRequest r = Parser.parse(input);
//
//        assertNull(r);
//    }
//
//    @Test public void garbageInput2() {
//        String input = "       ";
//        HachiCommandRequest r = Parser.parse(input);
//
//        assertNull(r);
//    }
//
//    @Test public void justPrefix() {
//        String input = prefix("");
//        HachiCommandRequest r = Parser.parse(input);
//
//        assertNull(r);
//    }
//
//    @Test public void invalidCommand() {
//        String input = prefix("doesntexist");
//        HachiCommandRequest r = Parser.parse(input);
//
//        assertNull(r);
//    }
//
//    @Test public void validNoOption() {
//        String input = prefix("nooption");
//        HachiCommandRequest r = Parser.parse(input);
//
//        String nameExpected = "nooption";
//        Map<String, Object> optionsExpected = new HashMap<>();
//        HachiCommandRequest rExpected = new HachiCommandRequest(nameExpected, optionsExpected);
//
//        assertEquals(rExpected, r);
//    }
//
//    @Test public void notEnoughOptions() {
//        String input = prefix("stringoption");
//        HachiCommandRequest r = Parser.parse(input);
//
//        assertNull(r);
//    }
//
//    @Test public void validStringOption() {
//        String input = prefix("stringoption hi");
//        HachiCommandRequest r = Parser.parse(input);
//
//        String nameExpected = "stringoption";
//        Map<String, Object> optionsExpected = new HashMap<>();
//        optionsExpected.put("string", "hi");
//        HachiCommandRequest rExpected = new HachiCommandRequest(nameExpected, optionsExpected);
//
//        assertEquals(rExpected, r);
//    }
//
//    @Test public void tooManyOptions() {
//        String input = prefix("stringoption hi hi2");
//        HachiCommandRequest r = Parser.parse(input);
//
//        assertNull(r);
//    }
//
//    @Test public void validMultiOption() {
//        String input = prefix("stringoption3 first second third");
//        HachiCommandRequest r = Parser.parse(input);
//
//        String nameExpected = "stringoption3";
//        Map<String, Object> optionsExpected = new HashMap<>();
//        optionsExpected.put("string1", "first");
//        optionsExpected.put("string2", "second");
//        optionsExpected.put("string3", "third");
//        HachiCommandRequest rExpected = new HachiCommandRequest(nameExpected, optionsExpected);
//
//        assertEquals(rExpected, r);
//    }
//
//    @Test public void validAllOption() {
//        String input = prefix("alloption str true -5 8.4");
//        HachiCommandRequest r = Parser.parse(input);
//
//        String nameExpected = "alloption";
//        Map<String, Object> optionsExpected = new HashMap<>();
//        optionsExpected.put("string", "str");
//        optionsExpected.put("boolean", true);
//        optionsExpected.put("integer", -5);
//        optionsExpected.put("double", 8.4);
//        HachiCommandRequest rExpected = new HachiCommandRequest(nameExpected, optionsExpected);
//
//        assertEquals(rExpected, r);
//    }
//
//    @Test public void invalidTypeString() {
//        String input = prefix("alloption str str str str");
//        HachiCommandRequest r = Parser.parse(input);
//
//        assertNull(r);
//    }
//
//}
//
