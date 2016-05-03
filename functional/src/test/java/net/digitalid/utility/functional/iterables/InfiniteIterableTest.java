package net.digitalid.utility.functional.iterables;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.functional.interfaces.Producer;
import net.digitalid.utility.tuples.Pair;
import net.digitalid.utility.validation.annotations.type.Mutable;

import org.junit.Assert;
import org.junit.Test;

public class InfiniteIterableTest extends FunctionalIterableTest {
    
    @Test
    public void testRepeat() {
        assertElements(InfiniteIterable.repeat("a").limit(4), "a", "a", "a", "a");
    }
    
    private final @Nonnull InfiniteIterable<@Nonnull Integer> incrementingIterable = InfiniteIterable.iterate(0, i -> i + 1);
    
    @Test
    public void testIterate() {
        assertElements(incrementingIterable.limit(4), 0, 1, 2, 3);
    }
    
    @Mutable
    private static class Incrementor implements Producer<@Nonnull Integer> {
        
        private int value = 0;
        
        @Impure
        @Override
        public @Nonnull Integer produce() {
            return value++;
        }
        
    }
    
    @Test
    public void testGenerate() {
        assertElements(InfiniteIterable.generate(() -> new Incrementor()).limit(4), 0, 1, 2, 3);
    }
    
    @Test
    public void testGet() {
        final @Nonnull InfiniteIterable<@Nonnull Integer> iterable = InfiniteIterable.generate(() -> new Incrementor());
        Assert.assertEquals((Integer) 0, iterable.get(0));
        Assert.assertEquals((Integer) 1, iterable.get(1));
        Assert.assertEquals((Integer) 4, iterable.get(4));
        Assert.assertEquals((Integer) 8, iterable.get(8));
    }
    
    @Test
    public void testFilter() {
        assertElements(incrementingIterable.filter(a -> a % 2 == 1).limit(4), 1, 3, 5, 7);
    }
    
    @Test
    public void testMap() {
        assertElements(incrementingIterable.map(a -> a * 2).limit(4), 0, 2, 4, 6);
    }
    
    @Test
    public void testSkip() {
        assertElements(incrementingIterable.skip(4).limit(4), 4, 5, 6, 7);
    }
    
    @Test
    public void testExtract() {
        assertElements(incrementingIterable.extract(2, 6), 2, 3, 4, 5);
    }
    
    @Test
    public void testZipShortest() {
        assertElements(incrementingIterable.zipShortest(incrementingIterable.skip(2).limit(2)), Pair.of(0, 2), Pair.of(1, 3));
        assertElements(incrementingIterable.zipShortest(incrementingIterable.skip(2)).limit(2), Pair.of(0, 2), Pair.of(1, 3));
    }
    
    @Test
    public void testZipLongest() {
        assertElements(incrementingIterable.zipLongest(incrementingIterable.skip(2).limit(2)).limit(3), Pair.of(0, 2), Pair.of(1, 3), Pair.of(2, null));
        assertElements(incrementingIterable.zipLongest(incrementingIterable.skip(2)).limit(3), Pair.of(0, 2), Pair.of(1, 3), Pair.of(2, 4));
    }
    
    private final @Nonnull InfiniteIterable<@Nonnull Pair<@Nonnull Integer, @Nonnull Pair<@Nonnull Integer, @Nonnull Integer>>> nestedIterable = incrementingIterable.zipShortest(incrementingIterable.map(a -> a * 2).zipShortest(incrementingIterable.map(a -> a * a)));
    
    @Test
    public void testFlattenOne() {
        assertElements(nestedIterable.flattenOne().limit(8), 0, Pair.of(0, 0), 1, Pair.of(2, 1), 2, Pair.of(4, 4), 3, Pair.of(6, 9));
    }
    
    @Test
    public void testFlattenAll() {
        assertElements(nestedIterable.flattenAll().limit(12), 0, 0, 0, 1, 2, 1, 2, 4, 4, 3, 6, 9);
    }
    
}
