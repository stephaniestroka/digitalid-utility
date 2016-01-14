package net.digitalid.utility.string.iterable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.digitalid.utility.validation.state.Pure;

/**
 * Implementations of this interface convert elements to strings.
 * 
 * TODO: Rename to NullableElementConverter and create a subclass NonNullableElementConverter.
 * 
 * @see IterableConverter
 */
public interface ElementConverter<E> {
    
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
