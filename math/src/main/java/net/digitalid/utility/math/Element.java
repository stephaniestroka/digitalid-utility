package net.digitalid.utility.math;

import java.math.BigInteger;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.CallSuper;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.contracts.Validate;
import net.digitalid.utility.generator.annotations.Normalize;
import net.digitalid.utility.math.annotations.InSameGroup;
import net.digitalid.utility.rootclass.RootClass;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.interfaces.BigIntegerNumerical;

/**
 * An element is a number in a certain group.
 * 
 * @invariant getValue().compareTo(BigInteger.ZERO) >= 0 && getValue().compareTo(getGroup().getModulus()) == -1 : "The value is non-negative and smaller than the group modulus.";
 */
@Immutable
public abstract class Element extends RootClass implements BigIntegerNumerical<Element>, GroupMember {
    
    /* -------------------------------------------------- Validatable -------------------------------------------------- */
    
    @Pure
    @Override
    @CallSuper
    public void validate() {
        super.validate();
        Validate.that(getValue().compareTo(BigInteger.ZERO) >= 0 && getValue().compareTo(getGroup().getModulus()) == -1).orThrow("The value has to be non-negative and smaller than the group modulus.");
    }
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    @Pure
    @Override
    @Normalize("value.mod(group.getModulus())")
    public abstract @Nonnull BigInteger getValue();
    
    /* -------------------------------------------------- Conditions -------------------------------------------------- */
    
    /**
     * Returns whether this element is relatively prime to the group modulus.
     */
    @Pure
    public boolean isRelativelyPrime() {
        return getValue().gcd(getGroup().getModulus()).equals(BigInteger.ONE);
    }
    
    /**
     * Returns whether this element is equal to the neutral element.
     */
    @Pure
    public boolean isOne() {
        return getValue().equals(BigInteger.ONE);
    }
    
    /* -------------------------------------------------- Operations -------------------------------------------------- */
    
    /**
     * Adds the given element to this element.
     */
    @Pure
    public @Nonnull @InSameGroup Element add(@Nonnull @InSameGroup Element element) {
        return new GeneratedElement(getGroup(), getValue().add(element.getValue()));
    }
    
    /**
     * Subtracts the given element from this element.
     */
    @Pure
    public @Nonnull @InSameGroup Element subtract(@Nonnull @InSameGroup Element element) {
        return new GeneratedElement(getGroup(), getValue().subtract(element.getValue()));
    }
    
    /**
     * Multiplies this element with the given element.
     */
    @Pure
    public @Nonnull @InSameGroup Element multiply(@Nonnull @InSameGroup Element element) {
        return new GeneratedElement(getGroup(), getValue().multiply(element.getValue()));
    }
    
    /**
     * Inverses this element.
     * 
     * @return the multiplicative inverse of this element.
     * 
     * @require isRelativelyPrime() : "The element has to be relatively prime to the group modulus.";
     */
    @Pure
    public @Nonnull @InSameGroup Element inverse() {
        Require.that(isRelativelyPrime()).orThrow("The element has to be relatively prime to the group modulus.");
        
        return new GeneratedElement(getGroup(), getValue().modInverse(getGroup().getModulus()));
    }
    
    /**
     * Raises this element by the given exponent.
     */
    @Pure
    public @Nonnull @InSameGroup Element pow(@Nonnull BigInteger exponent) {
        return new GeneratedElement(getGroup(), getValue().modPow(exponent, getGroup().getModulus()));
    }
    
    /**
     * Raises this element by the given exponent.
     */
    @Pure
    public @Nonnull @InSameGroup Element pow(@Nonnull Exponent exponent) {
        return pow(exponent.getValue());
    }
    
}
