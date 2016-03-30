package net.digitalid.utility.functional.iterables;

import net.digitalid.utility.functional.interfaces.Predicate;
import net.digitalid.utility.functional.interfaces.Producer;
import net.digitalid.utility.functional.interfaces.UnaryFunction;
import net.digitalid.utility.functional.interfaces.UnaryOperator;
import net.digitalid.utility.functional.iterators.FilteringIterator;
import net.digitalid.utility.functional.iterators.FlatteningIterator;
import net.digitalid.utility.functional.iterators.GeneratingIterator;
import net.digitalid.utility.functional.iterators.IteratingIterator;
import net.digitalid.utility.functional.iterators.MappingIterator;
import net.digitalid.utility.functional.iterators.PruningIterator;
import net.digitalid.utility.functional.iterators.RepeatingIterator;
import net.digitalid.utility.functional.iterators.ZippingIterator;
import net.digitalid.utility.tuples.Pair;
import net.digitalid.utility.annotations.method.Pure;

/**
 * This interface extends the functional iterable interface to model infinite iterables.
 */
public interface InfiniteIterable<E> extends FunctionalIterable<E> {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Returns a new infinite iterable that repeats the given object infinitely.
     */
    @Pure
    public static <E> InfiniteIterable<E> repeat(E object) {
        return () -> RepeatingIterator.with(object);
    }
    
    /**
     * Returns a new infinite iterable that generates an infinite number of elements with the given producer.
     */
    @Pure
    public static <E> InfiniteIterable<E> generate(Producer<? extends E> producer) {
        return () -> GeneratingIterator.with(producer);
    }
    
    /**
     * Returns a new infinite iterable that iterates over the sequence produced by the given operator from the given seed.
     */
    @Pure
    public static <E> InfiniteIterable<E> iterate(UnaryOperator<E> operator, E seed) {
        return () -> IteratingIterator.with(operator, seed);
    }
    
    /* -------------------------------------------------- Size -------------------------------------------------- */
    
    @Pure
    @Override
    public default boolean isEmpty() {
        return false;
    }
    
    @Pure
    @Override
    public default long size(long limit) {
        if (limit < 0) { throw new IndexOutOfBoundsException("The limit has to be non-negative but was " + limit + "."); }
        
        return limit;
    }
    
    /* -------------------------------------------------- Filtering -------------------------------------------------- */
    
    @Pure
    @Override
    public default InfiniteIterable<E> filter(Predicate<? super E> predicate) {
        return () -> FilteringIterator.with(iterator(), predicate);
    }
    
    @Pure
    @Override
    public default FunctionalIterable<E> filterNulls() {
        return filter(element -> element != null);
    }
    
    /* -------------------------------------------------- Mapping -------------------------------------------------- */
    
    @Pure
    @Override
    public default <F> InfiniteIterable<F> map(UnaryFunction<? super E, ? extends F> function) {
        return () -> MappingIterator.with(iterator(), function);
    }
    
    /* -------------------------------------------------- Pruning -------------------------------------------------- */
    
    @Pure
    @Override
    public default InfiniteIterable<E> skip(long number) {
        return () -> PruningIterator.with(iterator(), number, Long.MAX_VALUE);
    }
    
    /* -------------------------------------------------- Zipping -------------------------------------------------- */
    
    @Pure
    @Override
    public default <F> InfiniteIterable<Pair<E, F>> zipShortest(InfiniteIterable<? extends F> iterable) {
        return () -> ZippingIterator.with(iterator(), iterable.iterator(), true);
    }
    
    @Pure
    @Override
    public default <F> InfiniteIterable<Pair<E, F>> zipLongest(FiniteIterable<? extends F> iterable) {
        return () -> ZippingIterator.with(iterator(), iterable.iterator(), false);
    }
    
    /* -------------------------------------------------- Flattening -------------------------------------------------- */
    
    @Pure
    @Override
    public default <F> InfiniteIterable<F> flatten(long level) {
        return () -> FlatteningIterator.with(iterator(), level);
    }
    
    @Pure
    @Override
    public default <F> InfiniteIterable<F> flattenOne() {
        return flatten(1);
    }
    
    @Pure
    @Override
    public default <F> InfiniteIterable<F> flattenAll() {
        return flatten(Long.MAX_VALUE);
    }
    
}
