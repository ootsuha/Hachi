package io.github.ootsuha.hachi.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility class that holds a set of all <code>HachiCommand</code> classes.
 */
public final class HachiCommandClassSet {
    /**
     * Set of command classes.
     */
    private static final Set<Class<?>> SET;
    /**
     * Package containing command classes.
     */
    private static final String PACKAGE_NAME = "io.github.ootsuha.hachi.command.usable";

    static {
        SET = getAllHachiCommandClasses();
    }

    /**
     * Private constructor so class cannot be instantiated.
     */
    private HachiCommandClassSet() {
    }

    /**
     * Gets all the classes in <code>PACKAGE_NAME</code>. Taken from
     * <a href="https://www.baeldung.com/java-find-all-classes-in-package">here
     * </a>.
     *
     * @return set of all classes in the package
     * @author not me lol
     */
    private static Set<Class<?>> getAllHachiCommandClasses() {
        InputStream stream =
                ClassLoader.getSystemClassLoader().getResourceAsStream(PACKAGE_NAME.replaceAll("[.]", "/"));
        assert stream != null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
        Set<Class<?>> set = reader.lines().filter(line -> line.endsWith(".class")).map(HachiCommandClassSet::getClass)
                .collect(Collectors.toSet());
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return set;
    }

    /**
     * Gets a class from <code>PACKAGE_NAME</code>.
     *
     * @param className name of the class
     * @return the class
     */
    private static Class<?> getClass(final String className) {
        try {
            return Class.forName(PACKAGE_NAME + "." + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            // handle the exception
        }
        return null;
    }

    /**
     * Gets the command class set.
     *
     * @return set of command classes
     */
    public static Set<Class<?>> getSet() {
        return new HashSet<>(SET);
    }
}
