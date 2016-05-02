package net.digitalid.utility.functional.iterables;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.functional.interfaces.Producer;
import net.digitalid.utility.validation.annotations.type.Mutable;

import org.junit.Test;

public class InfiniteIterableTest extends FunctionalIterableTest {
    
    @Test
    public void testRepeat() {
        assertElements(InfiniteIterable.repeat("a").limit(4), "a", "a", "a", "a");
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
        assertElements(InfiniteIterable.generate(new Incrementor()).limit(4), 0, 1, 2, 3);
    }
    
    @Test
    public void testIterate() {
        assertElements(InfiniteIterable.iterate(a -> a + 1, 0).limit(4), 1, 2, 3, 4);
    }
    
    @Test
    public void testGet() {
        final @Nonnull InfiniteIterable<@Nonnull Integer> iterable = InfiniteIterable.generate(new Incrementor());
        // TODO: The following assertions fail because the incrementor advances for every next() call.
//        System.out.println(iterable.limit(10).join());
//        Assert.assertEquals((Integer) 0, iterable.get(0));
//        Assert.assertEquals((Integer) 1, iterable.get(1));
//        Assert.assertEquals((Integer) 4, iterable.get(4));
//        Assert.assertEquals((Integer) 8, iterable.get(8));
    }
    
    @Test
    public void testFilter() {
        assertElements(InfiniteIterable.generate(new Incrementor()).filter(a -> a % 2 == 1).limit(4), 1, 3, 5, 7);
    }
    
    @Test
    public void testMap() {
        assertElements(InfiniteIterable.generate(new Incrementor()).map(a -> a * 2).limit(4), 0, 2, 4, 6);
    }
    
    @Test
    public void testSkip() {
    }
    
    @Test
    public void testZipShortest() {
    }
    
    @Test
    public void testZipLongest() {
    }
    
    @Test
    public void testFlatten() {
    }
    
    @Test
    public void testFlattenOne() {
    }
    
    @Test
    public void testFlattenAll() {
    }
    
}
