package net.digitalid.utility.math;

import java.math.BigInteger;

import javax.annotation.Nonnull;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.generator.conversion.Convertible;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * An exponent is a number that raises elements of an arbitrary group.
 */
@Immutable
public final class Exponent extends Number<Exponent, Object> implements Convertible {
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new exponent with the given value.
     * 
     * @param value the value of the new exponent.
     */
    private Exponent(@Nonnull BigInteger value) {
        super(value);
    }
    
    /**
     * Creates a new exponent with the given value.
     * 
     * @param value the value of the new exponent.
     * 
     * @return a new exponent with the given value.
     */
    @Pure
    public static @Nonnull Exponent get(@Nonnull BigInteger value) {
        return new Exponent(value);
    }
    
    /* -------------------------------------------------- Operations -------------------------------------------------- */
    
    /**
     * Adds the given exponent to this exponent.
     * 
     * @param exponent the exponent to be added.
     * 
     * @return the sum of this and the given exponent.
     */
    @Pure
    public @Nonnull Exponent add(@Nonnull Exponent exponent) {
        return new Exponent(getValue().add(exponent.getValue()));
    }
    
    /**
     * Subtracts the given exponent from this exponent.
     * 
     * @param exponent the exponent to be subtracted.
     * 
     * @return the difference between this and the given exponent.
     */
    @Pure
    public @Nonnull Exponent subtract(@Nonnull Exponent exponent) {
        return new Exponent(getValue().subtract(exponent.getValue()));
    }
    
    /**
     * Multiplies this exponent with the given exponent.
     * 
     * @param exponent the exponent to be multiplied.
     * 
     * @return the product of this and the given exponent.
     */
    @Pure
    public @Nonnull Exponent multiply(@Nonnull Exponent exponent) {
        return new Exponent(getValue().multiply(exponent.getValue()));
    }
    
    /**
     * Inverses this exponent in the given group.
     * 
     * @param group a group with known order.
     * 
     * @return the multiplicative inverse of this exponent.
     * 
     * @require group.getOrder().gcd(getValue()).compareTo(BigInteger.ONE) == 0 : "The exponent is relatively prime to the group order.";
     */
    @Pure
    public @Nonnull Exponent inverse(@Nonnull GroupWithKnownOrder group) {
        Require.that(group.getOrder().gcd(getValue()).compareTo(BigInteger.ONE) == 0).orThrow("The exponent is relatively prime to the group order.");
        
        return new Exponent(getValue().modInverse(group.getOrder()));
    }
    
    /**
     * Returns the next (or the same) relatively prime exponent.
     * 
     * @param group a group with known order.
     * 
     * @return the next (or the same) relatively prime exponent.
     */
    @Pure
    public @Nonnull Exponent getNextRelativePrime(@Nonnull GroupWithKnownOrder group) {
        @Nonnull BigInteger next = getValue();
        while (next.gcd(group.getOrder()).compareTo(BigInteger.ONE) == 1) { next = next.add(BigInteger.ONE); }
        return new Exponent(next);
    }
    
}
