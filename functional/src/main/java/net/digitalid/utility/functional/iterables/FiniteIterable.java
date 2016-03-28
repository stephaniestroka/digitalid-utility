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
import net.digitalid.utility.tuples.Pair;
import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This interface extends the functional iterable interface to model finite iterables.
 * 
 * @see CollectionIterable
 */
public interface FiniteIterable<E> extends FunctionalIterable<E> {
    
    /* -------------------------------------------------- Filter -------------------------------------------------- */
    
    @Pure
    @Override
    public default FiniteIterable<E> filter(Predicate<? super E> predicate) {
        return () -> new FilterIterator(iterator(), predicate);
    }
    
    /* -------------------------------------------------- Mapping -------------------------------------------------- */
    
    @Pure
    @Override
    public default <F> FiniteIterable<E> map(UnaryFunction<? super E, ? extends F> function) {
        return () -> new MappingIterator(iterator(), function);
    }
    
    /* -------------------------------------------------- Pruning -------------------------------------------------- */
    
    @Pure
    @Override
    public default FiniteIterable<E> skip(int number) {
        return () -> new SequenceIterator(iterator(), number, Integer.MAX_VALUE);
    }
    
    /* -------------------------------------------------- Zipping -------------------------------------------------- */
    
    @Pure
    @Override
    public default <F> FiniteIterable<Pair<E, F>> zipShortest(InfiniteIterable<F> iterable) {
        return () -> new ZipShortestIterator(iterator(), iterable.iterator());
    }
    
    @Pure
    @Override
    public default <F> FiniteIterable<Pair<E, F>> zipLongest(FiniteIterable<F> iterable) {
        return () -> new ZipLongestIterator(iterator(), iterable.iterator());
    }
    
    /* -------------------------------------------------- Flattening -------------------------------------------------- */
    
    @Pure
    @Override
    public default <F> FiniteIterable<F> flatten(int level) {
        return () -> new FlattenIterator(iterator(), level);
    }
    
    @Pure
    @Override
    public default <F> FiniteIterable<F> flattenOne() {
        return flatten(1);
    }
    
    @Pure
    @Override
    public default <F> FiniteIterable<F> flattenAll() {
        return flatten(Integer.MAX_VALUE);
    }
    
    /* -------------------------------------------------- Size -------------------------------------------------- */
    
    /**
     * Returns the size of this iterable.
     */
    @Pure
    public default int size() {
        return size(Integer.MAX_VALUE);
    }
    
    /* -------------------------------------------------- Index -------------------------------------------------- */
    
    /**
     * Returns the index of the first occurrence of the given object in this iterable or -1 if this iterable does not contain the given object.
     */
    @Pure
    public default int firstIndexOf(E object) {
        int index = 0;
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
    public default int lastIndexOf(E object) {
        int lastIndex = -1;
        int currentIndex = 0;
        for (E element : this) {
            if (Objects.equals(object, element)) { lastIndex = currentIndex; }
            currentIndex += 1;
        }
        return lastIndex;
    }
    
    /* -------------------------------------------------- Contain -------------------------------------------------- */
    
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
    
    /* -------------------------------------------------- Intersect -------------------------------------------------- */
    
    /**
     * Returns the elements that are contained both in this iterable and the given iterable.
     */
    @Pure
    public default FiniteIterable<E> intersect(FiniteIterable<? super E> iterable) {
        return filter(element -> iterable.contains(element));
    }
    
    /* -------------------------------------------------- Exclude -------------------------------------------------- */
    
    /**
     * Returns the elements that are contained in this iterable but not in the given iterable.
     */
    @Pure
    public default FiniteIterable<E> exclude(FiniteIterable<? super E> iterable) {
        return filter(element -> !iterable.contains(element));
    }
    
    /* -------------------------------------------------- Combine -------------------------------------------------- */
    
    /**
     * Returns the elements of this iterable followed by the elements of the given iterable.
     */
    @Pure
    public default FiniteIterable<E> combine(FiniteIterable<E> iterable) {
        return () -> new UnionIterator(iterator(), iterable.iterator());
    }
    
    /**
     * Returns the elements of this iterable followed by the elements of the given iterable.
     */
    @Pure
    public default InfiniteIterable<E> combine(InfiniteIterable<E> iterable) {
        return () -> new UnionIterator(iterator(), iterable.iterator());
    }
    
    /* -------------------------------------------------- Find -------------------------------------------------- */
    
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
    
    /* -------------------------------------------------- Match -------------------------------------------------- */
    
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
    
    /* -------------------------------------------------- Action -------------------------------------------------- */
    
    /**
     * Performs the given action for each element of this iterable.
     */
    public default void forEach(Consumer<? super E> action) {
        for (E element : this) { action.consume(element); }
    }
    
    /* -------------------------------------------------- Reduction -------------------------------------------------- */
    
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
    
    /* -------------------------------------------------- Array -------------------------------------------------- */
    
    /**
     * Returns the elements of this iterable as a capturable array.
     */
    @Pure
    @SuppressWarnings("unchecked")
    public default E[] toArray() {
        final Object[] array = new Object[size()];
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
    
    /* -------------------------------------------------- Reverse -------------------------------------------------- */
    
    /**
     * Returns the elements of this iterable in reversed order.
     */
    @Pure
    public default FiniteIterable<E> reversed() {
        final List<E> list = Arrays.asList(toArray());
        Collections.reverse(list);
        return CollectionIterable.of(list);
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
