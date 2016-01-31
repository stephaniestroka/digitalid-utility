package net.digitalid.utility.cryptography;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.generator.conversion.Convertible;
import net.digitalid.utility.math.Element;
import net.digitalid.utility.math.Exponent;
import net.digitalid.utility.math.GroupWithUnknownOrder;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.annotations.method.Pure;

/**
 * This class stores the groups, elements and exponents of a host's public key.
 * 
 * @invariant verifySubgroupProof() : "The elements au, ai, av and ao are in the subgroup of ab.";
 */
@Immutable
public final class PublicKey implements Convertible {
    
    /**
     * Stores the composite group for encryption and signing.
     */
    private final @Nonnull GroupWithUnknownOrder compositeGroup;
    
    /**
     * Returns the composite group for encryption and signing.
     * 
     * @return the composite group for encryption and signing.
     */
    @Pure
    public @Nonnull GroupWithUnknownOrder getCompositeGroup() {
        return compositeGroup;
    }
    
    /* -------------------------------------------------- Encryption Exponent -------------------------------------------------- */
    
    /**
     * Stores the encryption and verification exponent.
     */
    private final @Nonnull Exponent e;
    
    /**
     * Returns the encryption and verification exponent.
     * 
     * @return the encryption and verification exponent.
     */
    @Pure
    public @Nonnull Exponent getE() {
        return e;
    }
    
    /* -------------------------------------------------- Base for Blinding -------------------------------------------------- */
    
    /**
     * Stores the base for blinding.
     */
    private final @Nonnull Element ab;
    
    /**
     * Returns the base for blinding.
     * 
     * @return the base for blinding.
     */
    @Pure
    public @Nonnull Element getAb() {
        return ab;
    }
    
    /* -------------------------------------------------- Base for Secret -------------------------------------------------- */
    
    /**
     * Stores the base of the client's secret.
     */
    private final @Nonnull Element au;
    
    /**
     * Returns the base of the client's secret.
     * 
     * @return the base of the client's secret.
     */
    @Pure
    public @Nonnull Element getAu() {
        return au;
    }
    
    /* -------------------------------------------------- Base for Serial -------------------------------------------------- */
    
    /**
     * Stores the base of the serial number.
     */
    private final @Nonnull Element ai;
    
    /**
     * Returns the base of the serial number.
     * 
     * @return the base of the serial number.
     */
    @Pure
    public @Nonnull Element getAi() {
        return ai;
    }
    
    /* -------------------------------------------------- Base for Identifier -------------------------------------------------- */
    
    /**
     * Stores the base of the hashed identifier.
     */
    private final @Nonnull Element av;
    
    /**
     * Returns the base of the hashed identifier.
     * 
     * @return the base of the hashed identifier.
     */
    @Pure
    public @Nonnull Element getAv() {
        return av;
    }
    
    /* -------------------------------------------------- Base for Arguments -------------------------------------------------- */
    
    /**
     * Stores the base of the exposed arguments.
     */
    private final @Nonnull Element ao;
    
    /**
     * Returns the base of the exposed arguments.
     * 
     * @return the base of the exposed arguments.
     */
    @Pure
    public @Nonnull Element getAo() {
        return ao;
    }
    
    /* -------------------------------------------------- Subgroup Proof -------------------------------------------------- */
    
    /**
     * Stores the hash of the temporary commitments in the subgroup proof.
     */
    private final @Nonnull Exponent t;
    
    /**
     * Stores the solution for the proof that au is in the subgroup of ab.
     */
    private final @Nonnull Exponent su;
    
    /**
     * Stores the solution for the proof that ai is in the subgroup of ab.
     */
    private final @Nonnull Exponent si;
    
    /**
     * Stores the solution for the proof that av is in the subgroup of ab.
     */
    private final @Nonnull Exponent sv;
    
    /**
     * Stores the solution for the proof that ao is in the subgroup of ab.
     */
    private final @Nonnull Exponent so;
    
    /**
     * Returns whether the proof that au, ai, av and ao are in the subgroup of ab is correct.
     * 
     * @return {@code true} if the proof that au, ai, av and ao are in the subgroup of ab is correct, {@code false} otherwise.
     */
    @Pure
    public boolean verifySubgroupProof() {
        final @Nonnull Element tu = ab.pow(su).multiply(au.pow(t));
        final @Nonnull Element ti = ab.pow(si).multiply(ai.pow(t));
        final @Nonnull Element tv = ab.pow(sv).multiply(av.pow(t));
        final @Nonnull Element to = ab.pow(so).multiply(ao.pow(t));
        
        return t.getValue().equals(HashGenerator.generateHash(tu, ti, tv, to));
    }
    
    /* -------------------------------------------------- Square Group -------------------------------------------------- */
    
    /**
     * Stores the square group for verifiable encryption.
     */
    private final @Nonnull GroupWithUnknownOrder squareGroup;
    
    /**
     * Returns the square group for verifiable encryption.
     * 
     * @return the square group for verifiable encryption.
     */
    @Pure
    public @Nonnull GroupWithUnknownOrder getSquareGroup() {
        return squareGroup;
    }
    
    /* -------------------------------------------------- Group Generator -------------------------------------------------- */
    
    /**
     * Stores the generator of the square group.
     */
    private final @Nonnull Element g;
    
    /**
     * Returns the generator of the square group.
     * 
     * @return the generator of the square group.
     */
    @Pure
    public @Nonnull Element getG() {
        return g;
    }
    
    /* -------------------------------------------------- Encryption Element -------------------------------------------------- */
    
    /**
     * Stores the encryption element of the square group.
     */
    private final @Nonnull Element y;

    /**
     * Returns the encryption element of the square group.
     * 
     * @return the encryption element of the square group.
     */
    @Pure
    public @Nonnull Element getY() {
        return y;
    }
    
    /* -------------------------------------------------- Encryption Base -------------------------------------------------- */
    
    /**
     * Stores the encryption base of the square group.
     */
    private final @Nonnull Element zPlus1;
    
    /**
     * Returns the encryption base of the square group.
     * 
     * @return the encryption base of the square group.
     */
    @Pure
    public @Nonnull Element getZPlus1() {
        return zPlus1;
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */

    /**
     * Creates a new public key with the given groups, bases and exponents.
     * 
     * @param compositeGroup the composite group for encryption and signing.
     * @param e the encryption and verification exponent.
     * 
     * @param ab the base for blinding.
     * @param au the base of the client's secret.
     * @param ai the base of the serial number.
     * @param av the base of the hashed identifier.
     * @param ao the base of the exposed arguments.
     * 
     * @param t the hash of the temporary commitments in the subgroup proof.
     * @param su the solution for the proof that au is in the subgroup of ab.
     * @param si the solution for the proof that ai is in the subgroup of ab.
     * @param sv the solution for the proof that av is in the subgroup of ab.
     * @param so the solution for the proof that ao is in the subgroup of ab.
     * 
     * @param squareGroup the square group for verifiable encryption.
     * @param g the generator of the square group.
     * @param y the encryption element of the square group.
     * @param zPlus1 the encryption base of the square group.
     * 
     * @require ab.isElement(compositeGroup) : "ab is an element in the composite group.";
     * @require au.isElement(compositeGroup) : "au is an element in the composite group.";
     * @require ai.isElement(compositeGroup) : "ai is an element in the composite group.";
     * @require av.isElement(compositeGroup) : "av is an element in the composite group.";
     * @require ao.isElement(compositeGroup) : "ao is an element in the composite group.";
     *          
     * @require g.isElement(squareGroup) : "g is an element in the square group.";
     * @require y.isElement(squareGroup) : "y is an element in the square group.";
     * @require zPlus1.isElement(squareGroup) : "zPlus1 is an element in the square group.";
     *          
     * @require verifySubgroupProof() : "Assert that au, ai, av and ao are in the subgroup of ab.";
     */
    private PublicKey(@Nonnull GroupWithUnknownOrder compositeGroup, @Nonnull Exponent e, @Nonnull Element ab, @Nonnull Element au, @Nonnull Element ai, @Nonnull Element av, @Nonnull Element ao, @Nonnull Exponent t, @Nonnull Exponent su, @Nonnull Exponent si, @Nonnull Exponent sv, @Nonnull Exponent so, @Nonnull GroupWithUnknownOrder squareGroup, @Nonnull Element g, @Nonnull Element y, @Nonnull Element zPlus1) {
        assert ab.isElement(compositeGroup) : "ab is an element in the composite group.";
        assert au.isElement(compositeGroup) : "au is an element in the composite group.";
        assert ai.isElement(compositeGroup) : "ai is an element in the composite group.";
        assert av.isElement(compositeGroup) : "av is an element in the composite group.";
        assert ao.isElement(compositeGroup) : "ao is an element in the composite group.";
        
        assert g.isElement(squareGroup) : "g is an element in the square group.";
        assert y.isElement(squareGroup) : "y is an element in the square group.";
        assert zPlus1.isElement(squareGroup) : "zPlus1 is an element in the square group.";
        
        this.compositeGroup = compositeGroup;
        this.e = e;
        this.ab = ab;
        this.au = au;
        this.ai = ai;
        this.av = av;
        this.ao = ao;
        this.t = t;
        this.su = su;
        this.si = si;
        this.sv = sv;
        this.so = so;
        this.squareGroup = squareGroup;
        this.g = g;
        this.y = y;
        this.zPlus1 = zPlus1;
        
        assert verifySubgroupProof() : "The elements au, ai, av and ao are in the subgroup of ab.";
    }
    
    /**
     * Creates a new public key with the given groups, bases and exponents.
     * 
     * @param compositeGroup the composite group for encryption and signing.
     * @param e the encryption and verification exponent.
     * 
     * @param ab the base for blinding.
     * @param au the base of the client's secret.
     * @param ai the base of the serial number.
     * @param av the base of the hashed identifier.
     * @param ao the base of the exposed arguments.
     * 
     * @param t the hash of the temporary commitments in the subgroup proof.
     * @param su the solution for the proof that au is in the subgroup of ab.
     * @param si the solution for the proof that ai is in the subgroup of ab.
     * @param sv the solution for the proof that av is in the subgroup of ab.
     * @param so the solution for the proof that ao is in the subgroup of ab.
     * 
     * @param squareGroup the square group for verifiable encryption.
     * @param g the generator of the square group.
     * @param y the encryption element of the square group.
     * @param zPlus1 the encryption base of the square group.
     * 
     * @require ab.isElement(compositeGroup) : "ab is an element in the composite group.";
     * @require au.isElement(compositeGroup) : "au is an element in the composite group.";
     * @require ai.isElement(compositeGroup) : "ai is an element in the composite group.";
     * @require av.isElement(compositeGroup) : "av is an element in the composite group.";
     * @require ao.isElement(compositeGroup) : "ao is an element in the composite group.";
     *          
     * @require g.isElement(squareGroup) : "g is an element in the square group.";
     * @require y.isElement(squareGroup) : "y is an element in the square group.";
     * @require zPlus1.isElement(squareGroup) : "zPlus1 is an element in the square group.";
     *          
     * @require verifySubgroupProof() : "Assert that au, ai, av and ao are in the subgroup of ab.";
     */
    @Pure
    public static @Nonnull PublicKey get(@Nonnull GroupWithUnknownOrder compositeGroup, @Nonnull Exponent e, @Nonnull Element ab, @Nonnull Element au, @Nonnull Element ai, @Nonnull Element av, @Nonnull Element ao, @Nonnull Exponent t, @Nonnull Exponent su, @Nonnull Exponent si, @Nonnull Exponent sv, @Nonnull Exponent so, @Nonnull GroupWithUnknownOrder squareGroup, @Nonnull Element g, @Nonnull Element y, @Nonnull Element zPlus1) {
        return new PublicKey(compositeGroup, e, ab, au, ai, av, ao, t, su, si, sv, so, squareGroup, g, y, zPlus1);
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

    /* -------------------------------------------------- Object -------------------------------------------------- */

    @Pure
    @Override
    public @Nonnull String toString() {
        return "Public Key [n = " + compositeGroup.getModulus() + ", e = " + e + ", z^2 = " + squareGroup.getModulus() + ", g = " + g + ", y = " + y + "]";
    }

}
