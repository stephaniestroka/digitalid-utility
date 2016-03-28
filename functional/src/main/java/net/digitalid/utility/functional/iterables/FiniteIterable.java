package net.digitalid.utility.functional.iterables;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import net.digitalid.utility.functional.interfaces.BinaryOperator;
import net.digitalid.utility.functional.interfaces.Consumer;
import net.digitalid.utility.functional.interfaces.Predicate;
import net.digitalid.utility.functional.interfaces.UnaryFunction;
import net.digitalid.utility.functional.iterators.CombiningIterator;
import net.digitalid.utility.functional.iterators.FilteringIterator;
import net.digitalid.utility.functional.iterators.FlatteningIterator;
import net.digitalid.utility.functional.iterators.MappingIterator;
import net.digitalid.utility.functional.iterators.PruningIterator;
import net.digitalid.utility.functional.iterators.ReversingIterator;
import net.digitalid.utility.functional.iterators.ZippingIterator;
import net.digitalid.utility.tuples.Pair;
import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This interface extends the functional iterable interface to model finite iterables.
 * 
 * @see CollectionIterable
 */
public interface FiniteIterable<E> extends FunctionalIterable<E> {
    
    /* -------------------------------------------------- Filtering -------------------------------------------------- */
    
    @Pure
    @Override
    public default FiniteIterable<E> filter(Predicate<? super E> predicate) {
        return () -> FilteringIterator.with(iterator(), predicate);
    }
    
    /* -------------------------------------------------- Mapping -------------------------------------------------- */
    
    @Pure
    @Override
    public default <F> FiniteIterable<F> map(UnaryFunction<? super E, ? extends F> function) {
        return () -> MappingIterator.with(iterator(), function);
    }
    
    /* -------------------------------------------------- Pruning -------------------------------------------------- */
    
    @Pure
    @Override
    public default FiniteIterable<E> skip(long number) {
        return () -> PruningIterator.with(iterator(), number, Long.MAX_VALUE);
    }
    
    /* -------------------------------------------------- Zipping -------------------------------------------------- */
    
    @Pure
    @Override
    public default <F> FiniteIterable<Pair<E, F>> zipShortest(InfiniteIterable<? extends F> iterable) {
        return () -> ZippingIterator.with(iterator(), iterable.iterator(), true);
    }
    
    @Pure
    @Override
    public default <F> FiniteIterable<Pair<E, F>> zipLongest(FiniteIterable<? extends F> iterable) {
        return () -> ZippingIterator.with(iterator(), iterable.iterator(), false);
    }
    
    /* -------------------------------------------------- Flattening -------------------------------------------------- */
    
    @Pure
    @Override
    public default <F> FiniteIterable<F> flatten(long level) {
        return () -> FlatteningIterator.with(iterator(), level);
    }
    
    @Pure
    @Override
    public default <F> FiniteIterable<F> flattenOne() {
        return flatten(1);
    }
    
    @Pure
    @Override
    public default <F> FiniteIterable<F> flattenAll() {
        return flatten(Long.MAX_VALUE);
    }
    
    /* -------------------------------------------------- Size -------------------------------------------------- */
    
    /**
     * Returns the size of this iterable.
     */
    @Pure
    public default long size() {
        return size(Long.MAX_VALUE);
    }
    
    /* -------------------------------------------------- Index -------------------------------------------------- */
    
    /**
     * Returns the index of the first occurrence of the given object in this iterable or -1 if this iterable does not contain the given object.
     */
    @Pure
    public default long firstIndexOf(E object) {
        long index = 0;
        for (E element : this) {
            if (Objects.equals(object, element)) { return index; }
            index += 1;
        }
        return -1;
    }
    
    /**
     * Returns the index of the last occurrence of the given object in this iterable or -1 if this iterable does not contain the given object.
     */
    @Pure
    public default long lastIndexOf(E object) {
        long lastIndex = -1;
        long currentIndex = 0;
        for (E element : this) {
            if (Objects.equals(object, element)) { lastIndex = currentIndex; }
            currentIndex += 1;
        }
        return lastIndex;
    }
    
    /* -------------------------------------------------- Containing -------------------------------------------------- */
    
    /**
     * Returns whether this iterable contains the given object.
     */
    @Pure
    public default boolean contains(E object) {
        for (E element : this) {
            if (Objects.equals(object, element)) { return true; }
        }
        return false;
    }
    
    /**
     * Returns whether this iterable contains all of the elements of the given iterable.
     */
    @Pure
    public default boolean containsAll(FiniteIterable<? extends E> iterable) {
        for (E element : iterable) {
            if (!contains(element)) { return false; }
        }
        return true;
    }
    
    /* -------------------------------------------------- Intersecting -------------------------------------------------- */
    
    /**
     * Returns the elements that are contained both in this iterable and the given iterable.
     */
    @Pure
    public default FiniteIterable<E> intersect(FiniteIterable<? super E> iterable) {
        return filter(element -> iterable.contains(element));
    }
    
    /* -------------------------------------------------- Excluding -------------------------------------------------- */
    
    /**
     * Returns the elements that are contained in this iterable but not in the given iterable.
     */
    @Pure
    public default FiniteIterable<E> exclude(FiniteIterable<? super E> iterable) {
        return filter(element -> !iterable.contains(element));
    }
    
    /* -------------------------------------------------- Combining -------------------------------------------------- */
    
    /**
     * Returns the elements of this iterable followed by the elements of the given iterable.
     */
    @Pure
    public default FiniteIterable<E> combine(FiniteIterable<? extends E> iterable) {
        return () -> CombiningIterator.with(iterator(), iterable.iterator());
    }
    
    /**
     * Returns the elements of this iterable followed by the elements of the given iterable.
     */
    @Pure
    public default InfiniteIterable<E> combine(InfiniteIterable<? extends E> iterable) {
        return () -> CombiningIterator.with(iterator(), iterable.iterator());
    }
    
    /* -------------------------------------------------- Finding -------------------------------------------------- */
    
    /**
     * Returns the first element of this iterable that fulfills the given predicate or null if no such element is found.
     */
    @Pure
    public default E findFirst(Predicate<? super E> predicate) {
        for (E element : this) {
            if (predicate.evaluate(element)) { return element; }
        }
        return null;
    }
    
    /**
     * Returns the last element of this iterable that fulfills the given predicate or null if no such element is found.
     */
    @Pure
    public default E findLast(Predicate<? super E> predicate) {
        E lastElement = null;
        for (E element : this) {
            if (predicate.evaluate(element)) { lastElement = element; }
        }
        return lastElement;
    }
    
    /**
     * Returns the unique element of this iterable that fulfills the given predicate.
     * 
     * @throws NoSuchElementException if no unique element is found in this iterable.
     */
    @Pure
    public default E findUnique(Predicate<? super E> predicate) {
        E uniqueElement = null;
        boolean found = false;
        for (E element : this) {
            if (predicate.evaluate(element)) {
                if (found) {
                    throw new NoSuchElementException("More than one elements fulfill the given predicate.");
                } else {
                    uniqueElement = element;
                    found = true;
                }
            }
        }
        if (found) { return uniqueElement; }
        else { throw new NoSuchElementException("No element fulfills the given predicate."); }
    }
    
    /* -------------------------------------------------- Matching -------------------------------------------------- */
    
    /**
     * Returns whether any elements of this iterable match the given predicate.
     */
    @Pure
    public default boolean matchAny(Predicate<? super E> predicate) {
        for (E element : this) {
            if (predicate.evaluate(element)) { return true; }
        }
        return false;
    }
    
    /**
     * Returns whether all elements of this iterable match the given predicate.
     */
    @Pure
    public default boolean matchAll(Predicate<? super E> predicate) {
        for (E element : this) {
            if (!predicate.evaluate(element)) { return false; }
        }
        return true;
    }
    
    /**
     * Returns whether no element of this iterable matches the given predicate.
     */
    @Pure
    public default boolean matchNone(Predicate<? super E> predicate) {
        return !matchAny(predicate);
    }
    
    /* -------------------------------------------------- Reducing -------------------------------------------------- */
    
    /**
     * Returns the value reduced by the given operator or the given element if this iterable is empty.
     */
    @Pure
    public default E reduce(BinaryOperator<E> operator, E element) {
        final Iterator<E> iterator = iterator();
        if (iterator.hasNext()) {
            E result = iterator.next();
            while (iterator.hasNext()) {
                result = operator.evaluate(result, iterator.next());
            }
            return result;
        } else {
            return element;
        }
    }
    
    /**
     * Returns the value reduced by the given operator or null if this iterable is empty.
     */
    @Pure
    public default E reduce(BinaryOperator<E> operator) {
        return reduce(operator, null);
    }
    
    /* -------------------------------------------------- Minimum -------------------------------------------------- */
    
    /**
     * Returns the minimum element of this iterable according to the given comparator or null if this iterable is empty.
     */
    @Pure
    public default E min(Comparator<? super E> comparator) {
        return reduce(BinaryOperator.minBy(comparator));
    }
    
    /**
     * Returns the minimum element of this iterable according to the natural order or null if this iterable is empty.
     * 
     * @throws ClassCastException if the elements of this iterable are not comparable.
     */
    @Pure
    @SuppressWarnings("unchecked")
    public default E min() {
        return reduce((a, b) -> a == null ? b : (b == null ? a : ( ((Comparable<? super E>) a).compareTo(b) <= 0 ? a : b )));
    }
    
    /* -------------------------------------------------- Maximum -------------------------------------------------- */
    
    /**
     * Returns the maximum element of this iterable according to the given comparator or null if this iterable is empty.
     */
    @Pure
    public default E max(Comparator<? super E> comparator) {
        return reduce(BinaryOperator.maxBy(comparator));
    }
    
    /**
     * Returns the maximum element of this iterable according to the natural order or null if this iterable is empty.
     * 
     * @throws ClassCastException if the elements of this iterable are not comparable.
     */
    @Pure
    @SuppressWarnings("unchecked")
    public default E max() {
        return reduce((a, b) -> a == null ? b : (b == null ? a : ( ((Comparable<? super E>) a).compareTo(b) >= 0 ? a : b )));
    }
    
    /* -------------------------------------------------- Action -------------------------------------------------- */
    
    /**
     * Performs the given action for each element of this iterable.
     */
    public default void forEach(Consumer<? super E> action) {
        for (E element : this) { action.consume(element); }
    }
    
    /* -------------------------------------------------- Array -------------------------------------------------- */
    
    /**
     * Returns the elements of this iterable as a capturable array.
     */
    @Pure
    @SuppressWarnings("unchecked")
    public default E[] toArray() {
        final Object[] array = new Object[(int) size()];
        int index = 0;
        for (E element : this) {
            array[index++] = element;
        }
        return (E[]) array;
    }
    
    /* -------------------------------------------------- Sorting -------------------------------------------------- */
    
    /**
     * Returns the elements of this iterable sorted according to the given comparator.
     */
    @Pure
    public default FiniteIterable<E> sorted(Comparator<? super E> comparator) {
        final List<E> list = Arrays.asList(toArray());
        Collections.sort(list, comparator);
        return CollectionIterable.of(list);
    }
    
    /**
     * Returns the elements of this iterable sorted according to their natural order.
     * 
     * @throws ClassCastException if the elements of this iterable are not comparable.
     */
    @Pure
    @SuppressWarnings("unchecked")
    public default FiniteIterable<E> sorted() {
        return sorted((a, b) -> a == null ? 1 : (b == null ? -1 : ( ((Comparable<? super E>) a).compareTo(b) )));
    }
    
    /* -------------------------------------------------- Reversing -------------------------------------------------- */
    
    /**
     * Returns the elements of this iterable in reversed order.
     */
    @Pure
    public default FiniteIterable<E> reversed() {
        return () -> ReversingIterator.with(toArray());
    }
    
    /* -------------------------------------------------- Distinct -------------------------------------------------- */
    
    /**
     * Returns the distinct elements of this iterable.
     */
    @Pure
    public default FiniteIterable<E> distinct() {
        return CollectionIterable.of(new LinkedHashSet<>(Arrays.asList(toArray())));
    }
    
}
