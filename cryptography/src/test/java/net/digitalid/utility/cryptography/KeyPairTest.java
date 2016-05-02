package net.digitalid.utility.cryptography;

import javax.annotation.Nonnull;

import net.digitalid.utility.cryptography.key.KeyPair;
import net.digitalid.utility.cryptography.key.PrivateKey;
import net.digitalid.utility.cryptography.key.PublicKey;
import net.digitalid.utility.logging.Log;
import net.digitalid.utility.math.Element;
import net.digitalid.utility.testing.CustomTest;
import net.digitalid.utility.time.Time;
import net.digitalid.utility.time.TimeBuilder;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class KeyPairTest extends CustomTest {
    
    /**
     * Sets the length of the cryptographic parameters.
     */
    @BeforeClass
    public static void setUpParameters() {
        Parameters.FACTOR.set(128);
        Parameters.RANDOM_EXPONENT.set(64);
        Parameters.CREDENTIAL_EXPONENT.set(64);
        Parameters.RANDOM_CREDENTIAL_EXPONENT.set(96);
        Parameters.BLINDING_EXPONENT.set(96);
        Parameters.RANDOM_BLINDING_EXPONENT.set(128);
        Parameters.VERIFIABLE_ENCRYPTION.set(128);
        Parameters.ENCRYPTION_KEY.set(128);
    }
    
    @Test
    public void testKeyPair() {
        @Nonnull Time time = TimeBuilder.get().build();
        final @Nonnull KeyPair keyPair = KeyPair.withRandomValues();
        final @Nonnull PrivateKey privateKey = keyPair.getPrivateKey();
        final @Nonnull PublicKey publicKey = keyPair.getPublicKey();
        Log.information("Key Pair Generation: " + time.ago().getValue() + " ms");
        
        Assert.assertTrue(publicKey.verifySubgroupProof());
        
        for (int i = 0; i < 10; i++) {
            Log.information("Starting with another round:");
            final @Nonnull Element m = publicKey.getCompositeGroup().getRandomElement();
            time = TimeBuilder.get().build();
            final @Nonnull Element c = m.pow(publicKey.getE());
            Log.information("Encryption (only algorithm): " + time.ago().getValue() + " ms");
            time = TimeBuilder.get().build();
            Assert.assertEquals(c.pow(privateKey.getD()), m);
            Log.information("Decryption (slow algorithm): " + time.ago().getValue() + " ms");
            time = TimeBuilder.get().build();
            Assert.assertEquals(privateKey.powD(c), m);
            Log.information("Decryption (fast algorithm): " + time.ago().getValue() + " ms");
        }
    }
    
}
