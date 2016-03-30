package net.digitalid.utility.cryptography;

import java.security.SecureRandom;

import javax.annotation.Nonnull;
import javax.crypto.spec.IvParameterSpec;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.generator.conversion.Convertible;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.size.Size;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * The random initialization vector ensures that the cipher-texts of the same content are different.
 */
@Immutable
public final class InitializationVector extends IvParameterSpec implements Convertible {
    
    /* -------------------------------------------------- Generator -------------------------------------------------- */
    
    /**
     * Returns an array of 16 random bytes.
     */
    @Pure
    private static @Nonnull @Size(16) byte[] getRandomBytes() {
        final @Nonnull byte[] bytes = new byte[16];
        new SecureRandom().nextBytes(bytes);
        return bytes;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates a new initialization vector with the given bytes.
     * 
     * @param bytes the bytes of the new initialization vector.
     */
    private InitializationVector(@Nonnull @Size(16) byte[] bytes) {
        super(bytes);
        
        Require.that(bytes.length == 16).orThrow("The array contains 16 bytes.");
    }
    
    /**
     * Returns a new initialization vector with the given bytes.
     * 
     * @param bytes the bytes of the new initialization vector.
     * 
     * @return a new initialization vector with the given bytes.
     */
    @Pure
    public static @Nonnull InitializationVector get(@Nonnull @Size(16) byte[] bytes) {
        return new InitializationVector(bytes);
    }
    
    /**
     * Returns a random initialization vector.
     * 
     * @return a random initialization vector.
     */
    @Pure
    public static @Nonnull InitializationVector getRandom() {
        return get(getRandomBytes());
    }
    
}
