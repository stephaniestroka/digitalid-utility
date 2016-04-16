package net.digitalid.utility.validation.interfaces;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Functional;

/**
 * This interface extends the {@link Comparable} interface with default comparisons.
 */
@Functional
public interface CustomComparable<T extends CustomComparable<T>> extends Comparable<T> {
    
    /* -------------------------------------------------- Comparison -------------------------------------------------- */
    
    @Pure
    @Override
    public int compareTo(@Nonnull T object);
    
    /* -------------------------------------------------- Comparisons -------------------------------------------------- */
    
    /**
     * Returns whether this object is equal to the given object.
     */
    @Pure
    public default boolean isEqualTo(@Nonnull T object) {
        return compareTo(object) == 0;
    }
    
    /**
     * Returns whether this object is greater than the given object.
     */
    @Pure
    public default boolean isGreaterThan(@Nonnull T object) {
        return compareTo(object) > 0;
    }
    
    /**
     * Returns whether this object is greater than or equal to the given object.
     */
    @Pure
    public default boolean isGreaterThanOrEqualTo(@Nonnull T object) {
        return compareTo(object) >= 0;
    }
    
    /**
     * Returns whether this object is less than the given object.
     */
    @Pure
    public default boolean isLessThan(@Nonnull T object) {
        return compareTo(object) < 0;
    }
    
    /**
     * Returns whether this object is less than or equal to the given object.
     */
    @Pure
    public default boolean isLessThanOrEqualTo(@Nonnull T object) {
        return compareTo(object) <= 0;
    }
    
}
