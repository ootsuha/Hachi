package io.github.ootsuha.hachi.core.parser;

/**
 * A pair of two values.
 * @param <L> type of first value
 * @param <R> type of second value
 */
public record Pair<L, R>(L left, R right) {
}
