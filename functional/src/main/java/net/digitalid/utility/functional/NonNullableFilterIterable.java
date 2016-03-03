package net.digitalid.utility.functional;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.elements.NonNullableElements;

/**
 *
 */
public class NonNullableFilterIterable<T> extends NonNullableIterable<T> {
    
    private final @Nonnull Predicate<T> predicate;
    
    protected NonNullableFilterIterable(@Nonnull @NonNullableElements Iterable<T> iterable, @Nonnull Predicate<T> predicate) {
        super(iterable);
        this.predicate = predicate;
    }
    
    @Override
    public Iterator<T> iterator() {
        return new FilterIterator<>(super.iterator(), predicate);
    }
    
}
