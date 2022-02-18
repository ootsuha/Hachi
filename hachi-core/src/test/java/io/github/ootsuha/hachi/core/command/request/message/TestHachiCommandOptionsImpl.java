package io.github.ootsuha.hachi.core.command.request.message;

import io.github.ootsuha.hachi.core.command.request.*;
import net.dv8tion.jda.api.interactions.commands.*;
import net.dv8tion.jda.api.interactions.commands.build.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestHachiCommandOptionsImpl {

    private OptionType getOptionType(final Object o) {
        if (o instanceof String) {
            return OptionType.STRING;
        } else if (o instanceof Integer) {
            return OptionType.INTEGER;
        } else if (o instanceof Boolean) {
            return OptionType.BOOLEAN;
        } else if (o instanceof Double) {
            return OptionType.NUMBER;
        }
        throw new IllegalArgumentException();
    }

    private CommandData createCommandData(final Map<String, Object> map) {
        return createCommandData(map, true);
    }

    private CommandData createCommandData(final Map<String, Object> map, final boolean required) {
        CommandData data = new CommandData("command", "description");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            OptionType type = getOptionType(entry.getValue());
            data.addOption(type, entry.getKey(), "description", required);
        }
        return data;
    }

    @Test
    public void getString() {
        Map<String, Object> m = new HashMap<>();
        m.put("a", "b");

        CommandData data = createCommandData(m);
        HachiCommandOptions o = new HachiCommandOptionsImpl(m, data);

        String value = o.getString("a");
        String valueExpected = "b";

        assertEquals(valueExpected, value);
    }

    @Test
    public void getInteger() {
        Map<String, Object> m = new HashMap<>();
        m.put("a", 1);

        CommandData data = createCommandData(m);
        HachiCommandOptions o = new HachiCommandOptionsImpl(m, data);

        int value = o.getInteger("a");
        int valueExpected = 1;

        assertEquals(valueExpected, value);
    }

    @Test
    public void getDouble() {
        Map<String, Object> m = new HashMap<>();
        m.put("a", 1.23);

        CommandData data = createCommandData(m);
        HachiCommandOptions o = new HachiCommandOptionsImpl(m, data);

        double value = o.getDouble("a");
        double valueExpected = 1.23;

        assertEquals(valueExpected, value);
    }

    @Test
    public void getBoolean() {
        Map<String, Object> m = new HashMap<>();
        m.put("a", false);
        m.put("b", true);

        CommandData data = createCommandData(m);
        HachiCommandOptions o = new HachiCommandOptionsImpl(m, data);

        boolean value = o.getBoolean("a");
        boolean value2 = o.getBoolean("b");

        assertFalse(value);
        assertTrue(value2);
    }

    @Test
    public void allTypes() {
        Map<String, Object> m = new HashMap<>();
        m.put("string", "hachiroku");
        m.put("int", -8620);
        m.put("double", 100000.0000000000000000000000000000000); // yeah
        m.put("bool", true);

        CommandData data = createCommandData(m);
        HachiCommandOptions o = new HachiCommandOptionsImpl(m, data);

        String s = o.getString("string");
        int i = o.getInteger("int");
        double d = o.getDouble("double");
        boolean b = o.getBoolean("bool");
        String sExpected = "hachiroku";
        int iExpected = -8620;
        double dExpected = 100000.0;

        assertEquals(sExpected, s);
        assertEquals(iExpected, i);
        assertEquals(dExpected, d);
        assertTrue(b);
    }

    @Test
    public void hasOption() {
        Map<String, Object> m = new HashMap<>();
        m.put("a", "");
        m.put("b", 0);
        m.put("c", 0.0);
        m.put("d", false);

        CommandData data = createCommandData(m);
        HachiCommandOptions o = new HachiCommandOptionsImpl(m, data);

        assertTrue(o.hasOption("a"));
        assertTrue(o.hasOption("b"));
        assertTrue(o.hasOption("c"));
        assertTrue(o.hasOption("d"));
        assertFalse(o.hasOption("A"));
        assertFalse(o.hasOption("B"));
        assertFalse(o.hasOption("C"));
        assertFalse(o.hasOption("D"));
        assertFalse(o.hasOption(null));
    }

    @Test
    public void missingOptionThrows() {
        Map<String, Object> m = new HashMap<>();

        CommandData data = createCommandData(m);
        HachiCommandOptions o = new HachiCommandOptionsImpl(m, data);

        assertThrows(AssertionError.class, () -> o.getString("a"));
        assertThrows(AssertionError.class, () -> o.getInteger("a"));
        assertThrows(AssertionError.class, () -> o.getDouble("a"));
        assertThrows(AssertionError.class, () -> o.getBoolean("a"));
    }

    @Test
    public void missingOptionOptional() {
        Map<String, Object> m = new HashMap<>();
        m.put("a", "");
        m.put("b", 0);
        m.put("c", 0.0);
        m.put("d", false);

        CommandData data = createCommandData(m, false);
        Map<String, Object> empty = new HashMap<>();
        HachiCommandOptions o = new HachiCommandOptionsImpl(empty, data);

        assertNull(o.getString("a"));
        assertNull(o.getInteger("b"));
        assertNull(o.getDouble("c"));
        assertNull(o.getBoolean("d"));
    }

    @Test
    public void incorrectTypeThrows() {
        Map<String, Object> m = new HashMap<>();
        m.put("a", "");
        m.put("b", 0);
        m.put("c", 0.0);
        m.put("d", false);

        CommandData data = createCommandData(m);
        HachiCommandOptions o = new HachiCommandOptionsImpl(m, data);

        assertDoesNotThrow(() -> o.getString("a"));
        assertThrows(AssertionError.class, () -> o.getString("b"));
        assertThrows(AssertionError.class, () -> o.getString("c"));
        assertThrows(AssertionError.class, () -> o.getString("d"));

        assertThrows(AssertionError.class, () -> o.getInteger("a"));
        assertDoesNotThrow(() -> o.getInteger("b"));
        assertThrows(AssertionError.class, () -> o.getInteger("c"));
        assertThrows(AssertionError.class, () -> o.getInteger("d"));

        assertThrows(AssertionError.class, () -> o.getDouble("a"));
        assertThrows(AssertionError.class, () -> o.getDouble("b"));
        assertDoesNotThrow(() -> o.getDouble("c"));
        assertThrows(AssertionError.class, () -> o.getDouble("d"));

        assertThrows(AssertionError.class, () -> o.getBoolean("a"));
        assertThrows(AssertionError.class, () -> o.getBoolean("b"));
        assertThrows(AssertionError.class, () -> o.getBoolean("c"));
        assertDoesNotThrow(() -> o.getBoolean("d"));
    }

    @Test
    public void missingOptionDataThrows() {
        Map<String, Object> m = new HashMap<>();
        m.put("a", "");
        m.put("b", 0);
        m.put("c", 0.0);
        m.put("d", false);

        Map<String, Object> empty = new HashMap<>();
        CommandData data = createCommandData(empty, false);
        HachiCommandOptions o = new HachiCommandOptionsImpl(m, data);

        assertThrows(AssertionError.class, () -> o.getString("a"));
        assertThrows(AssertionError.class, () -> o.getInteger("b"));
        assertThrows(AssertionError.class, () -> o.getDouble("c"));
        assertThrows(AssertionError.class, () -> o.getBoolean("d"));
    }

    @Test
    public void equals() {
        Map<String, Object> m = new HashMap<>();
        m.put("a", "");
        m.put("b", 0);
        m.put("c", 0.0);
        m.put("d", false);
        CommandData data = createCommandData(m, false);
        HachiCommandOptions o = new HachiCommandOptionsImpl(m, data);
        HachiCommandOptions oExpected = new HachiCommandOptionsImpl(new HashMap<>(m), data);

        assertEquals(oExpected, o);
    }
}
