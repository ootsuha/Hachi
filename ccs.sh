#!/bin/bash
# creates io.github.ootsuha.hachi.command.HachiCommandClassSet

CLASSES=`ls src/main/java/io/github/ootsuha/hachi/command/usable`
IMPORT=""
for CLASS in $CLASSES; do
  IMPORT+="        SET.add(${CLASS:0:${#CLASS}-5}.class);"$'\n'
done

PATH="src/main/java/io/github/ootsuha/hachi/command/HachiCommandClassSet.java"

CONTENT="package io.github.ootsuha.hachi.command;

import io.github.ootsuha.hachi.command.usable.*;

import java.util.*;

/**
 * Utility class that holds a set of all <code>HachiCommand</code> classes.
 */
public final class HachiCommandClassSet {
    /**
     * Set of command classes.
     */
    private static final Set<Class<?>> SET;

    static {
        SET = new HashSet<>();
$IMPORT    }

    /**
     * Private constructor so class cannot be instantiated.
     */
    private HachiCommandClassSet() {
    }

    /**
     * Gets the command class set.
     *
     * @return set of command classes
     */
    public static Set<Class<?>> getSet() {
        return new HashSet<>(SET);
    }
}"

echo "$CONTENT" > $PATH