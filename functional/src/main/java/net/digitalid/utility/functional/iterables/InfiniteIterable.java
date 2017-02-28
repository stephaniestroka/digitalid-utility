package net.digitalid.utility.functional.iterables;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.generics.Unspecifiable;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.functional.failable.FailablePredicate;
import net.digitalid.utility.functional.failable.FailableProducer;
import net.digitalid.utility.functional.failable.FailableUnaryFunction;
import net.digitalid.utility.functional.failable.FailableUnaryOperator;
import net.digitalid.utility.functional.interfaces.Producer;
import net.digitalid.utility.functional.iterators.FilteringIterator;
import net.digitalid.utility.functional.iterators.FlatteningIterator;
import net.digitalid.utility.functional.iterators.GeneratingIterator;
import net.digitalid.utility.functional.iterators.IteratingIterator;
import net.digitalid.utility.functional.iterators.MappingIterator;
import net.digitalid.utility.functional.iterators.PruningIterator;
import net.digitalid.utility.functional.iterators.RepeatingIterator;
import net.digitalid.utility.functional.iterators.ZippingIterator;
import net.digitalid.utility.tuples.Pair;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.math.Positive;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.ReadOnly;

/**
 * This interface extends the functional iterable interface to model infinite iterables.
 */
@ReadOnly
@Functional
public interface InfiniteIterable<@Specifiable ELEMENT> extends FunctionalIterable<ELEMENT> {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Returns a new infinite iterable that repeats the given element infinitely.
     */
    @Pure
    public static <@Specifiable ELEMENT> @Nonnull InfiniteIterable<ELEMENT> repeat(@Captured ELEMENT element) {
        return () -> RepeatingIterator.with(element);
    }
    
    /**
     * Returns a new infinite iterable that iterates over the sequence produced by the given operator from the given first element.
     */
    @Pure
    public static <@Specifiable ELEMENT> @Nonnull InfiniteIterable<ELEMENT> iterate(@Captured ELEMENT firstElement, @Nonnull FailableUnaryOperator<ELEMENT, ?> unaryOperator) {
        return () -> IteratingIterator.with(firstElement, unaryOperator);
    }
    
    /**
     * Returns a new infinite iterable that generates an infinite number of elements with the producer produced by the given producer.
     * All producers produced by the given producer should produce the same sequence of elements, otherwise operations like
     * {@link #get(int)} are no longer side-effect free and calling them repeatedly leads to unexpected results.
     */
    @Pure
    public static <@Specifiable ELEMENT> @Nonnull InfiniteIterable<ELEMENT> generate(@Captured @Nonnull Producer<? extends FailableProducer<? extends ELEMENT, ?>> producer) {
        return () -> GeneratingIterator.with(producer.produce());
    }
    
    /* -------------------------------------------------- Size -------------------------------------------------- */
    
    @Pure
    @Override
    public default boolean isEmpty() {
        return false;
    }
    
    @Pure
    @Override
    public default @NonNegative int size(@Positive int limit) {
        if (limit <= 0) { throw new IndexOutOfBoundsException("The limit has to be positive but was " + limit + "."); }
        
        return limit;
    }
    
    /* -------------------------------------------------- Filtering -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull InfiniteIterable<ELEMENT> filter(@Nonnull FailablePredicate<? super ELEMENT, ?> predicate) {
        return () -> FilteringIterator.with(iterator(), predicate);
    }
    
    @Pure
    @Override
    public default @Nonnull InfiniteIterable<ELEMENT> filterNot(@Nonnull FailablePredicate<? super ELEMENT, ?> predicate) {
        return filter(predicate.negate());
    }
    
    @Pure
    @Override
    public default @Nonnull InfiniteIterable<ELEMENT> filterNulls() {
        return filter(element -> element != null);
    }
    
    /* -------------------------------------------------- Mapping -------------------------------------------------- */
    
    @Pure
    @Override
    public default <@Specifiable TYPE> @Nonnull InfiniteIterable<TYPE> map(@Nonnull FailableUnaryFunction<? super ELEMENT, ? extends TYPE, ?> function) {
        return () -> MappingIterator.with(iterator(), function);
    }
    
    /* -------------------------------------------------- Instance -------------------------------------------------- */
    
    @Pure
    @Override
    public default <@Specifiable TYPE> @Nonnull InfiniteIterable<TYPE> instanceOf(@Nonnull Class<TYPE> type) {
        return filter(type::isInstance).map(type::cast);
    }
    
    /* -------------------------------------------------- Pruning -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull InfiniteIterable<ELEMENT> skip(@Positive int number) {
        return () -> PruningIterator.with(iterator(), number, Integer.MAX_VALUE);
    }
    
    /* -------------------------------------------------- Zipping -------------------------------------------------- */
    
    @Pure
    @Override
    public default <@Specifiable TYPE> @Nonnull InfiniteIterable<@Nonnull Pair<ELEMENT, TYPE>> zipShortest(@Nonnull InfiniteIterable<? extends TYPE> iterable) {
        return () -> ZippingIterator.with(iterator(), iterable.iterator(), true);
    }
    
    @Pure
    @Override
    public default <@Unspecifiable TYPE> @Nonnull InfiniteIterable<@Nonnull Pair<ELEMENT, @Nullable TYPE>> zipLongest(@Nonnull FiniteIterable<? extends TYPE> iterable) {
        return () -> ZippingIterator.with(iterator(), iterable.iterator(), false);
    }
    
    /* -------------------------------------------------- Flattening -------------------------------------------------- */
    
    @Pure
    @Override
    public default <@Specifiable TYPE> @Nonnull InfiniteIterable<TYPE> flatten(@Positive int level) {
        return () -> FlatteningIterator.with(iterator(), level);
    }
    
    @Pure
    @Override
    public default <@Specifiable TYPE> @Nonnull InfiniteIterable<TYPE> flattenOne() {
        return flatten(1);
    }
    
    @Pure
    @Override
    public default <@Specifiable TYPE> @Nonnull InfiniteIterable<TYPE> flattenAll() {
        return flatten(Integer.MAX_VALUE);
    }
    
}
