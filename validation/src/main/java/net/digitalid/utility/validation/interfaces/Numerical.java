package net.digitalid.utility.validation.interfaces;

import java.math.BigInteger;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.method.Pure;

/**
 * Numerical values can be validated with generated contracts.
 * 
 * @see net.digitalid.utility.validation.annotations.math
 */
public interface Numerical {
    
    /**
     * Returns the value of this numerical.
     */
    @Pure
    public @Nonnull BigInteger getValue();
    
}
