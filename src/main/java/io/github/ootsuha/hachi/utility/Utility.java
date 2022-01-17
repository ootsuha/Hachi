package io.github.ootsuha.hachi.utility;

import java.util.*;

/**
 * Utility class.
 */
public final class Utility {
    /**
     * Seed for random numbers.
     */
    private static final int SEED = 8620;
    /**
     * Random number generator.
     */
    private static final Random RANDOM = new Random(SEED);

    /**
     * Private constructor so class cannot be instantiated.
     */
    private Utility() {
    }

    /**
     * Generates a random integer [min, max).
     *
     * @param min lower bound
     * @param max upper bound
     * @return an integer within the bounds
     */
    public static int randInt(final int min, final int max) {
        return RANDOM.nextInt(max - min) + min;
    }
}
