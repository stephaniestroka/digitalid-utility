package net.digitalid.utility.tuples;

import javax.annotation.Nonnull;

import org.junit.Assert;
import org.junit.Test;

public class TripletTest extends TupleTest {
    
    @Test
    public void testGetters() {
        final @Nonnull Triplet<String, String, String> triplet = Triplet.of("a", "b", "c");
        Assert.assertEquals("a", triplet.get0());
        Assert.assertEquals("b", triplet.get1());
        Assert.assertEquals("c", triplet.get2());
    }
    
    @Test
    public void testGettersWithIndex() {
        final @Nonnull Triplet<String, String, String> triplet = Triplet.of("a", "b", "c");
        Assert.assertEquals("a", triplet.get(0));
        Assert.assertEquals("b", triplet.get(1));
        Assert.assertEquals("c", triplet.get(2));
    }
    
    @Test
    public void testElementIteration() {
        final @Nonnull Triplet<String, String, String> triplet = Triplet.of("a", "b", "c");
        final Tuple.@Nonnull Iterator iterator = triplet.iterator();
        Assert.assertEquals("a", iterator.next());
        Assert.assertEquals("b", iterator.next());
        Assert.assertEquals("c", iterator.next());
        Assert.assertFalse(iterator.hasNext());
    }
    
    @Test
    public void testContains() {
        final @Nonnull Triplet<String, String, String> triplet = Triplet.of("a", "b", "c");
        Assert.assertTrue(triplet.contains("c"));
        Assert.assertFalse(triplet.contains("d"));
    }
    
    @Test
    public void testSetters() {
        final @Nonnull Triplet<String, String, String> triplet = Triplet.of("a", "b", "c").set0("d").set1(null).set2("f");
        Assert.assertEquals("d", triplet.get0());
        Assert.assertNull(triplet.get1());
        Assert.assertEquals("f", triplet.get2());
    }
    
    @Test
    public void testEquals() {
        Assert.assertEquals(Triplet.of("a", "b", "c"), Triplet.of("a", "b", "c"));
        Assert.assertEquals(Triplet.of("a", null, "c"), Triplet.of("a", null, "c"));
        assertComparisonsWithEqualTuples(Triplet.of("a", "b", "c"), Triplet.of("a", "b", "c"));
        assertComparisonsWithEqualTuples(Triplet.of("a", null, "c"), Triplet.of("a", null, "c"));
    }
    
    @Test
    public void testToString() {
        Assert.assertEquals("(\"a\", \"b\", \"c\")", Triplet.of("a", "b", "c").toString());
        Assert.assertEquals("(\"a\", null, \"c\")", Triplet.of("a", null, "c").toString());
    }
    
    @Test
    public void testToArray() {
        Assert.assertArrayEquals(new String[] {"a", "b", "c"}, Triplet.of("a", "b", "c").toArray());
        Assert.assertArrayEquals(new String[] {"a", "b", "c"}, Triplet.of("a", "b", "c").toArray(new String[0]));
        Assert.assertArrayEquals(new String[] {"a", "b", "c"}, Triplet.of("a", "b", "c").toArray(new String[3]));
    }
    
    @Test
    public void testCompareTo() {
        assertComparisonsWithUnequalTuples(Triplet.of(null, "b", "c"), Triplet.of("a", "b", "c"));
        assertComparisonsWithUnequalTuples(Triplet.of("a", "b", "c"), Triplet.of("aa", "b", "c"));
        assertComparisonsWithUnequalTuples(Triplet.of("a", "b", "c"), Triplet.of("a", "b", "d"));
        
        assertComparisonsWithUnequalTuples(Triplet.of("a", "b", "c"), Quartet.of("a", "b", "c", "d"));
        assertComparisonsWithUnequalTuples(Triplet.of(null, "b", "c"), Quartet.of(null, "b", "c", null));
    }
    
}
