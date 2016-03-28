package net.digitalid.utility.functional.iterables;

import net.digitalid.utility.functional.interfaces.Predicate;
import net.digitalid.utility.functional.interfaces.Producer;
import net.digitalid.utility.functional.interfaces.UnaryFunction;
import net.digitalid.utility.functional.interfaces.UnaryOperator;
import net.digitalid.utility.tuples.Pair;
import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This interface extends the functional iterable interface to model infinite iterables.
 */
public interface InfiniteIterable<E> extends FunctionalIterable<E> {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    @Pure
    public static <E> InfiniteIterable<E> repeat(E object) {
        return () -> new ObjectIterator(object);
    }
    
    @Pure
    public static <E> InfiniteIterable<E> generate(Producer<? extends E> producer) {
        return () -> new ProducerIterator(producer);
    }
    
    @Pure
    public static <E> InfiniteIterable<E> iterate(UnaryOperator<E> operator, E seed) {
        return () -> new OperatorIterator(operator, seed);
    }
    
    /* -------------------------------------------------- Size -------------------------------------------------- */
    
    @Pure
    @Override
    public default boolean isEmpty() {
        return false;
    }
    
    @Pure
    @Override
    public default int size(int limit) {
        if (limit < 0) { throw new IndexOutOfBoundsException("The limit has to be non-negative but was " + limit + "."); }
        
        return limit;
    }
    
    /* -------------------------------------------------- Filter -------------------------------------------------- */
    
    @Pure
    @Override
    public default InfiniteIterable<E> filter(Predicate<? super E> predicate) {
        return () -> new FilterIterator(iterator(), predicate);
    }
    
    /* -------------------------------------------------- Mapping -------------------------------------------------- */
    
    @Pure
    @Override
    public default <F> InfiniteIterable<E> map(UnaryFunction<? super E, ? extends F> function) {
        return () -> new MappingIterator(iterator(), function);
    }
    
    /* -------------------------------------------------- Pruning -------------------------------------------------- */
    
    @Pure
    @Override
    public default InfiniteIterable<E> skip(int number) {
        return () -> new SequenceIterator(iterator(), number, Integer.MAX_VALUE);
    }
    
    /* -------------------------------------------------- Zipping -------------------------------------------------- */
    
    @Pure
    @Override
    public default <F> InfiniteIterable<Pair<E, F>> zipShortest(InfiniteIterable<F> iterable) {
        return () -> new ZipShortestIterator(iterator(), iterable.iterator());
    }
    
    @Pure
    @Override
    public default <F> InfiniteIterable<Pair<E, F>> zipLongest(FiniteIterable<F> iterable) {
        return () -> new ZipLongestIterator(iterator(), iterable.iterator());
    }
    
    /* -------------------------------------------------- Flattening -------------------------------------------------- */
    
    @Pure
    @Override
    public default <F> InfiniteIterable<F> flatten(int level) {
        return () -> new FlattenIterator(iterator(), level);
    }
    
    @Pure
    @Override
    public default <F> InfiniteIterable<F> flattenOne() {
        return flatten(1);
    }
    
    @Pure
    @Override
    public default <F> InfiniteIterable<F> flattenAll() {
        return flatten(Integer.MAX_VALUE);
    }
    
}
