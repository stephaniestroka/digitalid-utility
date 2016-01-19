package net.digitalid.utility.cryptography;

import javax.annotation.Nonnull;

import net.digitalid.utility.math.Element;
import net.digitalid.utility.time.Time;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit testing of the class {@link KeyPair}.
 */
public final class KeyPairTest {
    
    @Test
    public void testKeyPair() {
        @Nonnull Time time = Time.getCurrent();
        final @Nonnull KeyPair keyPair = KeyPair.getRandom();
        final @Nonnull PrivateKey privateKey = keyPair.getPrivateKey();
        final @Nonnull PublicKey publicKey = keyPair.getPublicKey();
        System.out.println("\nKey Pair Generation: " + time.ago().getValue() + " ms\n");
        
        Assert.assertTrue(publicKey.verifySubgroupProof());
        
        for (int i = 0; i < 10; i++) {
            final @Nonnull Element m = publicKey.getCompositeGroup().getRandomElement();
            time = Time.getCurrent();
            final @Nonnull Element c = m.pow(publicKey.getE());
            System.out.println("Encryption (only algorithm): " + time.ago().getValue() + " ms");
            time = Time.getCurrent();
            Assert.assertEquals(c.pow(privateKey.getD()), m);
            System.out.println("Decryption (slow algorithm): " + time.ago().getValue() + " ms");
            time = Time.getCurrent();
            Assert.assertEquals(privateKey.powD(c), m);
            System.out.println("Decryption (fast algorithm): " + time.ago().getValue() + " ms\n");
        }
    }
    
}
