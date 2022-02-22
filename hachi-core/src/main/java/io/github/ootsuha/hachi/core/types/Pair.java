package io.github.ootsuha.hachi.core.types;

/**
 * An immutable pair of two values.
 *
 * @param <L>   type of first value
 * @param <R>   type of second value
 * @param left  first value
 * @param right second value
 */
public record Pair<L, R>(L left, R right) {
    /**
     * Static constructor.
     *
     * @param <L>   type of first value
     * @param <R>   type of second value
     * @param left  first value
     * @param right second value
     * @return pair
     */
    public static <L, R> Pair<L, R> of(final L left, final R right) {
        // how do i make this work with lombok
        return new Pair<>(left, right);
    }
}
