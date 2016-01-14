package net.digitalid.utility.cryptography;

import net.digitalid.utility.errors.ShouldNeverHappenError;
import net.digitalid.utility.math.Element;

import javax.annotation.Nonnull;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 */
public class HashGenerator {

    public static @Nonnull BigInteger generateHash(@Nonnull Element... elements) {
        try {
            final @Nonnull MessageDigest instance = MessageDigest.getInstance("SHA-256");
            int offset = 0;
            for (@Nonnull Element element : elements) {
                final @Nonnull BigInteger bigInteger = element.getValue();
                final @Nonnull byte[] bytes = bigInteger.toByteArray();
                instance.update(bytes, offset, bytes.length);
                offset += bytes.length;
            }
            return new BigInteger(1, instance.digest());
        } catch (@Nonnull NoSuchAlgorithmException exception) {
            throw ShouldNeverHappenError.get("The hashing algorithm 'SHA-256' is not supported on this platform.", exception);
        }
    }

}
