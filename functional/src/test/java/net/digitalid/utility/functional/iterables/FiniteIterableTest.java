package net.digitalid.utility.functional.iterables;

import javax.annotation.Nonnull;

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
    }
    
    @Test
    public void testGetFirst_GenericType() {
        
    }
    
    @Test
    public void testGetFirstOrNull() {
    }
    
    @Test
    public void testGetFirst_0args() {
    }
    
    @Test
    public void testGetLast_GenericType() {
    }
    
    @Test
    public void testGetLastOrNull() {
    }
    
    @Test
    public void testGetLast_0args() {
    }
    
    @Test
    public void testIndexOf() {
    }
    
    @Test
    public void testLastIndexOf() {
    }
    
    @Test
    public void testCount() {
    }
    
    @Test
    public void testContains() {
    }
    
    @Test
    public void testContainsAll_FiniteIterable() {
    }
    
    @Test
    public void testContainsAll_Collection() {
    }
    
    @Test
    public void testContainsNull() {
    }
    
    @Test
    public void testContainsDuplicates() {
    }
    
    @Test
    public void testDistinct() {
    }
    
    @Test
    public void testForEach() {
    }
    
    @Test
    public void testIntersect() {
    }
    
    @Test
    public void testExclude() {
    }
    
    @Test
    public void testCombine_FiniteIterable() {
    }
    
    @Test
    public void testCombine_InfiniteIterable() {
    }
    
    @Test
    public void testRepeated() {
    }
    
    @Test
    public void testFindFirst_Predicate_GenericType() {
    }
    
    @Test
    public void testFindFirst_Predicate() {
    }
    
    @Test
    public void testFindLast_Predicate_GenericType() {
    }
    
    @Test
    public void testFindLast_Predicate() {
    }
    
    @Test
    public void testFindUnique() {
    }
    
    @Test
    public void testMatchAny() {
    }
    
    @Test
    public void testMatchAll() {
    }
    
    @Test
    public void testMatchNone() {
    }
    
    @Test
    public void testReduce_BinaryOperator_GenericType() {
    }
    
    @Test
    public void testReduce_BinaryOperator() {
    }
    
    @Test
    public void testCollect() {
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
