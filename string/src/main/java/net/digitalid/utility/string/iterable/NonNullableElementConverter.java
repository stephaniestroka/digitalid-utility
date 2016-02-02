package net.digitalid.utility.string.iterable;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.method.Pure;

/**
 *
 */
public interface NonNullableElementConverter<E> {
    
    /**
     * Returns a string representation of the given element.
     * 
     * @param element the element to be returned as a string.
     * 
     * @return a string representation of the given element.
     */
    @Pure
    public @Nonnull String toString(@Nonnull E element);
 
}
