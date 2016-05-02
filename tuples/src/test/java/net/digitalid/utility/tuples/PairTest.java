package net.digitalid.utility.tuples;

import javax.annotation.Nonnull;

import org.junit.Assert;
import org.junit.Test;

public class PairTest {
    
    @Test
    public void testGetters() {
        final @Nonnull Pair<String, String> pair = Pair.of("Hello", "World");
        Assert.assertEquals("Hello", pair.get0());
        Assert.assertEquals("World", pair.get1());
    }
    
    @Test
    public void testGettersWithIndex() {
        final @Nonnull Pair<String, String> pair = Pair.of("Hello", "World");
        Assert.assertEquals("Hello", pair.get(0));
        Assert.assertEquals("World", pair.get(1));
    }
    
    @Test
    public void testSetters() {
        final @Nonnull Pair<String, String> pair = Pair.of("Hello", "World").set0("Bye");
        Assert.assertEquals("Bye", pair.get0());
        Assert.assertEquals("World", pair.get1());
    }
    
}
