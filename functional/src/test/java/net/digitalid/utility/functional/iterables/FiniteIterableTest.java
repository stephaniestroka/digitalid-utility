package net.digitalid.utility.functional.iterables;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.functional.interfaces.Collector;
import net.digitalid.utility.tuples.Pair;
import net.digitalid.utility.tuples.Quartet;

import org.junit.Assert;
import org.junit.Test;

public class FiniteIterableTest extends FunctionalIterableTest {
    
    @Test
    public void testCreationWithCollection() {
        assertElements(FiniteIterable.of(Quartet.of("alpha", "beta", "gamma", "delta")), "alpha", "beta", "gamma", "delta");
    }
    
    private final @Nonnull FiniteIterable<@Nonnull String> iterable = FiniteIterable.of("alpha", "beta", "gamma", "delta");
    
    @Test
    public void testCreationWithArray() {
        assertElements(iterable, "alpha", "beta", "gamma", "delta");
    }
    
    @Test
    public void testFilter() {
        assertElements(iterable.filter(a -> a.contains("e")), "beta", "delta");
        assertElements(iterable.filter(a -> !a.endsWith("ta")), "alpha", "gamma");
    }
    
    @Test
    public void testFilterNulls() {
        assertElements(FiniteIterable.of(null, "alpha", "beta", null, null, "gamma", "delta").filterNulls(), "alpha", "beta", "gamma", "delta");
    }
    
    @Test
    public void testMap() {
        assertElements(iterable.map(a -> a.replace("a", "")), "lph", "bet", "gmm", "delt");
    }
    
    @Test
    public void testInstanceOf() {
        final @Nonnull FiniteIterable<CharSequence> iterable = FiniteIterable.of(null, new StringBuilder("alpha"), "beta", "gamma", new StringBuilder("delta"));
        assertElements(iterable.instanceOf(String.class), "beta", "gamma");
    }
    
    @Test
    public void testSkip() {
        assertElements(iterable.skip(2), "gamma", "delta");
        assertElements(iterable.skip(4));
    }
    
    @Test
    public void testZipShortest() {
        assertElements(FiniteIterable.of("a", "b", "c").zipShortest(InfiniteIterable.repeat(0)), Pair.of("a", 0), Pair.of("b", 0), Pair.of("c", 0));
    }
    
    @Test
    public void testZipLongest() {
        assertElements(FiniteIterable.of("a", "b", "c").zipLongest(iterable), Pair.of("a", "alpha"), Pair.of("b", "beta"), Pair.of("c", "gamma"), Pair.of(null, "delta"));
    }
    
    private final @Nonnull FiniteIterable<@Nonnull Pair<@Nonnull Integer, @Nonnull Pair<@Nonnull String, @Nonnull String>>> nestedIterable = FiniteIterable.of(0, 1, 2).zipLongest(FiniteIterable.of("a", "b", "c").zipShortest(iterable));
    
    @Test
    public void testFlattenOne() {
        assertElements(nestedIterable.flattenOne(), 0, Pair.of("a", "alpha"), 1, Pair.of("b", "beta"), 2, Pair.of("c", "gamma"));
    }
    
    @Test
    public void testFlattenAll() {
        assertElements(nestedIterable.flattenAll(), 0, "a", "alpha", 1, "b", "beta", 2, "c", "gamma");
    }
    
    @Test
    public void testEquals() {
        Assert.assertTrue(iterable.limit(2).equals(FiniteIterable.of("alpha", "beta")));
    }
    
    @Test
    public void testSize() {
        Assert.assertEquals(4, iterable.size());
        Assert.assertFalse(iterable.isEmpty());
        Assert.assertFalse(iterable.isSingle());
        Assert.assertFalse(iterable.sizeAtMost(3));
        Assert.assertFalse(iterable.sizeAtLeast(5));
        Assert.assertTrue(iterable.sizeAtMost(4));
        Assert.assertTrue(iterable.sizeAtLeast(4));
    }
    
    @Test
    public void testGetFirst() {
        Assert.assertEquals("alpha", iterable.getFirst());
    }
    
    @Test
    public void testGetLast() {
        Assert.assertEquals("delta", iterable.getLast());
    }
    
    private final @Nonnull FiniteIterable<@Nonnull String> combinedIterable = iterable.combine(iterable);
    
    @Test
    public void testCombine() {
        assertElements(combinedIterable, "alpha", "beta", "gamma", "delta", "alpha", "beta", "gamma", "delta");
        assertElements(FiniteIterable.of().combine(FiniteIterable.of()));
    }
    
    @Test
    public void testIndexOf() {
        Assert.assertEquals(2, combinedIterable.indexOf("gamma"));
        Assert.assertEquals(-1, combinedIterable.indexOf("omega"));
    }
    
    @Test
    public void testLastIndexOf() {
        Assert.assertEquals(6, combinedIterable.lastIndexOf("gamma"));
        Assert.assertEquals(-1, combinedIterable.lastIndexOf("omega"));
    }
    
    @Test
    public void testCount() {
        Assert.assertEquals(2, combinedIterable.count("gamma"));
        Assert.assertEquals(0, combinedIterable.count("omega"));
    }
    
    @Test
    public void testContains() {
        Assert.assertTrue(combinedIterable.contains("gamma"));
        Assert.assertFalse(combinedIterable.contains("omega"));
    }
    
    @Test
    public void testContainsAll() {
        Assert.assertTrue(combinedIterable.containsAll(FiniteIterable.of("beta", "gamma")));
    }
    
    @Test
    public void testContainsNull() {
        Assert.assertFalse(combinedIterable.containsNull());
    }
    
    @Test
    public void testContainsDuplicates() {
        Assert.assertFalse(iterable.containsDuplicates());
        Assert.assertTrue(combinedIterable.containsDuplicates());
    }
    
    @Test
    public void testDistinct() {
        assertElements(iterable.distinct(), "alpha", "beta", "gamma", "delta");
        assertElements(combinedIterable.distinct(), "alpha", "beta", "gamma", "delta");
    }
    
    @Test
    public void testForEach() {
        final @Nonnull AtomicInteger length = new AtomicInteger(0);
        iterable.doForEach(a -> length.addAndGet(a.length()));
        Assert.assertEquals(19, length.get());
    }
    
    private final @Nonnull FiniteIterable<@Nonnull String> subiterable = iterable.limit(2);
    
    @Test
    public void testIntersect() {
        assertElements(iterable.intersect(subiterable), "alpha", "beta");
        assertElements(subiterable.intersect(iterable), "alpha", "beta");
    }
    
    @Test
    public void testExclude() {
        assertElements(iterable.exclude(subiterable), "gamma", "delta");
        assertElements(subiterable.exclude(iterable));
    }
    
    @Test
    public void testRepeated() {
        assertElements(subiterable.repeated().limit(5), "alpha", "beta", "alpha", "beta", "alpha");
    }
    
    @Test
    public void testFindFirst() {
        Assert.assertEquals("beta", iterable.findFirst(e -> e.endsWith("ta")));
    }
    
    @Test
    public void testFindLast() {
        Assert.assertEquals("delta", iterable.findLast(e -> e.endsWith("ta")));
    }
    
    @Test
    public void testFindUnique() {
        Assert.assertEquals("gamma", iterable.findUnique(e -> e.contains("mm")));
    }
    
    @Test
    public void testMatchAny() {
        Assert.assertTrue(iterable.matchAny(e -> e.contains("mm")));
        Assert.assertFalse(iterable.matchAny(e -> e.contains("mmm")));
    }
    
    @Test
    public void testMatchAll() {
        Assert.assertTrue(iterable.matchAll(e -> e.contains("a")));
        Assert.assertFalse(iterable.matchAll(e -> e.contains("e")));
    }
    
    @Test
    public void testMatchNone() {
        Assert.assertTrue(iterable.matchNone(e -> e.contains("mmm")));
        Assert.assertFalse(iterable.matchNone(e -> e.contains("mm")));
    }
    
    @Test
    public void testReduce() {
        Assert.assertEquals("alpha : beta : gamma : delta", iterable.reduce((a, b) -> a + " : " + b));
    }
    
    @Test
    public void testCollect() {
        Assert.assertEquals(19, (int) iterable.collect(new Collector<String, Integer>() {
            
            private int length = 0;
            
            @Impure
            @Override
            public void consume(@Nonnull String string) {
                length += string.length();
            }
            
            @Pure
            @Override
            public @Nonnull Integer getResult() {
                return length;
            }
            
        }));
    }
    
    @Test
    public void testIsOrdered() {
    }
    
    @Test
    public void testIsAscending() {
    }
    
    @Test
    public void testIsStrictlyAscending() {
    }
    
    @Test
    public void testIsDescending() {
    }
    
    @Test
    public void testIsStrictlyDescending() {
    }
    
    @Test
    public void testSorted_Comparator() {
    }
    
    @Test
    public void testSorted_0args() {
    }
    
    @Test
    public void testReversed() {
    }
    
    @Test
    public void testMin_Comparator_GenericType() {
    }
    
    @Test
    public void testMin_Comparator() {
    }
    
    @Test
    public void testMin_GenericType() {
    }
    
    @Test
    public void testMin_0args() {
    }
    
    @Test
    public void testMax_Comparator_GenericType() {
    }
    
    @Test
    public void testMax_Comparator() {
    }
    
    @Test
    public void testMax_GenericType() {
    }
    
    @Test
    public void testMax_0args() {
    }
    
    @Test
    public void testSumAsLong() {
    }
    
    @Test
    public void testSumAsDouble() {
    }
    
    @Test
    public void testAverage() {
    }
    
    @Test
    public void testJoin_4args() {
    }
    
    @Test
    public void testJoin_3args_1() {
    }
    
    @Test
    public void testJoin_CharSequence_CharSequence() {
    }
    
    @Test
    public void testJoin_3args_2() {
    }
    
    @Test
    public void testJoin_Fixes_CharSequence() {
    }
    
    @Test
    public void testJoin_Fixes() {
    }
    
    @Test
    public void testJoin_CharSequence() {
    }
    
    @Test
    public void testJoin_0args() {
    }
    
    @Test
    public void testToArray_0args() {
    }
    
    @Test
    public void testToGenericArray() {
    }
    
    @Test
    public void testToArray_GenericType() {
    }
    
    @Test
    public void testToList() {
    }
    
    @Test
    public void testToSet() {
    }
    
    @Test
    public void testToMap() {
    }
    
    @Test
    public void testGroupBy() {
    }
    
}
