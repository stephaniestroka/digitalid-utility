package net.digitalid.utility.functional.iterables;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Referenced;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.annotations.state.Modifiable;
import net.digitalid.utility.fixes.Fixes;
import net.digitalid.utility.functional.interfaces.BinaryOperator;
import net.digitalid.utility.functional.interfaces.Collector;
import net.digitalid.utility.functional.interfaces.Consumer;
import net.digitalid.utility.functional.interfaces.Predicate;
import net.digitalid.utility.functional.interfaces.UnaryFunction;
import net.digitalid.utility.functional.iterators.CombiningIterator;
import net.digitalid.utility.functional.iterators.CyclingIterator;
import net.digitalid.utility.functional.iterators.FilteringIterator;
import net.digitalid.utility.functional.iterators.FlatteningIterator;
import net.digitalid.utility.functional.iterators.MappingIterator;
import net.digitalid.utility.functional.iterators.PruningIterator;
import net.digitalid.utility.functional.iterators.ReadOnlyArrayIterator;
import net.digitalid.utility.functional.iterators.ReadOnlyIterableIterator;
import net.digitalid.utility.functional.iterators.ReadOnlyIterator;
import net.digitalid.utility.functional.iterators.ReversingIterator;
import net.digitalid.utility.functional.iterators.ZippingIterator;
import net.digitalid.utility.interfaces.Countable;
import net.digitalid.utility.tuples.Pair;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.math.Positive;
import net.digitalid.utility.validation.annotations.math.relative.GreaterThanOrEqualTo;
import net.digitalid.utility.validation.annotations.method.Chainable;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.ReadOnly;

/**
 * This interface extends the functional iterable interface to model finite iterables.
 * 
 * @see CollectionIterable
 */
@ReadOnly
@Functional
public interface FiniteIterable<E> extends FunctionalIterable<E>, Countable {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Wraps the given collection as a finite iterable.
     */
    @Pure
    public static <E> @Nonnull FiniteIterable<E> of(@Referenced @Unmodified @Nonnull Collection<? extends E> collection) {
        return new CollectionBasedIterable<>(collection);
    }
    
    /**
     * Wraps the given elements as a finite iterable.
     */
    @Pure
    @SafeVarargs
    public static <E> @Nonnull FiniteIterable<E> of(@Referenced @Unmodified @Captured E... elements) {
        return () -> ReadOnlyArrayIterator.with(elements);
    }
    
    /* -------------------------------------------------- Filtering -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull FiniteIterable<E> filter(@Nonnull Predicate<? super E> predicate) {
        return () -> FilteringIterator.with(iterator(), predicate);
    }
    
    @Pure
    @Override
    public default @Nonnull FunctionalIterable<E> filterNulls() {
        return filter(element -> element != null);
    }
    
    /* -------------------------------------------------- Mapping -------------------------------------------------- */
    
    @Pure
    @Override
    public default <F> @Nonnull FiniteIterable<F> map(@Nonnull UnaryFunction<? super E, ? extends F> function) {
        return () -> MappingIterator.with(iterator(), function);
    }
    
    /* -------------------------------------------------- Instance -------------------------------------------------- */
    
    @Pure
    @Override
    public default <T> @Nonnull FiniteIterable<T> instanceOf(@Nonnull Class<T> type) {
        return filter(type::isInstance).map(type::cast);
    }
    
    /* -------------------------------------------------- Pruning -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull FiniteIterable<E> skip(@Positive int number) {
        return () -> PruningIterator.with(iterator(), number, Integer.MAX_VALUE);
    }
    
    /* -------------------------------------------------- Zipping -------------------------------------------------- */
    
    @Pure
    @Override
    public default <F> @Nonnull FiniteIterable<Pair<E, F>> zipShortest(@Nonnull InfiniteIterable<? extends F> iterable) {
        return () -> ZippingIterator.with(iterator(), iterable.iterator(), true);
    }
    
    @Pure
    @Override
    public default <F> @Nonnull FiniteIterable<Pair<E, F>> zipLongest(@Nonnull FiniteIterable<? extends F> iterable) {
        return () -> ZippingIterator.with(iterator(), iterable.iterator(), false);
    }
    
    /* -------------------------------------------------- Flattening -------------------------------------------------- */
    
    @Pure
    @Override
    public default <F> @Nonnull FiniteIterable<F> flatten(@Positive int level) {
        return () -> FlatteningIterator.with(iterator(), level);
    }
    
    @Pure
    @Override
    public default <F> @Nonnull FiniteIterable<F> flattenOne() {
        return flatten(1);
    }
    
    @Pure
    @Override
    public default <F> @Nonnull FiniteIterable<F> flattenAll() {
        return flatten(Integer.MAX_VALUE);
    }
    
    /* -------------------------------------------------- Equality -------------------------------------------------- */
    
    /**
     * Returns whether this iterable equals the given iterable.
     */
    @Pure
    public default boolean equals(@Nullable FiniteIterable<?> iterable) {
        if (iterable == null) { return false; }
        if (iterable == this) { return true; }
        final @Nonnull ReadOnlyIterator<E> thisIterator = iterator();
        final @Nonnull ReadOnlyIterator<?> thatIterator = iterable.iterator();
        while (thisIterator.hasNext() && thatIterator.hasNext()) {
            if (!Objects.equals(thisIterator.next(), thatIterator.next())) { return false; }
        }
        return !thisIterator.hasNext() && !thatIterator.hasNext();
    }
    
    /* -------------------------------------------------- Size -------------------------------------------------- */
    
    /**
     * Returns the size of this iterable.
     */
    @Pure
    @Override
    public default @NonNegative int size() {
        return size(Integer.MAX_VALUE);
    }
    
    /* -------------------------------------------------- Element -------------------------------------------------- */
    
    /**
     * Returns the first element of this iterable or the given default element if this iterable is empty.
     */
    @Pure
    public default @NonCapturable E getFirst(@NonCaptured @Unmodified E defaultElement) {
        final @Nonnull Iterator<E> iterator = iterator();
        return iterator.hasNext() ? iterator.next() : defaultElement;
    }
    
    /**
     * Returns the first element of this iterable or null if this iterable is empty.
     */
    @Pure
    public default @NonCapturable @Nullable E getFirstOrNull() {
        return getFirst(null);
    }
    
    /**
     * Returns the first element of this iterable.
     * 
     * @throws NoSuchElementException if this iterable is empty.
     */
    @Pure
    public default @NonCapturable E getFirst() {
        if (isEmpty()) { throw new NoSuchElementException(); }
        else { return getFirstOrNull(); }
    }
    
    /**
     * Returns the last element of this iterable or the given default element if this iterable is empty.
     */
    @Pure
    public default @NonCapturable E getLast(@NonCaptured @Unmodified E defaultElement) {
        E result = defaultElement;
        for (@Nullable E element : this) { result = element; }
        return result;
    }
    
    /**
     * Returns the last element of this iterable or null if this iterable is empty.
     */
    @Pure
    public default @NonCapturable @Nullable E getLastOrNull() {
        return getLast(null);
    }
    
    /**
     * Returns the last element of this iterable.
     * 
     * @throws NoSuchElementException if this iterable is empty.
     */
    @Pure
    public default @NonCapturable E getLast() {
        if (isEmpty()) { throw new NoSuchElementException(); }
        else { return getLastOrNull(); }
    }
    
    /* -------------------------------------------------- Index -------------------------------------------------- */
    
    /**
     * Returns the index of the first occurrence of the given object in this iterable or -1 if this iterable does not contain the given object.
     */
    @Pure
    public default @GreaterThanOrEqualTo(-1) int indexOf(@NonCaptured @Unmodified @Nullable Object object) {
        int index = 0;
        for (@Nullable E element : this) {
            if (Objects.equals(object, element)) { return index; }
            index += 1;
        }
        return -1;
    }
    
    /**
     * Returns the index of the last occurrence of the given object in this iterable or -1 if this iterable does not contain the given object.
     */
    @Pure
    public default @GreaterThanOrEqualTo(-1) int lastIndexOf(@NonCaptured @Unmodified @Nullable Object object) {
        int lastIndex = -1;
        int currentIndex = 0;
        for (@Nullable E element : this) {
            if (Objects.equals(object, element)) { lastIndex = currentIndex; }
            currentIndex += 1;
        }
        return lastIndex;
    }
    
    /* -------------------------------------------------- Counting -------------------------------------------------- */
    
    /**
     * Returns the number of elements in this iterable that equal the given object.
     */
    @Pure
    public default @NonNegative int count(@NonCaptured @Unmodified @Nullable Object object) {
        int count = 0;
        for (@Nullable E element : this) {
            if (Objects.equals(object, element)) { count++; }
        }
        return count;
    }
    
    /* -------------------------------------------------- Containing -------------------------------------------------- */
    
    /**
     * Returns whether this iterable contains the given object.
     */
    @Pure
    public default boolean contains(@NonCaptured @Unmodified @Nullable Object object) {
        for (@Nullable E element : this) {
            if (Objects.equals(object, element)) { return true; }
        }
        return false;
    }
    
    /**
     * Returns whether this iterable contains all of the elements of the given iterable.
     */
    @Pure
    public default boolean containsAll(@Nonnull FiniteIterable<?> iterable) {
        for (@Nullable Object element : iterable) {
            if (!contains(element)) { return false; }
        }
        return true;
    }
    
    /**
     * Returns whether this iterable contains all of the elements of the given collection.
     */
    @Pure
    public default boolean containsAll(@NonCaptured @Unmodified @Nonnull Collection<?> collection) {
        for (@Nullable Object element : collection) {
            if (!contains(element)) { return false; }
        }
        return true;
    }
    
    /**
     * Returns whether this iterable contains null.
     */
    @Pure
    public default boolean containsNull() {
        for (@Nullable E element : this) {
            if (element == null) { return true; }
        }
        return false;
    }
    
    /**
     * Returns whether this iterable contains duplicates.
     */
    @Pure
    public default boolean containsDuplicates() {
        final @Nonnull HashSet<E> set = new HashSet<>(size());
        for (@Nullable E element : this) {
            if (set.contains(element)) { return true; }
            else { set.add(element); }
        }
        return false;
    }
    
    /* -------------------------------------------------- Distinct -------------------------------------------------- */
    
    /**
     * Returns the distinct elements of this iterable.
     */
    @Pure
    public default @Nonnull FiniteIterable<E> distinct() {
        return () -> ReadOnlyIterableIterator.with(toSet().iterator());
    }
    
    /* -------------------------------------------------- Visiting -------------------------------------------------- */
    
    /**
     * Performs the given action for each element of this iterable and returns this iterable.
     */
    @Impure
    @Chainable
    public default FiniteIterable<E> forEach(@NonCaptured @Modified @Nonnull Consumer<? super E> action) {
        for (E element : this) { action.consume(element); }
        return this;
    }
    
    /* -------------------------------------------------- Intersecting -------------------------------------------------- */
    
    /**
     * Returns the elements that are contained both in this iterable and the given iterable.
     */
    @Pure
    public default @Nonnull FiniteIterable<E> intersect(@Nonnull FiniteIterable<? super E> iterable) {
        return filter(element -> iterable.contains(element));
    }
    
    /* -------------------------------------------------- Excluding -------------------------------------------------- */
    
    /**
     * Returns the elements that are contained in this iterable but not in the given iterable.
     */
    @Pure
    public default @Nonnull FiniteIterable<E> exclude(@Nonnull FiniteIterable<? super E> iterable) {
        return filter(element -> !iterable.contains(element));
    }
    
    /* -------------------------------------------------- Combining -------------------------------------------------- */
    
    /**
     * Returns the elements of this iterable followed by the elements of the given iterable.
     */
    @Pure
    public default @Nonnull FiniteIterable<E> combine(@Nonnull FiniteIterable<? extends E> iterable) {
        return () -> CombiningIterator.with(iterator(), iterable.iterator());
    }
    
    /**
     * Returns the elements of this iterable followed by the elements of the given iterable.
     */
    @Pure
    public default @Nonnull InfiniteIterable<E> combine(@Nonnull InfiniteIterable<? extends E> iterable) {
        return () -> CombiningIterator.with(iterator(), iterable.iterator());
    }
    
    /* -------------------------------------------------- Cycling -------------------------------------------------- */
    
    /**
     * Returns the elements of this iterable repeated indefinitely.
     */
    @Pure
    public default @Nonnull InfiniteIterable<E> repeated() {
        return () -> CyclingIterator.with(this);
    }
    
    /* -------------------------------------------------- Finding -------------------------------------------------- */
    
    /**
     * Returns the first element of this iterable that fulfills the given predicate or the given default element if no such element is found.
     */
    @Pure
    public default @NonCapturable @Nullable E findFirst(@Nonnull Predicate<? super E> predicate, @NonCaptured @Unmodified E defaultElement) {
        for (E element : this) {
            if (predicate.evaluate(element)) { return element; }
        }
        return defaultElement;
    }
    
    /**
     * Returns the first element of this iterable that fulfills the given predicate or null if no such element is found.
     */
    @Pure
    public default @NonCapturable @Nullable E findFirst(@Nonnull Predicate<? super E> predicate) {
        return findFirst(predicate, null);
    }
    
    /**
     * Returns the last element of this iterable that fulfills the given predicate or the given default element if no such element is found.
     */
    @Pure
    public default @NonCapturable E findLast(@Nonnull Predicate<? super E> predicate, @NonCaptured @Unmodified E defaultElement) {
        @Nullable E lastElement = defaultElement;
        for (E element : this) {
            if (predicate.evaluate(element)) { lastElement = element; }
        }
        return lastElement;
    }
    
    /**
     * Returns the last element of this iterable that fulfills the given predicate or null if no such element is found.
     */
    @Pure
    public default @NonCapturable @Nullable E findLast(@Nonnull Predicate<? super E> predicate) {
        return findLast(predicate, null);
    }
    
    /**
     * Returns the unique element of this iterable that fulfills the given predicate.
     * 
     * @throws NoSuchElementException if no unique element is found in this iterable.
     */
    @Pure
    public default @NonCapturable E findUnique(@Nonnull Predicate<? super E> predicate) {
        @Nullable E uniqueElement = null;
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
    public default boolean matchAny(@Nonnull Predicate<? super E> predicate) {
        for (E element : this) {
            if (predicate.evaluate(element)) { return true; }
        }
        return false;
    }
    
    /**
     * Returns whether all elements of this iterable match the given predicate.
     */
    @Pure
    public default boolean matchAll(@Nonnull Predicate<? super E> predicate) {
        for (E element : this) {
            if (!predicate.evaluate(element)) { return false; }
        }
        return true;
    }
    
    /**
     * Returns whether no element of this iterable matches the given predicate.
     */
    @Pure
    public default boolean matchNone(@Nonnull Predicate<? super E> predicate) {
        return !matchAny(predicate);
    }
    
    /* -------------------------------------------------- Reducing -------------------------------------------------- */
    
    /**
     * Returns the value reduced by the given operator or the given element if this iterable is empty.
     */
    @Pure
    public default @NonCapturable E reduce(@Nonnull BinaryOperator<E> operator, @NonCaptured @Unmodified E element) {
        final @Nonnull Iterator<E> iterator = iterator();
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
    public default @NonCapturable @Nullable E reduce(@Nonnull BinaryOperator<E> operator) {
        return reduce(operator, null);
    }
    
    /* -------------------------------------------------- Collecting -------------------------------------------------- */
    
    /**
     * Returns the result of the given collector after consuming all elements of this iterable.
     */
    @Pure
    public default <R> @Capturable R collect(@NonCaptured @Modified @Nonnull Collector<? super E, ? extends R> collector) {
        for (E element : this) { collector.consume(element); }
        return collector.getResult();
    }
    
    /* -------------------------------------------------- Ordering -------------------------------------------------- */
    
    /**
     * Returns whether the elements in this iterable are ordered (excluding null values).
     * 
     * @param strictly whether the ordering is strict (i.e. without equal values).
     * @param ascending whether the ordering is ascending (true) or descending (false).
     * 
     * @return {@code true} if the elements in this iterable are ordered, {@code false} otherwise.
     */
    @Pure
    @SuppressWarnings("unchecked")
    public default boolean isOrdered(boolean strictly, boolean ascending) {
        @Nullable E lastElement = null;
        for (@Nullable E element : this) {
            if (element != null) {
                if (lastElement != null) {
                    if (element instanceof Comparable<?>) {
                        if (((Comparable<? super E>) element).compareTo(lastElement) * (ascending ? 1 : -1) < (strictly ? 1 : 0)) { return false; }
                    }
                }
                lastElement = element;
            }
        }
        return true;
    }
    
    /**
     * Returns whether the elements in this iterable are ascending (excluding null values).
     */
    @Pure
    public default boolean isAscending() {
        return isOrdered(false, true);
    }
    
    /**
     * Returns whether the elements in this iterable are strictly ascending (excluding null values).
     */
    @Pure
    public default boolean isStrictlyAscending() {
        return isOrdered(true, true);
    }
    
    /**
     * Returns whether the elements in this iterable are descending (excluding null values).
     */
    @Pure
    public default boolean isDescending() {
        return isOrdered(false, false);
    }
    
    /**
     * Returns whether the elements in this iterable are strictly descending (excluding null values).
     */
    @Pure
    public default boolean isStrictlyDescending() {
        return isOrdered(true, false);
    }
    
    /* -------------------------------------------------- Sorting -------------------------------------------------- */
    
    /**
     * Returns the elements of this iterable sorted according to the given comparator.
     */
    @Pure
    public default @Nonnull FiniteIterable<E> sorted(@Nonnull Comparator<? super E> comparator) {
        return () -> {
            final @Nonnull List<E> list = toList();
            Collections.sort(list, comparator);
            return ReadOnlyIterableIterator.with(list.iterator());
        };
    }
    
    /**
     * Returns the elements of this iterable sorted according to their natural order.
     * 
     * @throws ClassCastException if the elements of this iterable are not comparable.
     */
    @Pure
    @SuppressWarnings("unchecked")
    public default @Nonnull FiniteIterable<E> sorted() {
        return sorted((a, b) -> a == null ? 1 : (b == null ? -1 : ( ((Comparable<? super E>) a).compareTo(b) )));
    }
    
    /* -------------------------------------------------- Reversing -------------------------------------------------- */
    
    /**
     * Returns the elements of this iterable in reversed order.
     */
    @Pure
    public default @Nonnull FiniteIterable<E> reversed() {
        return () -> ReversingIterator.with(toGenericArray());
    }
    
    /* -------------------------------------------------- Minimum -------------------------------------------------- */
    
    /**
     * Returns the minimum element of this iterable according to the given comparator or the given default element if this iterable is empty.
     */
    @Pure
    public default @NonCapturable E min(@Nonnull Comparator<? super E> comparator, @NonCaptured @Unmodified E defaultElement) {
        return reduce(BinaryOperator.min(comparator), defaultElement);
    }
    
    /**
     * Returns the minimum element of this iterable according to the given comparator or null if this iterable is empty.
     */
    @Pure
    public default @NonCapturable @Nullable E min(@Nonnull Comparator<? super E> comparator) {
        return min(comparator, null);
    }
    
    /**
     * Returns the minimum element of this iterable according to the natural order or the given default element if this iterable is empty.
     * 
     * @throws ClassCastException if the elements of this iterable are not comparable.
     */
    @Pure
    @SuppressWarnings("unchecked")
    public default @NonCapturable E min(@NonCaptured @Unmodified E defaultElement) {
        return reduce((a, b) -> a == null ? b : (b == null ? a : ( ((Comparable<? super E>) a).compareTo(b) <= 0 ? a : b )), defaultElement);
    }
    
    /**
     * Returns the minimum element of this iterable according to the natural order or null if this iterable is empty.
     * 
     * @throws ClassCastException if the elements of this iterable are not comparable.
     */
    @Pure
    public default @NonCapturable @Nullable E min() {
        return min((E) null);
    }
    
    /* -------------------------------------------------- Maximum -------------------------------------------------- */
    
    /**
     * Returns the maximum element of this iterable according to the given comparator or the given default element if this iterable is empty.
     */
    @Pure
    public default @NonCapturable E max(@Nonnull Comparator<? super E> comparator, @NonCaptured @Unmodified E defaultElement) {
        return reduce(BinaryOperator.max(comparator), defaultElement);
    }
    
    /**
     * Returns the maximum element of this iterable according to the given comparator or null if this iterable is empty.
     */
    @Pure
    public default @NonCapturable @Nullable E max(@Nonnull Comparator<? super E> comparator) {
        return max(comparator, null);
    }
    
    /**
     * Returns the maximum element of this iterable according to the natural order or the given default element if this iterable is empty.
     * 
     * @throws ClassCastException if the elements of this iterable are not comparable.
     */
    @Pure
    @SuppressWarnings("unchecked")
    public default @NonCapturable E max(@NonCaptured @Unmodified E defaultElement) {
        return reduce((a, b) -> a == null ? b : (b == null ? a : ( ((Comparable<? super E>) a).compareTo(b) >= 0 ? a : b )), defaultElement);
    }
    
    /**
     * Returns the maximum element of this iterable according to the natural order or null if this iterable is empty.
     * 
     * @throws ClassCastException if the elements of this iterable are not comparable.
     */
    @Pure
    public default @NonCapturable @Nullable E max() {
        return max((E) null);
    }
    
    /* -------------------------------------------------- Summation -------------------------------------------------- */
    
    /**
     * Returns the sum of all {@link Number numbers} in this iterable as a long.
     */
    @Pure
    public default long sumAsLong() {
        long sum = 0;
        for (E element : this) {
            if (element instanceof Number) {
                sum += ((Number) element).longValue();
            }
        }
        return sum;
    }
    
    /**
     * Returns the sum of all {@link Number numbers} in this iterable as a double.
     */
    @Pure
    public default double sumAsDouble() {
        double sum = 0;
        for (E element : this) {
            if (element instanceof Number) {
                sum += ((Number) element).doubleValue();
            }
        }
        return sum;
    }
    
    /* -------------------------------------------------- Average -------------------------------------------------- */
    
    /**
     * Returns the average of all {@link Number numbers} in this iterable.
     */
    @Pure
    public default double average() {
        double sum = 0;
        int counter = 0;
        for (E element : this) {
            if (element instanceof Number) {
                sum += ((Number) element).doubleValue();
                counter++;
            }
        }
        return sum / counter;
    }
    
    /* -------------------------------------------------- Joining -------------------------------------------------- */
    
    /**
     * Returns the elements of this iterable joined by the given delimiter with the given prefix and suffix or the given empty string if this iterable is empty.
     */
    @Pure
    public default @Nonnull String join(@Nonnull CharSequence prefix, @Nonnull CharSequence suffix, @Nonnull CharSequence empty, @Nonnull CharSequence delimiter) {
        if (isEmpty()) {
            return String.valueOf(empty);
        } else {
            final @Nonnull StringBuilder result = new StringBuilder(prefix);
            boolean first = true;
            for (E element : this) {
                if (first) { first = false; }
                else { result.append(delimiter); }
                result.append(String.valueOf(element));
            }
            return result.append(suffix).toString();
        }
    }
    
    /**
     * Returns the elements of this iterable joined by commas with the given prefix and suffix or the given empty string if this iterable is empty.
     */
    @Pure
    public default @Nonnull String join(@Nonnull CharSequence prefix, @Nonnull CharSequence suffix, @Nonnull CharSequence empty) {
        return join(prefix, suffix, empty, ", ");
    }
    
    /**
     * Returns the elements of this iterable joined by commas with the given prefix and suffix.
     */
    @Pure
    public default @Nonnull String join(@Nonnull CharSequence prefix, @Nonnull CharSequence suffix) {
        return join(prefix, suffix, prefix.toString() + suffix.toString());
    }
    
    /**
     * Returns the elements of this iterable joined by the given delimiter with the given fixes or the given empty string if this iterable is empty.
     */
    @Pure
    public default @Nonnull String join(@Nullable Fixes fixes, @Nonnull CharSequence empty, @Nonnull CharSequence delimiter) {
        if (fixes == null) { return join("", "", empty, delimiter); }
        else { return join(fixes.getPrefix(), fixes.getSuffix(), empty, delimiter); }
    }
    
    /**
     * Returns the elements of this iterable joined by commas with the given fixes or the given empty string if this iterable is empty.
     */
    @Pure
    public default @Nonnull String join(@Nullable Fixes fixes, @Nonnull CharSequence empty) {
        return join(fixes, empty, ", ");
    }
    
    /**
     * Returns the elements of this iterable joined by commas with the given fixes.
     */
    @Pure
    public default @Nonnull String join(@Nullable Fixes fixes) {
        return join(fixes, fixes != null ? fixes.getBoth() : "");
    }
    
    /**
     * Returns the elements of this iterable joined by the given delimiter.
     */
    @Pure
    public default @Nonnull String join(@Nonnull CharSequence delimiter) {
        return join((Fixes) null, "", delimiter);
    }
    
    /**
     * Returns the elements of this iterable joined by commas.
     */
    @Pure
    public default @Nonnull String join() {
        return join((Fixes) null);
    }
    
    /* -------------------------------------------------- Exports -------------------------------------------------- */
    
    /**
     * Returns the elements of this iterable as an array.
     */
    @Pure
    public default @Capturable @Nonnull Object[] toArray() {
        final @Nonnull Object[] array = new Object[size()];
        int index = 0;
        for (Object element : this) {
            array[index++] = element;
        }
        return array;
    }
    
    /**
     * Returns the elements of this iterable as a generic array.
     */
    @Pure
    @SuppressWarnings("unchecked")
    public default @Capturable @Nonnull E[] toGenericArray() {
        return (E[]) toArray();
    }
    
    /**
     * Returns the elements of this iterable as a generic array of the given type.
     * If this iterable fits in the given array, it is returned therein.
     * Otherwise, a new array is allocated with the runtime type
     * of the given array and the size of this iterable.
     * <p>
     * If this iterable fits in the given array with room to spare (i.e. the array
     * has more elements than this iterable), the element in the array immediately
     * following the end of the iterable is set to null.
     */
    @Pure
    @SuppressWarnings("unchecked")
    public default <T> @Capturable @Nonnull T[] toArray(@NonCaptured @Modified @Nonnull T[] array) {
        final int size = size();
        final @Nonnull T[] result = array.length >= size ? array : (T[]) Array.newInstance(array.getClass().getComponentType(), size);
        final @Nonnull Iterator<E> iterator = iterator();
        for (int i = 0; i < result.length; i++) {
            if (iterator.hasNext()) {
                result[i] = (T) iterator.next();
            } else {
                result[i] = null;
                break;
            }
        }
        return result;
    }
    
    /**
     * Returns the elements of this iterable as a list.
     */
    @Pure
    public default @Capturable @Modifiable @Nonnull List<E> toList() {
        final @Nonnull List<E> result = new LinkedList<>();
        for (E element : this) {
            result.add(element);
        }
        return result;
    }
    
    /**
     * Returns the elements of this iterable as a set.
     */
    @Pure
    public default @Capturable @Modifiable @Nonnull Set<E> toSet() {
        final @Nonnull Set<E> result = new LinkedHashSet<>();
        for (E element : this) {
            result.add(element);
        }
        return result;
    }
    
    /**
     * Returns the elements of this iterable as a map with their key determined by the given function.
     * Elements that are mapped to the same key overwrite each other. If this is not desired, use
     * {@link #groupBy(net.digitalid.utility.functional.interfaces.UnaryFunction)} instead.
     */
    @Pure
    public default <K> @Capturable @Modifiable @Nonnull Map<K, E> toMap(@Nonnull UnaryFunction<? super E, ? extends K> function) {
        final @Nonnull Map<K, E> result = new LinkedHashMap<>();
        for (E element : this) {
            result.put(function.evaluate(element), element);
        }
        return result;
    }
    
    /* -------------------------------------------------- Grouping -------------------------------------------------- */
    
    /**
     * Returns the elements of this iterable as a map grouped by the given function.
     */
    @Pure
    public default <K> @Capturable @Modifiable @Nonnull Map<K, @Nonnull List<E>> groupBy(@Nonnull UnaryFunction<? super E, ? extends K> function) {
        final @Nonnull Map<K, List<E>> result = new LinkedHashMap<>(size());
        for (E element : this) {
            final K key = function.evaluate(element);
            @Nullable List<E> list = result.get(key);
            if (list == null) {
                list = new LinkedList<>();
                result.put(key, list);
            }
            list.add(element);
        }
        return result;
    }
    
}
