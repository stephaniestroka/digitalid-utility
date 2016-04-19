package net.digitalid.utility.cryptography;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.math.Element;
import net.digitalid.utility.math.Exponent;
import net.digitalid.utility.math.GroupWithUnknownOrder;
import net.digitalid.utility.math.annotations.InGroup;
import net.digitalid.utility.rootclass.RootClass;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class stores the groups, elements and exponents of a host's public key.
 * 
 * @invariant verifySubgroupProof() : "The elements au, ai, av and ao are in the subgroup of ab.";
 */
@Immutable
public abstract class PublicKey extends RootClass {
    
    /* -------------------------------------------------- Composite Group -------------------------------------------------- */
    
    /**
     * Returns the composite group for encryption and signing.
     */
    @Pure
    public abstract @Nonnull GroupWithUnknownOrder getCompositeGroup();
    
    /**
     * Returns the encryption and verification exponent.
     */
    @Pure
    public abstract @Nonnull Exponent getE();
    
    /**
     * Returns the base for blinding.
     */
    @Pure
    public abstract @Nonnull @InGroup("compositeGroup") Element getAb();
    
    /**
     * Returns the base of the client's secret.
     */
    @Pure
    public abstract @Nonnull @InGroup("compositeGroup") Element getAu();
    
    /**
     * Returns the base of the serial number.
     */
    @Pure
    public abstract @Nonnull @InGroup("compositeGroup") Element getAi();
    
    /**
     * Returns the base of the hashed identifier.
     */
    @Pure
    public abstract @Nonnull @InGroup("compositeGroup") Element getAv();
    
    /**
     * Returns the base of the exposed arguments.
     */
    @Pure
    public abstract @Nonnull @InGroup("compositeGroup") Element getAo();
    
    /* -------------------------------------------------- Subgroup Proof -------------------------------------------------- */
    
    /**
     * Stores the hash of the temporary commitments in the subgroup proof.
     */
    public final @Nonnull Exponent t;
    
    /**
     * Stores the solution for the proof that au is in the subgroup of ab.
     */
    public final @Nonnull Exponent su;
    
    /**
     * Stores the solution for the proof that ai is in the subgroup of ab.
     */
    public final @Nonnull Exponent si;
    
    /**
     * Stores the solution for the proof that av is in the subgroup of ab.
     */
    public final @Nonnull Exponent sv;
    
    /**
     * Stores the solution for the proof that ao is in the subgroup of ab.
     */
    public final @Nonnull Exponent so;
    
    /**
     * Returns whether the proof that au, ai, av and ao are in the subgroup of ab is correct.
     * 
     * @return {@code true} if the proof that au, ai, av and ao are in the subgroup of ab is correct, {@code false} otherwise.
     */
    @Pure
    public boolean verifySubgroupProof() {
        final @Nonnull Element tu = getAb().pow(su).multiply(getAu().pow(t));
        final @Nonnull Element ti = getAb().pow(si).multiply(getAi().pow(t));
        final @Nonnull Element tv = getAb().pow(sv).multiply(getAv().pow(t));
        final @Nonnull Element to = getAb().pow(so).multiply(getAo().pow(t));
        
        return t.getValue().equals(HashGenerator.generateHash(tu, ti, tv, to));
    }
    
    /* -------------------------------------------------- Square Group -------------------------------------------------- */
    
    /**
     * Returns the square group for verifiable encryption.
     */
    @Pure
    public abstract @Nonnull GroupWithUnknownOrder getSquareGroup();
    
    /**
     * Returns the generator of the square group.
     */
    @Pure
    public abstract @Nonnull @InGroup("squareGroup") Element getG();
    
    /**
     * Returns the encryption element of the square group.
     */
    @Pure
    public abstract @Nonnull @InGroup("squareGroup") Element getY();
    
    /**
     * Returns the encryption base of the square group.
     */
    @Pure
    public abstract @Nonnull @InGroup("squareGroup") Element getZPlus1();
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */

    protected PublicKey(@Nonnull Exponent t, @Nonnull Exponent su, @Nonnull Exponent si, @Nonnull Exponent sv, @Nonnull Exponent so) {
        this.t = t;
        this.su = su;
        this.si = si;
        this.sv = sv;
        this.so = so;
        
        Require.that(verifySubgroupProof()).orThrow("The elements au, ai, av and ao are in the subgroup of ab.");
    }
    
    /* -------------------------------------------------- Verifiable Encryption -------------------------------------------------- */
    
    /**
     * Returns the verifiable encryption of the given value m with the random value r.
     * 
     * @param m the message to be verifiably encrypted.
     * @param r the random value to blind the message.
     * 
     * @return the verifiable encryption of the given value m with the random value r.
     */
    // TODO (kaspar) : decide where to move this piece of code. Suggestions: Create a VerifiableEncryption class which contains the fields w1 and w2. Provide a static method which generates a VerifiableEncryption class based on the inputs y, z, m, g, r.
/*    @Pure
    public @Nonnull @BasedOn("verifiable.encryption@core.digitalid.net") Block getVerifiableEncryption(@Nonnull Exponent m, @Nonnull Exponent r) {
        final @Nonnull FreezableArray<Block> elements = FreezableArray.get(2);
        elements.set(0, Encode.nonNullable(W1, y.pow(r).multiply(zPlus1.pow(m))));
        elements.set(1, Encode.nonNullable(W2, g.pow(r)));
        return TupleWrapper.encode(VERIFIABLE_ENCRYPTION, elements.freeze());
    }*/
    
}
