package net.digitalid.utility.functional;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.elements.NonNullableElements;

/**
 *
 */
public class ReactionStreamIterator<E1, E2> extends MapStreamIterator<E1, E2> {
    
    ReactionStreamIterator(@Nonnull NonNullableFunction<E1, E2> function, @Nonnull @NonNullableElements Iterator<E1> iterator) {
        super(function, iterator);
    }
    
}
