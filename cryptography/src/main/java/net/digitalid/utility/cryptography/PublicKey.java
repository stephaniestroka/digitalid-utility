package net.digitalid.utility.cryptography;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.collaboration.annotations.TODO;
import net.digitalid.utility.collaboration.enumerations.Author;
import net.digitalid.utility.collaboration.enumerations.Priority;
import net.digitalid.utility.generator.annotations.Invariant;
import net.digitalid.utility.group.annotations.InGroup;
import net.digitalid.utility.math.Element;
import net.digitalid.utility.math.Exponent;
import net.digitalid.utility.math.GroupWithUnknownOrder;
import net.digitalid.utility.rootclass.RootClass;
import net.digitalid.utility.tuples.Pair;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class stores the groups, elements and exponents of a host's public key.
 * 
 * @see PrivateKey
 * @see KeyPair
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
     * Returns the hash of the temporary commitments in the subgroup proof.
     */
    @Pure
    public abstract @Nonnull Exponent getT();
    
    /**
     * Returns the solution for the proof that au is in the subgroup of ab.
     */
    @Pure
    public abstract @Nonnull Exponent getSu();
    
    /**
     * Returns the solution for the proof that ai is in the subgroup of ab.
     */
    @Pure
    public abstract @Nonnull Exponent getSi();
    
    /**
     * Returns the solution for the proof that av is in the subgroup of ab.
     */
    @Pure
    public abstract @Nonnull Exponent getSv();
    
    /**
     * Returns the solution for the proof that ao is in the subgroup of ab.
     */
    @Pure
    @Invariant(condition = "t.getValue().equals(HashGenerator.generateHash(ab.pow(su).multiply(au.pow(t)), ab.pow(si).multiply(ai.pow(t)), ab.pow(sv).multiply(av.pow(t)), ab.pow(so).multiply(ao.pow(t))))", message = "The elements au, ai, av and ao have to be in the subgroup of ab.")
    public abstract @Nonnull Exponent getSo();
    
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
    
    /* -------------------------------------------------- Verifiable Encryption -------------------------------------------------- */
    
    /**
     * Returns the verifiable encryption of the given value m with the random value r.
     */
    @Pure
    @TODO(task = "Move this method to where it is used.", date = "2016-04-19", author = Author.KASPAR_ETTER, priority = Priority.LOW)
    public @Nonnull Pair<@Nonnull Element, @Nonnull Element> getVerifiableEncryption(@Nonnull Exponent m, @Nonnull Exponent r) {
        return Pair.of(getY().pow(r).multiply(getZPlus1().pow(m)), getG().pow(r));
    }
    
}
