package hachi.parser;

import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.command.request.*;
import io.github.ootsuha.hachi.core.command.request.message.*;
import io.github.ootsuha.hachi.core.parser.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.interactions.commands.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.util.*;
import java.util.stream.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestParser {
    private static final String prefix = "8";

    public static String prefix(final String s) {
        return prefix + s;
    }

    private static class Template extends HachiCommandImpl {
        public Template(final String name) {
            super(name, "description");
        }

        public void setup() {
        }

        @Override public void run(final HachiCommandRequest r) {
        }
    }

    // no option / single required option commands
    @Nested
    class Basic {
        private static Parser parser;
        private static HachiCommandLoader loader;

        @BeforeAll public static void loadCommands() {
            loader = new HachiCommandLoader();
            List<Template> commands = List.of(new Template("no_option") {
            }, new Template("string") {
                @Override public void setup() {
                    addOption(OptionType.STRING, "string", "description", true);
                }
            }, new Template("int") {
                @Override public void setup() {
                    addOption(OptionType.INTEGER, "int", "description", true);
                }
            }, new Template("double") {
                @Override public void setup() {
                    addOption(OptionType.NUMBER, "double", "description", true);
                }
            }, new Template("boolean") {
                @Override public void setup() {
                    addOption(OptionType.BOOLEAN, "boolean", "description", true);
                }
            });
            for (Template command : commands) {
                command.setup();
                loader.loadCommand(command);
            }
            parser = new Parser(loader, prefix);
        }

        public static List<String> causesNull() {
            return List.of("", "    ", "hachiroku", prefix(""), prefix("fake_command"), prefix("no_option opt"));
        }

        public static List<String> commandNames() {
            return List.of("string", "int", "double", "boolean");
        }

        public static List<Object> validOptions() {
            return List.of("hey", 5, -20.0, false);
        }

        public static List<String> noOption() {
            return List.of(prefix("no_option"), "     " + prefix("no_option"), prefix("no_option     "),
                    "    " + prefix("no_option     "));
        }

        public static List<String> stringOptions() {
            return List.of("string", "10", "-20.4", "!@#$%^&*()", "STRING", "string_string-string",
                    "-=\\[];',./_+|{}:\"<>?", prefix("string"));
        }

        public static List<Integer> intOptions() {
            return List.of(0, 1, 2, 3, -1, -2, -3, 1000, 2000);
        }

        public static List<Double> doubleOptions() {
            return List.of(0.0, 10.0, -10.0, 0.1, 0.001, 0.8620, 9999.9999, -0.00001);
        }

        public static List<Boolean> booleanOptions() {
            return List.of(true, false);
        }

        public static List<String> wrongTypes() {
            return List.of("int a", "int -", "int 0.2", "double a", "double -", "boolean 0", "boolean a", "boolean -");
        }

        private static HachiCommandRequest createRequest(final Message message, final String commandName,
                final Map<String, Object> map) {
            HachiCommand command = loader.getCommand(commandName);
            assert command != null;
            HachiCommandOptions options = new HachiCommandOptionsImpl(map, command.getCommandData());
            return new HachiMessageCommandRequest(message, command, options);
        }

        @ParameterizedTest @MethodSource(value = "causesNull") public void invalid(final String content) {
            Message m = mock(Message.class);
            when(m.getContentRaw()).thenReturn(content);

            HachiCommandRequest r = parser.parse(m);

            assertNull(r, "Invalid message does not cause parse failure.");
        }

        @ParameterizedTest @MethodSource(value = "commandNames")
        public void singleOptionNotEnoughOptions(final String comm) {
            String content = prefix(comm);
            Message m = mock(Message.class);
            when(m.getContentRaw()).thenReturn(content);

            HachiCommandRequest r = parser.parse(m);

            assertNull(r, "Missing required argument does not cause parse failure.");
        }

        @ParameterizedTest @MethodSource(value = "wrongTypes") public void singleOptionWrongType(final String s) {
            String content = prefix(s);
            Message m = mock(Message.class);
            when(m.getContentRaw()).thenReturn(content);

            HachiCommandRequest r = parser.parse(m);

            assertNull(r, "Wrong option type does not cause parse failure.");
        }

        @ParameterizedTest @ValueSource(ints = { 0, 1, 2, 3 }) public void singleOptionExtraOption(final int i) {
            String comm = commandNames().get(i);
            Object opt = validOptions().get(i);
            String content = prefix(comm + ' ' + opt + ' ' + opt);
            Message m = mock(Message.class);
            when(m.getContentRaw()).thenReturn(content);

            HachiCommandRequest r = parser.parse(m);

            assertNull(r, "Extra option does not cause parse failure.");
        }

        @ParameterizedTest @MethodSource(value = "noOption") public void validNoOption(final String content) {
            Message m = mock(Message.class);
            when(m.getContentRaw()).thenReturn(content);
            Map<String, Object> options = new HashMap<>();

            HachiCommandRequest r = parser.parse(m);
            HachiCommandRequest rExpected = createRequest(m, "no_option", options);

            assertEquals(rExpected, r, "Error parsing no option command.");
        }

        @ParameterizedTest @ValueSource(ints = { 0, 1, 2, 3 }) public void validSingleOption(final int i) {
            String comm = commandNames().get(i);
            Object opt = validOptions().get(i);
            String content = prefix(comm + ' ' + opt);
            Message m = mock(Message.class);
            when(m.getContentRaw()).thenReturn(content);
            Map<String, Object> options = new HashMap<>();
            options.put(comm, opt);

            HachiCommandRequest r = parser.parse(m);
            HachiCommandRequest rExpected = createRequest(m, comm, options);

            assertEquals(rExpected, r, "Error parsing single option.");
        }

        @ParameterizedTest @MethodSource(value = "stringOptions") public void validStringOption(final String s) {
            String content = prefix("string " + s);
            Message m = mock(Message.class);
            when(m.getContentRaw()).thenReturn(content);
            Map<String, Object> options = new HashMap<>();
            options.put("string", s);

            HachiCommandRequest r = parser.parse(m);
            HachiCommandRequest rExpected = createRequest(m, "string", options);

            assertEquals(rExpected, r, "Error parsing single string option.");
        }

        @ParameterizedTest @MethodSource(value = "intOptions") public void validIntOption(final int n) {
            String content = prefix("int " + n);
            Message m = mock(Message.class);
            when(m.getContentRaw()).thenReturn(content);
            Map<String, Object> options = new HashMap<>();
            options.put("int", n);

            HachiCommandRequest r = parser.parse(m);
            HachiCommandRequest rExpected = createRequest(m, "int", options);

            assertEquals(rExpected, r, "Error parsing single int option.");
        }

        @ParameterizedTest @MethodSource(value = "doubleOptions") public void validDoubleOption(final double n) {
            String content = prefix("double " + n);
            Message m = mock(Message.class);
            when(m.getContentRaw()).thenReturn(content);
            Map<String, Object> options = new HashMap<>();
            options.put("double", n);

            HachiCommandRequest r = parser.parse(m);
            HachiCommandRequest rExpected = createRequest(m, "double", options);

            assertEquals(rExpected, r, "Error parsing single double option.");
        }

        @ParameterizedTest @MethodSource(value = "booleanOptions") public void validBooleanOption(final boolean b) {
            String content = prefix("boolean " + b);
            Message m = mock(Message.class);
            when(m.getContentRaw()).thenReturn(content);
            Map<String, Object> options = new HashMap<>();
            options.put("boolean", b);

            HachiCommandRequest r = parser.parse(m);
            HachiCommandRequest rExpected = createRequest(m, "boolean", options);

            assertEquals(rExpected, r, "Error parsing single boolean option.");
        }
    }

    // optional options / multi option commands
    @Nested
    class Complex {
        private static Parser parser;
        private static HachiCommandLoader loader;

        @BeforeAll public static void loadCommands() {
            loader = new HachiCommandLoader();
            List<Template> commands = List.of(new Template("optional_string") {
                @Override public void setup() {
                    addOption(OptionType.STRING, "opt", "description", false);
                }
            }, new Template("optional_int") {
                @Override public void setup() {
                    addOption(OptionType.INTEGER, "opt", "description", false);
                }
            }, new Template("optional_double") {
                @Override public void setup() {
                    addOption(OptionType.NUMBER, "opt", "description", false);
                }
            }, new Template("optional_boolean") {
                @Override public void setup() {
                    addOption(OptionType.BOOLEAN, "opt", "description", false);
                }
            }, new Template("4") {
                @Override public void setup() {
                    addOption(OptionType.STRING, "string", "description", true);
                    addOption(OptionType.INTEGER, "integer", "description", true);
                    addOption(OptionType.NUMBER, "double", "description", true);
                    addOption(OptionType.BOOLEAN, "boolean", "description", true);
                }
            }, new Template("3") {
                @Override public void setup() {
                    addOption(OptionType.STRING, "string", "description", true);
                    addOption(OptionType.INTEGER, "integer", "description", true);
                    addOption(OptionType.NUMBER, "double", "description", true);
                    addOption(OptionType.BOOLEAN, "boolean", "description", false);
                }
            }, new Template("2") {
                @Override public void setup() {
                    addOption(OptionType.STRING, "string", "description", true);
                    addOption(OptionType.INTEGER, "integer", "description", true);
                    addOption(OptionType.NUMBER, "double", "description", false);
                    addOption(OptionType.BOOLEAN, "boolean", "description", false);
                }
            }, new Template("1") {
                @Override public void setup() {
                    addOption(OptionType.STRING, "string", "description", true);
                    addOption(OptionType.INTEGER, "integer", "description", false);
                    addOption(OptionType.NUMBER, "double", "description", false);
                    addOption(OptionType.BOOLEAN, "boolean", "description", false);
                }
            }, new Template("0") {
                @Override public void setup() {
                    addOption(OptionType.STRING, "string", "description", false);
                    addOption(OptionType.INTEGER, "integer", "description", false);
                    addOption(OptionType.NUMBER, "double", "description", false);
                    addOption(OptionType.BOOLEAN, "boolean", "description", false);
                }
            });
            for (Template command : commands) {
                command.setup();
                loader.loadCommand(command);
            }
            parser = new Parser(loader, prefix);
        }

        private static HachiCommandRequest createRequest(final Message message, final String commandName,
                final Map<String, Object> map) {
            HachiCommand command = loader.getCommand(commandName);
            assert command != null;
            HachiCommandOptions options = new HachiCommandOptionsImpl(map, command.getCommandData());
            return new HachiMessageCommandRequest(message, command, options);
        }

        public static int[] indexes() {
            return IntStream.range(0, 8).toArray();
        }

        public static List<String> optionalCommandNames() {
            return List.of("optional_string", "optional_int", "optional_double", "optional_boolean");
        }

        public static List<Object> options() {
            return List.of("string", 888, 4.5555, false);
        }

        public static List<String> wrongTypes() {
            return List.of("optional_int a", "optional_int -", "optional_int 0.2", "optional_double a",
                    "optional_double -", "optional_boolean 0", "optional_boolean a", "optional_boolean -");
        }

        @ParameterizedTest @MethodSource(value = "indexes") public void extraOptionalOption(final int i) {
            if (i >= optionalCommandNames().size()) {
                return;
            }
            String comm = optionalCommandNames().get(i);
            Object option = options().get(i);
            String content = prefix(comm + ' ' + option.toString() + ' ' + option);
            Message m = mock(Message.class);
            when(m.getContentRaw()).thenReturn(content);

            HachiCommandRequest r = parser.parse(m);

            assertNull(r, "Extra optional option does not cause parse failure.");
        }

        @ParameterizedTest @MethodSource(value = "wrongTypes") public void wrongTypeOptionalOption(final String s) {
            String content = prefix(s);
            Message m = mock(Message.class);
            when(m.getContentRaw()).thenReturn(content);

            HachiCommandRequest r = parser.parse(m);

            assertNull(r, "Wrong option type does not cause parse failure.");
        }

        @ParameterizedTest @MethodSource(value = "indexes") public void validOptionalMultiOption(final int i) {
            if (i > 4) {
                return;
            }
            String comm = "" + i;
            List<Object> options = List.of("str", 2, -0.4, false);
            for (int j = i; j < 4; j++) {
                StringBuilder content = new StringBuilder(prefix(comm));
                Map<String, Object> map = new HashMap<>();
                for (Object o : options.subList(0, j)) {
                    content.append(' ').append(o.toString());
                    map.put(o.getClass().getSimpleName().toLowerCase(), o);
                }
                Message m = mock(Message.class);
                when(m.getContentRaw()).thenReturn(content.toString());

                HachiCommandRequest r = parser.parse(m);
                HachiCommandRequest rExpected = createRequest(m, comm, map);

                assertEquals(rExpected, r, "Error parsing multiple options.");
            }
        }

        @ParameterizedTest @MethodSource(value = "indexes") public void validOptionalOption(final int i) {
            String comm = optionalCommandNames().get(i / 2);
            Object option = options().get(i / 2);
            boolean addOption = i % 2 == 0;
            String content = prefix(comm);
            if (addOption) {
                content += ' ' + option.toString();
            }
            Message m = mock(Message.class);
            when(m.getContentRaw()).thenReturn(content);
            Map<String, Object> options = new HashMap<>();
            if (addOption) {
                options.put("opt", option);
            }

            HachiCommandRequest r = parser.parse(m);
            HachiCommandRequest rExpected = createRequest(m, comm, options);

            assertEquals(rExpected, r, "Error parsing single optional option.");
        }

    }

}
