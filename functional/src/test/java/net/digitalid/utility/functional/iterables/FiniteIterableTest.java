package net.digitalid.utility.functional.iterables;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.circumfixes.Brackets;
import net.digitalid.utility.functional.interfaces.Collector;
import net.digitalid.utility.tuples.Pair;
import net.digitalid.utility.tuples.Quartet;

import org.junit.Test;

import static org.junit.Assert.*;

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
        assertTrue(iterable.limit(2).equals(FiniteIterable.of("alpha", "beta")));
    }
    
    @Test
    public void testSize() {
        assertEquals(4, iterable.size());
        assertFalse(iterable.isEmpty());
        assertFalse(iterable.isSingle());
        assertFalse(iterable.sizeAtMost(3));
        assertFalse(iterable.sizeAtLeast(5));
        assertTrue(iterable.sizeAtMost(4));
        assertTrue(iterable.sizeAtLeast(4));
    }
    
    @Test
    public void testGetFirst() {
        assertEquals("alpha", iterable.getFirst());
    }
    
    @Test
    public void testGetLast() {
        assertEquals("delta", iterable.getLast());
    }
    
    private final @Nonnull FiniteIterable<@Nonnull String> combinedIterable = iterable.combine(iterable);
    
    @Test
    public void testCombine() {
        assertElements(combinedIterable, "alpha", "beta", "gamma", "delta", "alpha", "beta", "gamma", "delta");
        assertElements(FiniteIterable.of().combine(FiniteIterable.of()));
    }
    
    @Test
    public void testIndexOf() {
        assertEquals(2, combinedIterable.indexOf("gamma"));
        assertEquals(-1, combinedIterable.indexOf("omega"));
    }
    
    @Test
    public void testLastIndexOf() {
        assertEquals(6, combinedIterable.lastIndexOf("gamma"));
        assertEquals(-1, combinedIterable.lastIndexOf("omega"));
    }
    
    @Test
    public void testCount() {
        assertEquals(2, combinedIterable.count("gamma"));
        assertEquals(0, combinedIterable.count("omega"));
    }
    
    @Test
    public void testContains() {
        assertTrue(combinedIterable.contains("gamma"));
        assertFalse(combinedIterable.contains("omega"));
    }
    
    @Test
    public void testContainsAll() {
        assertTrue(combinedIterable.containsAll(FiniteIterable.of("beta", "gamma")));
    }
    
    @Test
    public void testContainsNull() {
        assertFalse(combinedIterable.containsNull());
    }
    
    @Test
    public void testContainsDuplicates() {
        assertFalse(iterable.containsDuplicates());
        assertTrue(combinedIterable.containsDuplicates());
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
        assertEquals(19, length.get());
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
        assertEquals("beta", iterable.findFirst(e -> e.endsWith("ta")));
    }
    
    @Test
    public void testFindLast() {
        assertEquals("delta", iterable.findLast(e -> e.endsWith("ta")));
    }
    
    @Test
    public void testFindUnique() {
        assertEquals("gamma", iterable.findUnique(e -> e.contains("mm")));
    }
    
    @Test
    public void testMatchAny() {
        assertTrue(iterable.matchAny(e -> e.contains("mm")));
        assertFalse(iterable.matchAny(e -> e.contains("mmm")));
    }
    
    @Test
    public void testMatchAll() {
        assertTrue(iterable.matchAll(e -> e.contains("a")));
        assertFalse(iterable.matchAll(e -> e.contains("e")));
    }
    
    @Test
    public void testMatchNone() {
        assertTrue(iterable.matchNone(e -> e.contains("mmm")));
        assertFalse(iterable.matchNone(e -> e.contains("mm")));
    }
    
    @Test
    public void testReduce() {
        assertEquals("alpha : beta : gamma : delta", iterable.reduce((a, b) -> a + " : " + b));
    }
    
    @Test
    public void testCollect() {
        assertEquals(19, (int) iterable.collect(new Collector<String, Integer>() {
            
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
    public void testIsAscending() {
        assertFalse(iterable.isAscending());
        assertTrue(iterable.limit(3).isAscending());
    }
    
    @Test
    public void testIsStrictlyAscending() {
        assertTrue(FiniteIterable.of("a", "b", "c", "d").isStrictlyAscending());
        assertFalse(FiniteIterable.of("a", "b", "b", "c").isStrictlyAscending());
    }
    
    @Test
    public void testIsDescending() {
        assertFalse(iterable.isDescending());
        assertTrue(iterable.extract(2, 4).isDescending());
    }
    
    @Test
    public void testIsStrictlyDescending() {
        assertTrue(FiniteIterable.of("d", "c", "b", "a").isStrictlyDescending());
        assertFalse(FiniteIterable.of("c", "b", "b", "a").isStrictlyDescending());
    }
    
    @Test
    public void testSorted() {
        assertElements(iterable.sorted(), "alpha", "beta", "delta", "gamma");
    }
    
    @Test
    public void testReversed() {
        assertElements(iterable.reversed(), "delta", "gamma", "beta", "alpha");
    }
    
    @Test
    public void testMin() {
        assertEquals(iterable.min(), "alpha");
    }
    
    @Test
    public void testMax() {
        assertEquals(iterable.max(), "gamma");
    }
    
    private final @Nonnull FiniteIterable<@Nonnull Double> numbers = FiniteIterable.of(3.1416, 2.7183);
    
    @Test
    public void testSumAsLong() {
        assertEquals(5, numbers.sumAsLong());
    }
    
    @Test
    public void testSumAsDouble() {
        assertEquals(3.1416 + 2.7183, numbers.sumAsDouble(), 0.00001);
    }
    
    @Test
    public void testAverage() {
        assertEquals((3.1416 + 2.7183) / 2, numbers.average(), 0.00001);
    }
    
    @Test
    public void testJoin() {
        assertEquals("alpha, beta, gamma, delta", iterable.join());
        assertEquals("alpha / beta / gamma / delta", iterable.join(" / "));
        assertEquals("[alpha, beta, gamma, delta]", iterable.join(Brackets.SQUARE));
    }
    
    @Test
    public void testToArray() {
        final @Nonnull String[] array = new String[] {"alpha", "beta", "gamma", "delta"};
        assertArrayEquals(array, iterable.toArray());
        assertArrayEquals(array, iterable.toGenericArray());
        assertArrayEquals(array, iterable.toArray(new String[0]));
    }
    
    @Test
    public void testToList() {
        assertEquals(Arrays.asList("alpha", "beta", "gamma", "delta"), iterable.toList());
    }
    
    @Test
    public void testToSet() {
        assertEquals(new LinkedHashSet<>(Arrays.asList("alpha", "beta", "gamma", "delta")), iterable.toSet());
    }
    
    @Test
    public void testToMap() {
        final @Nonnull LinkedHashMap<@Nonnull Character, @Nonnull String> map = new LinkedHashMap<>();
        map.put('a', "alpha");
        map.put('b', "beta");
        map.put('g', "gamma");
        map.put('d', "delta");
        assertEquals(map, iterable.toMap(a -> a.charAt(0)));
    }
    
    @Test
    public void testGroupBy() {
        final @Nonnull LinkedHashMap<@Nonnull Character, @Nonnull List<@Nonnull String>> map = new LinkedHashMap<>();
        map.put('a', Arrays.asList("alpha"));
        map.put('b', Arrays.asList("beta"));
        map.put('g', Arrays.asList("gamma"));
        map.put('d', Arrays.asList("delta"));
        assertEquals(map, iterable.groupBy(a -> a.charAt(0)));
    }
    
}
