package net.digitalid.utility.interfaces;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Functional;

/**
 * Numerical values can be validated with generated contracts.
 */
@Functional
public interface LongNumerical<T extends LongNumerical<T>> extends CustomComparable<T> {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Returns the value of this numerical.
     */
    @Pure
    public long getValue();
    
    /* -------------------------------------------------- Comparison -------------------------------------------------- */
    
    @Pure
    @Override
    public default int compareTo(@Nonnull T object) {
        return Long.compare(getValue(), object.getValue());
    }
    
    /* -------------------------------------------------- Comparisons -------------------------------------------------- */
    
    /**
     * Returns whether this numerical is negative.
     */
    @Pure
    public default boolean isNegative() {
        return getValue() < 0;
    }
    
    /**
     * Returns whether this numerical is non-negative.
     */
    @Pure
    public default boolean isNonNegative() {
        return getValue() >= 0;
    }
    
    /**
     * Returns whether this numerical is positive.
     */
    @Pure
    public default boolean isPositive() {
        return getValue() > 0;
    }
    
    /**
     * Returns whether this numerical is non-positive.
     */
    @Pure
    public default boolean isNonPositive() {
        return getValue() <= 0;
    }
    
}
