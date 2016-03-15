package net.digitalid.utility.functional.string;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.method.Pure;

/**
 * @deprecated should be replaced with map() functions from {@link net.digitalid.utility.functional.iterable.NullableIterable NullableIterable} and 
 * {@link net.digitalid.utility.functional.iterable.NonNullableIterable NonNullableIterable}.
 */
@Deprecated
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
