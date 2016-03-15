package net.digitalid.utility.functional.string;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.validation.annotations.method.Pure;

/**
 * Implementations of this interface convert elements to strings.
 * 
 *
 * @deprecated should be replaced with map() functions from {@link net.digitalid.utility.functional.iterable.NullableIterable NullableIterable} and 
 * {@link net.digitalid.utility.functional.iterable.NonNullableIterable NonNullableIterable}.
 * 
 * @see IterableConverter
 */
public interface ElementConverter<E> extends NonNullableElementConverter<E> {
    
    /**
     * Stores the default element converter that just uses the {@link Object#toString()} method.
     */
    public final static ElementConverter<Object> DEFAULT = new ElementConverter<Object>() {
        @Pure
        @Override
        public @Nonnull String toString(@Nullable Object element) {
            return String.valueOf(element);
        }
    };
    
    /**
     * Returns a string representation of the given element.
     * 
     * @param element the element to be returned as a string.
     * 
     * @return a string representation of the given element.
     */
    @Pure
    public @Nonnull String toString(@Nullable E element);
    
}
