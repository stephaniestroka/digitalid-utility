package net.digitalid.utility.functional;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.elements.NullableElements;

/**
 *
 */
public class NullableStream<T> implements Iterable<T> {
    
    private final @Nonnull @NullableElements Iterator<T> iterator;
    
    // TODO: not sure if iterator can be used here. What are the consequences?!
    protected NullableStream(@Nonnull @NullableElements Iterator<T> iterator) {
        this.iterator = iterator;
    }
    
    /**
     * @param reaction defines the reaction that should be triggered when a null value is encountered.
     */
    public NonNullableStream ifNull(@Nonnull Reaction reaction) {
        return new NonNullableStream<>(new ReactionStreamIterator<>(reaction, iterator));
    }
    
    @Override public Iterator<T> iterator() {
        return null;
    }
    
}
