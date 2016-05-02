package net.digitalid.utility.tuples;

import javax.annotation.Nonnull;

import org.junit.Assert;
import org.junit.Test;

public class PairTest extends TupleTest {
    
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
    public void testElementIteration() {
        final @Nonnull Pair<String, String> pair = Pair.of("Hello", "World");
        final Tuple.@Nonnull Iterator iterator = pair.iterator();
        Assert.assertEquals("Hello", iterator.next());
        Assert.assertEquals("World", iterator.next());
        Assert.assertFalse(iterator.hasNext());
    }
    
    @Test
    public void testContains() {
        final @Nonnull Pair<String, String> pair = Pair.of("Hello", "World");
        Assert.assertTrue(pair.contains("Hello"));
        Assert.assertFalse(pair.contains("Bye"));
    }
    
    @Test
    public void testSetters() {
        final @Nonnull Pair<String, String> pair = Pair.of("Hello", "World").set0("Bye").set1(null);
        Assert.assertEquals("Bye", pair.get0());
        Assert.assertNull(pair.get1());
    }
    
    @Test
    public void testEquals() {
        Assert.assertEquals(Pair.of("Hello", null), Pair.of("Hello", null));
        Assert.assertEquals(Pair.of("Hello", "World"), Pair.of("Hello", "World"));
        assertComparisonsWithEqualTuples(Pair.of("Hello", null), Pair.of("Hello", null));
        assertComparisonsWithEqualTuples(Pair.of("Hello", "World"), Pair.of("Hello", "World"));
    }
    
    @Test
    public void testToString() {
        Assert.assertEquals("(\"Hello\", null)", Pair.of("Hello", null).toString());
        Assert.assertEquals("(\"Hello\", \"World\")", Pair.of("Hello", "World").toString());
    }
    
    @Test
    public void testToArray() {
        Assert.assertArrayEquals(new String[] {"Hello", "World"}, Pair.of("Hello", "World").toArray());
        Assert.assertArrayEquals(new String[] {"Hello", "World"}, Pair.of("Hello", "World").toArray(new String[0]));
        Assert.assertArrayEquals(new String[] {"Hello", "World"}, Pair.of("Hello", "World").toArray(new String[2]));
    }
    
    @Test
    public void testCompareTo() {
        assertComparisonsWithUnequalTuples(Pair.of(null, "World"), Pair.of("Hello", "World"));
        assertComparisonsWithUnequalTuples(Pair.of("Bye", "World"), Pair.of("Hello", "World"));
        assertComparisonsWithUnequalTuples(Pair.of("Hello", "Universe"), Pair.of("Hello", "World"));
        
        assertComparisonsWithUnequalTuples(Pair.of("Hello", "World"), Triplet.of("Hello", "World", "!"));
        assertComparisonsWithUnequalTuples(Pair.of(null, "World"), Triplet.of(null, "World", null));
    }
    
}
