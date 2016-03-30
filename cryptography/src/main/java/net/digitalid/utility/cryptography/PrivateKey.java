package net.digitalid.utility.cryptography;

import java.math.BigInteger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.generator.conversion.Convertible;
import net.digitalid.utility.math.Element;
import net.digitalid.utility.math.Exponent;
import net.digitalid.utility.math.GroupWithKnownOrder;
import net.digitalid.utility.tuples.annotations.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class stores the groups and exponents of a host's private key.
 */
@Immutable
public final class PrivateKey implements Convertible {
    
    /* -------------------------------------------------- Composite Group -------------------------------------------------- */
    
    /**
     * Stores the composite group of this private key.
     */
    private final @Nonnull GroupWithKnownOrder compositeGroup;
    
    /**
     * Returns the composite group of this private key.
     * 
     * @return the composite group of this private key.
     */
    @Pure
    public @Nonnull GroupWithKnownOrder getCompositeGroup() {
        return compositeGroup;
    }
    
    /* -------------------------------------------------- Prime Factors -------------------------------------------------- */
    
    /**
     * Stores the first prime factor of the composite group's modulus.
     */
    private final @Nonnull BigInteger p;
    
    /**
     * Stores the second prime factor of the composite group's modulus.
     */
    private final @Nonnull BigInteger q;
    
    /* -------------------------------------------------- Decryption Exponent -------------------------------------------------- */
    
    /**
     * Stores the decryption exponent d of this private key.
     */
    private final @Nonnull Exponent d;
    
    /**
     * Stores the decryption exponent in the subgroup of p.
     */
    private final @Nonnull BigInteger dMod_pMinus1;
    
    /**
     * Stores the decryption exponent in the subgroup of q.
     */
    private final @Nonnull BigInteger dMod_qMinus1;
    
    /**
     * Stores the identity of p's subgroup in the Chinese Remainder Theorem.
     */
    private final @Nonnull BigInteger pIdentityCRT;
    
    /**
     * Stores the identity of q's subgroup in the Chinese Remainder Theorem.
     */
    private final @Nonnull BigInteger qIdentityCRT;
    
    /**
     * Returns the exponent d of this private key.
     * 
     * @return the exponent d of this private key.
     */
    @Pure
    public @Nonnull Exponent getD() {
        return d;
    }
    
    /**
     * Returns the integer c raised to the power of d by using the Chinese Remainder Theorem.
     * 
     * @return the integer c raised to the power of d by using the Chinese Remainder Theorem.
     */
    @Pure
    public @Nonnull Element powD(@Nonnull BigInteger c) {
        final @Nonnull BigInteger mModP = c.modPow(dMod_pMinus1, p);
        final @Nonnull BigInteger mModQ = c.modPow(dMod_qMinus1, q);
        return compositeGroup.getElement(mModP.multiply(pIdentityCRT).add(mModQ.multiply(qIdentityCRT)));
    }
    
    /**
     * Returns the element c raised to the power of d by using the Chinese Remainder Theorem.
     * 
     * @return the element c raised to the power of d by using the Chinese Remainder Theorem.
     * 
     * @require c.getGroup().equals(compositeGroup) : "The element belongs to the composite group.";
     */
    @Pure
    public @Nonnull Element powD(@Nonnull Element c) {
        Require.that(c.getGroup().equals(compositeGroup)).orThrow("The element belongs to the composite group.");
        
        return powD(c.getValue());
    }
    
    /* -------------------------------------------------- Square Group -------------------------------------------------- */
    
    /**
     * Stores the square group of this private key.
     */
    private final @Nonnull GroupWithKnownOrder squareGroup;
    
    /**
     * Returns the square group of this private key.
     * 
     * @return the square group of this private key.
     */
    @Pure
    public @Nonnull GroupWithKnownOrder getSquareGroup() {
        return squareGroup;
    }
    
    /* -------------------------------------------------- Decryption Exponent -------------------------------------------------- */
    
    /**
     * Stores the decryption exponent x of this private key.
     */
    private final @Nonnull Exponent x;
    
    /**
     * Returns the exponent x of this private key.
     * 
     * @return the exponent x of this private key.
     */
    @Pure
    public @Nonnull Exponent getX() {
        return x;
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new private key with the given groups and exponents.
     * 
     * @param compositeGroup the composite group of the private key.
     * @param d the decryption exponent of the private key.
     * @param squareGroup the square group of the private key.
     * @param x the decryption exponent of the private key.
     * 
     * @require compositeGroup.getModulus().equals(p.multiply(q)) : "The modulus of the composite group is the product of p and q.";
     */
    private PrivateKey(@Nonnull GroupWithKnownOrder compositeGroup, @Nonnull BigInteger p, @Nonnull BigInteger q, @Nonnull Exponent d, @Nonnull GroupWithKnownOrder squareGroup, @Nonnull Exponent x) {
        Require.that(compositeGroup.getModulus().equals(p.multiply(q))).orThrow("The modulus of the composite group is the product of p and q.");
        
        this.compositeGroup = compositeGroup;
        this.p = p;
        this.q = q;
        this.d = d;
        this.squareGroup = squareGroup;
        this.x = x;
        
        @Nonnull BigInteger pMinus1 = p.subtract(BigInteger.ONE);
        @Nonnull BigInteger qMinus1 = q.subtract(BigInteger.ONE);
        
        this.dMod_pMinus1 = d.getValue().mod(pMinus1);
        this.dMod_qMinus1 = d.getValue().mod(qMinus1);
        
        this.pIdentityCRT = q.modInverse(p).multiply(q).mod(compositeGroup.getModulus());
        this.qIdentityCRT = p.modInverse(q).multiply(p).mod(compositeGroup.getModulus());
    }
    
    /**
     * Creates a new private key with the given groups and exponents.
     * 
     * @param compositeGroup the composite group of the private key.
     * @param d the decryption exponent of the private key.
     * @param squareGroup the square group of the private key.
     * @param x the decryption exponent of the private key.
     * 
     * @return a new private key with the given groups and exponents.
     * 
     * @require compositeGroup.getModulus().equals(p.multiply(q)) : "The modulus of the composite group is the product of p and q.";
     */
    @Pure
    public static @Nonnull PrivateKey get(@Nonnull GroupWithKnownOrder compositeGroup, @Nonnull BigInteger p, @Nonnull BigInteger q, @Nonnull Exponent d, @Nonnull GroupWithKnownOrder squareGroup, @Nonnull Exponent x) {
        return new PrivateKey(compositeGroup, p, q, d, squareGroup, x);
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean equals(@Nullable Object object) {
        if (object == this) { return true; }
        if (object == null || !(object instanceof PrivateKey)) { return false; }
        final @Nonnull PrivateKey other = (PrivateKey) object;
        return this.compositeGroup.equals(other.compositeGroup)
                && this.p.equals(other.p)
                && this.q.equals(other.q)
                && this.d.equals(other.d)
                && this.squareGroup.equals(other.squareGroup)
                && this.x.equals(other.x);
    }
    
    @Pure
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + compositeGroup.hashCode();
        hash = 53 * hash + p.hashCode();
        hash = 53 * hash + q.hashCode();
        hash = 53 * hash + d.hashCode();
        hash = 53 * hash + squareGroup.hashCode();
        hash = 53 * hash + x.hashCode();
        return hash;
    }
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return "Private Key [n = " + compositeGroup.getModulus() + ", p = " + p + ", q = " + q + ", d = " + d + ", z^2 = " + squareGroup.getModulus() + ", x = " + x + "]";
    }
    
}
