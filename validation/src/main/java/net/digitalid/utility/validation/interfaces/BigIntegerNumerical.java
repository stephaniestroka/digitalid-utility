package net.digitalid.utility.validation.interfaces;

import java.math.BigInteger;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.type.Functional;

/**
 * Numerical values can be validated with generated contracts.
 * 
 * @see net.digitalid.utility.validation.annotations.math
 */
@Functional
public interface BigIntegerNumerical<T extends BigIntegerNumerical<T>> extends CustomComparable<T> {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Returns the value of this numerical.
     */
    @Pure
    public @Nonnull BigInteger getValue();
    
    /**
     * Returns the bit length of this numerical.
     */
    @Pure
    public default @NonNegative int getBitLength() {
        return getValue().bitLength();
    }
    
    /* -------------------------------------------------- Comparison -------------------------------------------------- */
    
    @Pure
    @Override
    public default int compareTo(@Nonnull T object) {
        return getValue().compareTo(object.getValue());
    }
    
    /* -------------------------------------------------- Comparisons -------------------------------------------------- */
    
    /**
     * Returns whether this numerical is negative.
     */
    @Pure
    public default boolean isNegative() {
        return getValue().compareTo(BigInteger.ZERO) < 0;
    }
    
    /**
     * Returns whether this numerical is non-negative.
     */
    @Pure
    public default boolean isNonNegative() {
        return getValue().compareTo(BigInteger.ZERO) >= 0;
    }
    
    /**
     * Returns whether this numerical is positive.
     */
    @Pure
    public default boolean isPositive() {
        return getValue().compareTo(BigInteger.ZERO) > 0;
    }
    
    /**
     * Returns whether this numerical is non-positive.
     */
    @Pure
    public default boolean isNonPositive() {
        return getValue().compareTo(BigInteger.ZERO) <= 0;
    }
    
}
