package net.digitalid.utility.functional;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.elements.NonNullableElements;

/**
 *
 */
public class NonNullableStream<T> implements Iterable<T> {
    
    private final @Nonnull @NonNullableElements Iterator<T> iterator;
    
    // TODO: not sure if iterator can be used here. What are the consequences?!
    protected NonNullableStream(@Nonnull @NonNullableElements Iterator<T> iterator) {
        this.iterator = iterator;
    }
    
    public NonNullableStream<T> filter(@Nonnull Predicate<T> predicate) {
        return new NonNullableStream<>(new NonNullableFilterStreamIterator<>(predicate, iterator));
    }
    
    public <E> NonNullableStream<E> map(@Nonnull NonNullableFunction<T, E> function) {
        return new NonNullableStream<>(new MapStreamIterator<>(function, iterator));
    }
    
    public <E> NullableStream<E> map(@Nonnull NullableFunction<T, E> function) {
        return new NullableStream<>(new NullableMapStreamIterator<>(function, iterator));
    }
    
    @Override
    public Iterator<T> iterator() {
        return iterator;
    }
    
}
