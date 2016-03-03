package net.digitalid.utility.functional;

import java.util.Iterator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.elements.NullableElements;

/**
 *
 */
public abstract class NullableIterable<T> implements Iterable<T> {
    
    private final @Nonnull @NullableElements Iterable<T> iterable;
    
    protected NullableIterable() {
        this.iterable = this;
    }
    
    protected NullableIterable(@Nonnull @NullableElements Iterable<T> iterable) {
        this.iterable = iterable;
    }
    
    /* -------------------------------------------------- Filter Non-Null Values -------------------------------------------------- */
    
    private Predicate<T> nonNullPredicate = new Predicate<T>() {
        
        @Override
        public boolean apply(@Nullable T object) {
            return object != null;
        }
        
    };
    
    public @Nonnull @NonNullableElements NonNullableIterable<T> filterNonNull() {
        return new NonNullableIterable<T>() {
        
            @Override public Iterator<T> iterator() {
                return new FilterIterator<>(iterable.iterator(), nonNullPredicate);
            }
        
        };
    }
    
    /* -------------------------------------------------- Filter -------------------------------------------------- */
    
    public @Nonnull @NullableElements NullableIterable<T> filter(@Nonnull final Predicate<T> predicate) {
        return new NullableIterable<T>() {
            
            @Override public Iterator<T> iterator() {
                return new FilterIterator<>(iterable.iterator(), predicate);
            }
            
        };
    }
    
    public @Nonnull @NonNullableElements <E> NonNullableIterable<E> map(@Nonnull final NonNullableFunction<T, E> function) {
        return new NonNullableIterable<E>() {
            
            @Override
            public Iterator<E> iterator() {
                return new MapIterator<>(iterable.iterator(), function);
            }
            
        };
    }
    
    public @Nonnull @NullableElements <E> NullableIterable<E> map(@Nonnull final NullableFunction<T, E> function) {
        return new NullableIterable<E>() {
            
            @Override
            public Iterator<E> iterator() {
                return new MapIterator<>(iterable.iterator(), function);
            }
            
        };
    }
    
    public @Nullable T reduce(@Nonnull final NullableBinaryFunction<T> function) {
        final @Nonnull Iterator<T> iterator = iterable.iterator();
        @Nullable T first = null;
        if (iterator.hasNext()) {
            first = iterator.next();
            while (iterator.hasNext()) {
                @Nonnull T second = iterator.next();
                first = function.apply(first, second);
            }
        }
        return first;
    }
    
}
