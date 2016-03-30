package net.digitalid.utility.cryptography;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.annotation.Nonnull;

import net.digitalid.utility.exceptions.MissingSupportException;
import net.digitalid.utility.math.Element;
import net.digitalid.utility.annotations.method.Pure;

/**
 * Generates cryptographic hashes.
 */
public class HashGenerator {

    /**
     * Generates and returns a cryptographic hash using the SHA-256 hash algorithm on the values of the given elements.
     */
    @Pure
    public static @Nonnull BigInteger generateHash(@Nonnull Element... elements) {
        try {
            final @Nonnull MessageDigest instance = MessageDigest.getInstance("SHA-256");
            int offset = 0;
            for (@Nonnull Element element : elements) {
                final @Nonnull BigInteger value = element.getValue();
                final @Nonnull byte[] bytes = value.toByteArray();
                instance.update(bytes); // TODO: Verify that this works!
                offset += bytes.length;
            }
            return new BigInteger(1, instance.digest());
        } catch (@Nonnull NoSuchAlgorithmException exception) {
            throw MissingSupportException.with("The hashing algorithm 'SHA-256' is not supported on this platform.", exception);
        }
    }

}
