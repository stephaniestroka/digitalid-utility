/*
 * Copyright (C) 2017 Synacts GmbH, Switzerland (info@synacts.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.generics.Unspecifiable;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.ownership.Shared;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.annotations.state.Modifiable;
import net.digitalid.utility.circumfixes.Circumfix;
import net.digitalid.utility.functional.exceptions.IterationException;
import net.digitalid.utility.functional.failable.FailableBinaryOperator;
import net.digitalid.utility.functional.failable.FailableCollector;
import net.digitalid.utility.functional.failable.FailableConsumer;
import net.digitalid.utility.functional.failable.FailablePredicate;
import net.digitalid.utility.functional.failable.FailableUnaryFunction;
import net.digitalid.utility.functional.interfaces.BinaryOperator;
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
public interface FiniteIterable<@Specifiable ELEMENT> extends FunctionalIterable<ELEMENT>, Countable {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Wraps the given collection as a finite iterable or returns null if the collection is null.
     */
    @Pure
    public static <@Specifiable ELEMENT> FiniteIterable<ELEMENT> of(@Shared @Unmodified Collection<? extends ELEMENT> collection) {
        return collection == null ? null : new CollectionBasedIterable<>(collection);
    }
    
    /**
     * Wraps the given elements as a finite iterable.
     */
    @Pure
    @SafeVarargs
    public static <@Specifiable ELEMENT> @Nonnull FiniteIterable<ELEMENT> of(@Shared @Unmodified @Nonnull ELEMENT... elements) {
        return () -> ReadOnlyArrayIterator.with(elements);
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
    
    @Pure
    @Override
    public default boolean isEmpty() {
        return FunctionalIterable.super.isEmpty();
    }
    
    @Pure
    @Override
    public default boolean isSingle() {
        return FunctionalIterable.super.isSingle();
    }
    
    @Pure
    @Override
    public default boolean isEmptyOrSingle() {
        return FunctionalIterable.super.isEmptyOrSingle();
    }
    
    /* -------------------------------------------------- Filtering -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull FiniteIterable<ELEMENT> filter(@Nonnull FailablePredicate<? super ELEMENT, ?> predicate) {
        return () -> FilteringIterator.with(iterator(), predicate);
    }
    
    @Pure
    @Override
    public default @Nonnull FiniteIterable<ELEMENT> filterNot(@Nonnull FailablePredicate<? super ELEMENT, ?> predicate) {
        return filter(predicate.negate());
    }
    
    @Pure
    @Override
    public default @Nonnull FiniteIterable<ELEMENT> filterNulls() {
        return filter(element -> element != null);
    }
    
    /* -------------------------------------------------- Mapping -------------------------------------------------- */
    
    @Pure
    @Override
    public default <@Specifiable TYPE> @Nonnull FiniteIterable<TYPE> map(@Nonnull FailableUnaryFunction<? super ELEMENT, ? extends TYPE, ?> function) {
        return () -> MappingIterator.with(iterator(), function);
    }
    
    /* -------------------------------------------------- Instance -------------------------------------------------- */
    
    @Pure
    @Override
    public default <@Specifiable TYPE> @Nonnull FiniteIterable<TYPE> instanceOf(@Nonnull Class<TYPE> type) {
        return filter(type::isInstance).map(type::cast);
    }
    
    /* -------------------------------------------------- Pruning -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull FiniteIterable<ELEMENT> skip(@Positive int number) {
        return () -> PruningIterator.with(iterator(), number, Integer.MAX_VALUE);
    }
    
    /* -------------------------------------------------- Zipping -------------------------------------------------- */
    
    @Pure
    @Override
    public default <@Specifiable TYPE> @Nonnull FiniteIterable<@Nonnull Pair<ELEMENT, TYPE>> zipShortest(@Nonnull InfiniteIterable<? extends TYPE> iterable) {
        return () -> ZippingIterator.with(iterator(), iterable.iterator(), true);
    }
    
    @Pure
    @Override
    public default <@Unspecifiable TYPE> @Nonnull FiniteIterable<@Nonnull Pair<@Nullable ELEMENT, @Nullable TYPE>> zipLongest(@Nonnull FiniteIterable<? extends TYPE> iterable) {
        return () -> ZippingIterator.with(iterator(), iterable.iterator(), false);
    }
    
    /* -------------------------------------------------- Flattening -------------------------------------------------- */
    
    @Pure
    @Override
    public default <@Specifiable TYPE> @Nonnull FiniteIterable<TYPE> flatten(@Positive int level) {
        return () -> FlatteningIterator.with(iterator(), level);
    }
    
    @Pure
    @Override
    public default <@Specifiable TYPE> @Nonnull FiniteIterable<TYPE> flattenOne() {
        return flatten(1);
    }
    
    @Pure
    @Override
    public default <@Specifiable TYPE> @Nonnull FiniteIterable<TYPE> flattenAll() {
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
        final @Nonnull ReadOnlyIterator<ELEMENT> thisIterator = iterator();
        final @Nonnull ReadOnlyIterator<?> thatIterator = iterable.iterator();
        while (thisIterator.hasNext() && thatIterator.hasNext()) {
            if (!Objects.equals(thisIterator.next(), thatIterator.next())) { return false; }
        }
        return !thisIterator.hasNext() && !thatIterator.hasNext();
    }
    
    /* -------------------------------------------------- Element -------------------------------------------------- */
    
    /**
     * Returns the first element of this iterable or the given default element if this iterable is empty.
     */
    @Pure
    public default @NonCapturable ELEMENT getFirst(@NonCaptured @Unmodified ELEMENT defaultElement) {
        final @Nonnull Iterator<ELEMENT> iterator = iterator();
        return iterator.hasNext() ? iterator.next() : defaultElement;
    }
    
    /**
     * Returns the first element of this iterable or null if this iterable is empty.
     */
    @Pure
    public default @NonCapturable @Nullable ELEMENT getFirstOrNull() {
        return getFirst(null);
    }
    
    /**
     * Returns the first element of this iterable.
     * 
     * @throws NoSuchElementException if this iterable is empty.
     */
    @Pure
    public default @NonCapturable ELEMENT getFirst() {
        if (isEmpty()) { throw new NoSuchElementException(); }
        else { return getFirstOrNull(); }
    }
    
    /**
     * Returns the last element of this iterable or the given default element if this iterable is empty.
     */
    @Pure
    public default @NonCapturable ELEMENT getLast(@NonCaptured @Unmodified ELEMENT defaultElement) {
        ELEMENT result = defaultElement;
        for (@Nullable ELEMENT element : this) { result = element; }
        return result;
    }
    
    /**
     * Returns the last element of this iterable or null if this iterable is empty.
     */
    @Pure
    public default @NonCapturable @Nullable ELEMENT getLastOrNull() {
        return getLast(null);
    }
    
    /**
     * Returns the last element of this iterable.
     * 
     * @throws NoSuchElementException if this iterable is empty.
     */
    @Pure
    public default @NonCapturable ELEMENT getLast() {
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
        for (@Nullable ELEMENT element : this) {
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
        for (@Nullable ELEMENT element : this) {
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
        for (@Nullable ELEMENT element : this) {
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
        for (@Nullable ELEMENT element : this) {
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
        for (@Nullable ELEMENT element : this) {
            if (element == null) { return true; }
        }
        return false;
    }
    
    /**
     * Returns whether this iterable contains duplicates.
     */
    @Pure
    public default boolean containsDuplicates() {
        final @Nonnull HashSet<ELEMENT> set = new HashSet<>(size());
        for (@Nullable ELEMENT element : this) {
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
    public default @Nonnull FiniteIterable<ELEMENT> distinct() {
        return () -> ReadOnlyIterableIterator.with(toSet().iterator());
    }
    
    /* -------------------------------------------------- Visiting -------------------------------------------------- */
    
    /**
     * Performs the given action for each element of this iterable and returns this iterable.
     */
    @Pure
    @Chainable
    public default <@Unspecifiable EXCEPTION extends Exception> FiniteIterable<ELEMENT> doForEach(@NonCaptured @Modified @Nonnull FailableConsumer<? super ELEMENT, ? extends EXCEPTION> action) throws EXCEPTION {
        for (ELEMENT element : this) { action.consume(element); }
        return this;
    }
    
    /* -------------------------------------------------- Intersecting -------------------------------------------------- */
    
    /**
     * Returns the elements that are contained both in this iterable and the given iterable.
     */
    @Pure
    public default @Nonnull FiniteIterable<ELEMENT> intersect(@Nonnull FiniteIterable<? super ELEMENT> iterable) {
        return filter(element -> iterable.contains(element));
    }
    
    /* -------------------------------------------------- Excluding -------------------------------------------------- */
    
    /**
     * Returns the elements that are contained in this iterable but not in the given iterable.
     */
    @Pure
    public default @Nonnull FiniteIterable<ELEMENT> exclude(@Nonnull FiniteIterable<? super ELEMENT> iterable) {
        return filter(element -> !iterable.contains(element));
    }
    
    /* -------------------------------------------------- Combining -------------------------------------------------- */
    
    /**
     * Returns the elements of this iterable followed by the elements of the given iterable.
     */
    @Pure
    public default @Nonnull FiniteIterable<ELEMENT> combine(@Nonnull FiniteIterable<? extends ELEMENT> iterable) {
        return () -> CombiningIterator.with(iterator(), iterable.iterator());
    }
    
    /**
     * Returns the elements of this iterable followed by the elements of the given iterable.
     */
    @Pure
    public default @Nonnull InfiniteIterable<ELEMENT> combine(@Nonnull InfiniteIterable<? extends ELEMENT> iterable) {
        return () -> CombiningIterator.with(iterator(), iterable.iterator());
    }
    
    /* -------------------------------------------------- Cycling -------------------------------------------------- */
    
    /**
     * Returns the elements of this iterable repeated indefinitely.
     */
    @Pure
    public default @Nonnull InfiniteIterable<ELEMENT> repeated() {
        return () -> CyclingIterator.with(this);
    }
    
    /* -------------------------------------------------- Finding -------------------------------------------------- */
    
    /**
     * Returns the first element of this iterable that fulfills the given predicate or the given default element if no such element is found.
     */
    @Pure
    public default @NonCapturable <@Unspecifiable EXCEPTION extends Exception> @Nullable ELEMENT findFirst(@Nonnull FailablePredicate<? super ELEMENT, ? extends EXCEPTION> predicate, @NonCaptured @Unmodified ELEMENT defaultElement) throws EXCEPTION {
        for (ELEMENT element : this) {
            if (predicate.evaluate(element)) { return element; }
        }
        return defaultElement;
    }
    
    /**
     * Returns the first element of this iterable that fulfills the given predicate or null if no such element is found.
     */
    @Pure
    public default @NonCapturable <@Unspecifiable EXCEPTION extends Exception> @Nullable ELEMENT findFirst(@Nonnull FailablePredicate<? super ELEMENT, ? extends EXCEPTION> predicate) throws EXCEPTION {
        return findFirst(predicate, null);
    }
    
    /**
     * Returns the last element of this iterable that fulfills the given predicate or the given default element if no such element is found.
     */
    @Pure
    public default @NonCapturable <@Unspecifiable EXCEPTION extends Exception> ELEMENT findLast(@Nonnull FailablePredicate<? super ELEMENT, ? extends EXCEPTION> predicate, @NonCaptured @Unmodified ELEMENT defaultElement) throws EXCEPTION {
        @Nullable ELEMENT lastElement = defaultElement;
        for (ELEMENT element : this) {
            if (predicate.evaluate(element)) { lastElement = element; }
        }
        return lastElement;
    }
    
    /**
     * Returns the last element of this iterable that fulfills the given predicate or null if no such element is found.
     */
    @Pure
    public default @NonCapturable <@Unspecifiable EXCEPTION extends Exception> @Nullable ELEMENT findLast(@Nonnull FailablePredicate<? super ELEMENT, ? extends EXCEPTION> predicate) throws EXCEPTION {
        return findLast(predicate, null);
    }
    
    /**
     * Returns the unique element of this iterable that fulfills the given predicate.
     * 
     * @throws NoSuchElementException if no unique element is found in this iterable.
     */
    @Pure
    public default @NonCapturable <@Unspecifiable EXCEPTION extends Exception> ELEMENT findUnique(@Nonnull FailablePredicate<? super ELEMENT, ? extends EXCEPTION> predicate) throws EXCEPTION {
        @Nullable ELEMENT uniqueElement = null;
        boolean found = false;
        for (ELEMENT element : this) {
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
    public default <@Unspecifiable EXCEPTION extends Exception> boolean matchAny(@Nonnull FailablePredicate<? super ELEMENT, ? extends EXCEPTION> predicate) throws EXCEPTION {
        for (ELEMENT element : this) {
            if (predicate.evaluate(element)) { return true; }
        }
        return false;
    }
    
    /**
     * Returns whether all elements of this iterable match the given predicate.
     */
    @Pure
    public default <@Unspecifiable EXCEPTION extends Exception> boolean matchAll(@Nonnull FailablePredicate<? super ELEMENT, ? extends EXCEPTION> predicate) throws EXCEPTION {
        for (ELEMENT element : this) {
            if (!predicate.evaluate(element)) { return false; }
        }
        return true;
    }
    
    /**
     * Returns whether no element of this iterable matches the given predicate.
     */
    @Pure
    public default <@Unspecifiable EXCEPTION extends Exception> boolean matchNone(@Nonnull FailablePredicate<? super ELEMENT, ? extends EXCEPTION> predicate) throws EXCEPTION {
        return !matchAny(predicate);
    }
    
    /* -------------------------------------------------- Reducing -------------------------------------------------- */
    
    /**
     * Returns the value reduced by the given operator or the given element if this iterable is empty.
     */
    @Pure
    public default @NonCapturable <@Unspecifiable EXCEPTION extends Exception> ELEMENT reduce(@Nonnull FailableBinaryOperator<ELEMENT, ? extends EXCEPTION> operator, @NonCaptured @Unmodified ELEMENT element) throws EXCEPTION {
        final @Nonnull Iterator<ELEMENT> iterator = iterator();
        if (iterator.hasNext()) {
            ELEMENT result = iterator.next();
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
    public default @NonCapturable <@Unspecifiable EXCEPTION extends Exception> @Nullable ELEMENT reduce(@Nonnull FailableBinaryOperator<ELEMENT, ? extends EXCEPTION> operator) throws EXCEPTION {
        return reduce(operator, null);
    }
    
    /* -------------------------------------------------- Collecting -------------------------------------------------- */
    
    /**
     * Returns the result of the given collector after consuming all elements of this iterable.
     */
    @Pure
    public default @Capturable <@Specifiable RESULT, @Unspecifiable COLLECT_EXCEPTION extends Exception, @Unspecifiable RESULT_EXCEPTION extends Exception> RESULT collect(@NonCaptured @Modified @Nonnull FailableCollector<? super ELEMENT, ? extends RESULT, ? extends COLLECT_EXCEPTION, ? extends RESULT_EXCEPTION> collector) throws COLLECT_EXCEPTION, RESULT_EXCEPTION {
        for (ELEMENT element : this) { collector.consume(element); }
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
        @Nullable ELEMENT lastElement = null;
        for (@Nullable ELEMENT element : this) {
            if (element != null) {
                if (lastElement != null) {
                    if (element instanceof Comparable<?>) {
                        if (((Comparable<? super ELEMENT>) element).compareTo(lastElement) * (ascending ? 1 : -1) < (strictly ? 1 : 0)) { return false; }
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
    public default @Nonnull FiniteIterable<ELEMENT> sorted(@Nonnull Comparator<? super ELEMENT> comparator) {
        return () -> {
            final @Nonnull List<ELEMENT> list = toList();
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
    public default @Nonnull FiniteIterable<ELEMENT> sorted() {
        return sorted((a, b) -> a == null ? 1 : (b == null ? -1 : ( ((Comparable<? super ELEMENT>) a).compareTo(b) )));
    }
    
    /* -------------------------------------------------- Reversing -------------------------------------------------- */
    
    /**
     * Returns the elements of this iterable in reversed order.
     */
    @Pure
    public default @Nonnull FiniteIterable<ELEMENT> reversed() {
        return () -> ReversingIterator.with(toGenericArray());
    }
    
    /* -------------------------------------------------- Minimum -------------------------------------------------- */
    
    /**
     * Returns the minimum element of this iterable according to the given comparator or the given default element if this iterable is empty.
     */
    @Pure
    public default @NonCapturable ELEMENT min(@Nonnull Comparator<? super ELEMENT> comparator, @NonCaptured @Unmodified ELEMENT defaultElement) {
        return reduce(BinaryOperator.min(comparator), defaultElement);
    }
    
    /**
     * Returns the minimum element of this iterable according to the given comparator or null if this iterable is empty.
     */
    @Pure
    public default @NonCapturable @Nullable ELEMENT min(@Nonnull Comparator<? super ELEMENT> comparator) {
        return min(comparator, null);
    }
    
    /**
     * Returns the minimum element of this iterable according to the natural order or the given default element if this iterable is empty.
     * 
     * @throws ClassCastException if the elements of this iterable are not comparable.
     */
    @Pure
    @SuppressWarnings("unchecked")
    public default @NonCapturable ELEMENT min(@NonCaptured @Unmodified ELEMENT defaultElement) {
        return reduce((a, b) -> a == null ? b : (b == null ? a : ( ((Comparable<? super ELEMENT>) a).compareTo(b) <= 0 ? a : b )), defaultElement);
    }
    
    /**
     * Returns the minimum element of this iterable according to the natural order or null if this iterable is empty.
     * 
     * @throws ClassCastException if the elements of this iterable are not comparable.
     */
    @Pure
    public default @NonCapturable @Nullable ELEMENT min() {
        return min((ELEMENT) null);
    }
    
    /* -------------------------------------------------- Maximum -------------------------------------------------- */
    
    /**
     * Returns the maximum element of this iterable according to the given comparator or the given default element if this iterable is empty.
     */
    @Pure
    public default @NonCapturable ELEMENT max(@Nonnull Comparator<? super ELEMENT> comparator, @NonCaptured @Unmodified ELEMENT defaultElement) {
        return reduce(BinaryOperator.max(comparator), defaultElement);
    }
    
    /**
     * Returns the maximum element of this iterable according to the given comparator or null if this iterable is empty.
     */
    @Pure
    public default @NonCapturable @Nullable ELEMENT max(@Nonnull Comparator<? super ELEMENT> comparator) {
        return max(comparator, null);
    }
    
    /**
     * Returns the maximum element of this iterable according to the natural order or the given default element if this iterable is empty.
     * 
     * @throws ClassCastException if the elements of this iterable are not comparable.
     */
    @Pure
    @SuppressWarnings("unchecked")
    public default @NonCapturable ELEMENT max(@NonCaptured @Unmodified ELEMENT defaultElement) {
        return reduce((a, b) -> a == null ? b : (b == null ? a : ( ((Comparable<? super ELEMENT>) a).compareTo(b) >= 0 ? a : b )), defaultElement);
    }
    
    /**
     * Returns the maximum element of this iterable according to the natural order or null if this iterable is empty.
     * 
     * @throws ClassCastException if the elements of this iterable are not comparable.
     */
    @Pure
    public default @NonCapturable @Nullable ELEMENT max() {
        return max((ELEMENT) null);
    }
    
    /* -------------------------------------------------- Summation -------------------------------------------------- */
    
    /**
     * Returns the sum of all {@link Number numbers} in this iterable as a long.
     */
    @Pure
    public default long sumAsLong() {
        long sum = 0;
        for (ELEMENT element : this) {
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
        for (ELEMENT element : this) {
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
        for (ELEMENT element : this) {
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
            for (ELEMENT element : this) {
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
    public default @Nonnull String join(@Nullable Circumfix fixes, @Nonnull CharSequence empty, @Nonnull CharSequence delimiter) {
        if (fixes == null) { return join("", "", empty, delimiter); }
        else { return join(fixes.getPrefix(), fixes.getSuffix(), empty, delimiter); }
    }
    
    /**
     * Returns the elements of this iterable joined by commas with the given fixes or the given empty string if this iterable is empty.
     */
    @Pure
    public default @Nonnull String join(@Nullable Circumfix fixes, @Nonnull CharSequence empty) {
        return join(fixes, empty, ", ");
    }
    
    /**
     * Returns the elements of this iterable joined by commas with the given fixes.
     */
    @Pure
    public default @Nonnull String join(@Nullable Circumfix fixes) {
        return join(fixes, fixes != null ? fixes.getBoth() : "");
    }
    
    /**
     * Returns the elements of this iterable joined by the given delimiter.
     */
    @Pure
    public default @Nonnull String join(@Nonnull CharSequence delimiter) {
        return join((Circumfix) null, "", delimiter);
    }
    
    /**
     * Returns the elements of this iterable joined by commas.
     */
    @Pure
    public default @Nonnull String join() {
        return join((Circumfix) null);
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
    public default @Capturable @Nonnull ELEMENT[] toGenericArray() {
        return (ELEMENT[]) toArray();
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
    public default @Capturable <@Specifiable TYPE> @Nonnull TYPE[] toArray(@NonCaptured @Modified @Nonnull TYPE[] array) {
        final int size = size();
        final @Nonnull TYPE[] result = array.length >= size ? array : (TYPE[]) Array.newInstance(array.getClass().getComponentType(), size);
        final @Nonnull Iterator<ELEMENT> iterator = iterator();
        for (int i = 0; i < result.length; i++) {
            if (iterator.hasNext()) {
                result[i] = (TYPE) iterator.next();
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
    public default @Capturable @Modifiable @Nonnull List<ELEMENT> toList() {
        final @Nonnull List<ELEMENT> result = new LinkedList<>();
        for (ELEMENT element : this) {
            result.add(element);
        }
        return result;
    }
    
    /**
     * Returns the elements of this iterable as a set.
     */
    @Pure
    public default @Capturable @Modifiable @Nonnull Set<ELEMENT> toSet() {
        final @Nonnull Set<ELEMENT> result = new LinkedHashSet<>();
        for (ELEMENT element : this) {
            result.add(element);
        }
        return result;
    }
    
    /**
     * Returns the elements of this iterable as a map with their key determined by the given function.
     * Elements that are mapped to the same key overwrite each other. If this is not desired, use
     * {@link #groupBy(net.digitalid.utility.functional.failable.FailableUnaryFunction)} instead.
     */
    @Pure
    public default @Capturable <@Specifiable KEY, @Unspecifiable EXCEPTION extends Exception> @Modifiable @Nonnull Map<KEY, ELEMENT> toMap(@Nonnull FailableUnaryFunction<? super ELEMENT, ? extends KEY, ? extends EXCEPTION> function) throws EXCEPTION {
        final @Nonnull Map<KEY, ELEMENT> result = new LinkedHashMap<>();
        for (ELEMENT element : this) {
            result.put(function.evaluate(element), element);
        }
        return result;
    }
    
    /* -------------------------------------------------- Grouping -------------------------------------------------- */
    
    /**
     * Returns the elements of this iterable as a map grouped by the given function.
     */
    @Pure
    public default @Capturable <@Specifiable KEY, @Unspecifiable EXCEPTION extends Exception> @Modifiable @Nonnull Map<KEY, @Nonnull List<ELEMENT>> groupBy(@Nonnull FailableUnaryFunction<? super ELEMENT, ? extends KEY, ? extends EXCEPTION> function) throws EXCEPTION {
        final @Nonnull Map<KEY, List<ELEMENT>> result = new LinkedHashMap<>(size());
        for (ELEMENT element : this) {
            final KEY key = function.evaluate(element);
            @Nullable List<ELEMENT> list = result.get(key);
            if (list == null) {
                list = new LinkedList<>();
                result.put(key, list);
            }
            list.add(element);
        }
        return result;
    }
    
    /* -------------------------------------------------- Evaluating -------------------------------------------------- */
    
    /**
     * Iterates through the elements of this iterable and returns them as a new iterable.
     * This method is useful to trigger any mapping and filtering exceptions and to cache the result.
     */
    @Pure
    @SuppressWarnings("unchecked")
    public default @Capturable <@Unspecifiable EXCEPTION extends Exception> @Nonnull FiniteIterable<ELEMENT> evaluate() throws EXCEPTION {
        try {
            return FiniteIterable.of(toList());
        } catch (@Nonnull IterationException exception) {
            throw (EXCEPTION) exception.getCause();
        }
    }
    
}
