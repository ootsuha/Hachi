package io.github.ootsuha.hachi.core.types;

import lombok.*;
import lombok.experimental.*;

/**
 * Wraps a single value so it can be mutable.
 *
 * @param <T> type of the value
 */
@AllArgsConstructor(staticName = "of")
@Accessors(fluent = true)
public class Mutable<T> {
    @Getter
    @Setter
    private T value;
}
