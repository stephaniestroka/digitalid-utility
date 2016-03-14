package net.digitalid.utility.functional.iterable;

import java.util.Iterator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.functional.iterable.infinite.InfiniteIterator;

/**
 * An infinite nullable iterable is an iterable that holds an infinite iterator,
 * which returns a given nullable element infinitely.
 */
public class InfiniteNullableIterable<T> extends NullableIterable<T> {
    
    /* -------------------------------------------------- Final Fields -------------------------------------------------- */
    
    /**
     * The nullable element that is returned infinitely.
     */
    private final @Nullable T element;
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    InfiniteNullableIterable(@Nullable T element) {
        this.element = element;
    }
    
    /* -------------------------------------------------- Iterable -------------------------------------------------- */
    
    @Override
    public Iterator<T> iterator() {
        return new InfiniteIterator<>(element);
    }
    
    @Override
    public int size() {
        return -1;
    }
    
}
