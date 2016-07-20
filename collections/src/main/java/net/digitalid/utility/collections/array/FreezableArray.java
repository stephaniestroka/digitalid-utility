package net.digitalid.utility.collections.array;

import java.util.Arrays;
import java.util.Collection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.circumfixes.Brackets;
import net.digitalid.utility.collaboration.annotations.TODO;
import net.digitalid.utility.collaboration.enumerations.Author;
import net.digitalid.utility.collaboration.enumerations.Priority;
import net.digitalid.utility.collections.collection.FreezableCollection;
import net.digitalid.utility.collections.iterable.FreezableIterable;
import net.digitalid.utility.freezable.FreezableInterface;
import net.digitalid.utility.freezable.annotations.Freezable;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.freezable.annotations.NonFrozenRecipient;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.functional.iterators.ReadOnlyArrayIterator;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.rootclass.RootClass;
import net.digitalid.utility.validation.annotations.index.Index;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.method.Chainable;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.annotations.type.ReadOnly;

/**
 * This class models {@link FreezableInterface freezable} arrays.
 * It is recommended to use only {@link ReadOnly} or {@link Immutable} types for the elements.
 */
@GenerateSubclass
@Freezable(ReadOnlyArray.class)
public abstract class FreezableArray<E> extends RootClass implements ReadOnlyArray<E>, FreezableIterable<E> {
    
    /* -------------------------------------------------- Elements -------------------------------------------------- */
    
    /**
     * Stores the elements in an array.
     */
    private final @Nonnull E[] elements;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    @SuppressWarnings("unchecked")
    protected FreezableArray(@NonNegative int size) {
        this.elements = (E[]) new Object[size];
    }
    
    /**
     * Returns a new freezable array with the given size.
     */
    @Pure
    public static @Capturable <E> @Nonnull @NonFrozen FreezableArray<E> withSize(@NonNegative int size) {
        return new FreezableArraySubclass<>(size);
    }
    
    @SafeVarargs
    protected FreezableArray(@NonCaptured @Unmodified @Nonnull E... elements) {
        this.elements = elements.clone();
    }
    
    /**
     * Returns a new freezable array with the given elements or null if the given array is null.
     */
    @Pure
    @SafeVarargs
    public static @Capturable <E> @NonFrozen FreezableArray<E> withElements(@NonCaptured @Unmodified E... elements) {
        return elements == null ? null : new FreezableArraySubclass<>(elements);
    }
    
    protected FreezableArray(@NonNegative int size, @Nonnull Iterable<? extends E> iterable) {
        this(size);
        
        int index = 0;
        for (E element : iterable) {
            this.elements[index++] = element;
        }
    }
    
    /**
     * Returns a new freezable array with the elements of the given iterable or null if the given iterable is null.
     */
    @Pure
    public static @Capturable <E> @NonFrozen FreezableArray<E> withElementsOf(FiniteIterable<? extends E> iterable) {
        return iterable == null ? null : new FreezableArraySubclass<>(iterable.size(), iterable);
    }
    
    /**
     * Returns a new freezable array with the elements of the given collection or null if the given collection is null.
     */
    @Pure
    public static @Capturable <E> @NonFrozen FreezableArray<E> withElementsOf(@NonCaptured @Unmodified Collection<? extends E> collection) {
        return collection == null ? null : new FreezableArraySubclass<>(collection.size(), collection);
    }
    
    /**
     * Returns a new freezable array with the elements of the given freezable collection or null if the given collection is null.
     */
    @Pure
    public static @Capturable <E> @NonFrozen FreezableArray<E> withElementsOf(@NonCaptured @Unmodified FreezableCollection<? extends E> collection) {
        return collection == null ? null : new FreezableArraySubclass<>(collection.size(), collection);
    }
    
    /* -------------------------------------------------- Freezable -------------------------------------------------- */
    
    private boolean frozen = false;
    
    @Pure
    @Override
    @TODO(task = "Generate this implementation.", date = "2016-04-06", author = Author.KASPAR_ETTER, assignee = Author.STEPHANIE_STROKA, priority = Priority.MIDDLE)
    public boolean isFrozen() {
        return frozen;
    }
    
    @Impure
    @Override
    @NonFrozenRecipient
    @TODO(task = "Generate this implementation.", date = "2016-04-06", author = Author.KASPAR_ETTER, assignee = Author.STEPHANIE_STROKA, priority = Priority.MIDDLE)
    public @Chainable @Nonnull @Frozen ReadOnlyArray<E> freeze() {
        this.frozen = true;
        return this;
    }
    
    /* -------------------------------------------------- Size -------------------------------------------------- */
    
    @Pure
    @Override
    public @NonNegative int size() {
        return elements.length;
    }
    
    /* -------------------------------------------------- Access -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nullable E get(@Index int index) {
        return elements[index];
    }
    
    /* -------------------------------------------------- Iterator -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull ReadOnlyArrayIterator<E> iterator() {
        return ReadOnlyArrayIterator.with(elements);
    }
    
    /* -------------------------------------------------- Element -------------------------------------------------- */
    
    @Pure
    @Override
    public @NonCapturable E getFirst(@NonCaptured @Unmodified E defaultElement) {
        return elements.length > 0 ? elements[0] : defaultElement;
    }
    
    @Pure
    @Override
    public @NonCapturable E getLast(@NonCaptured @Unmodified E defaultElement) {
        return elements.length > 0 ? elements[elements.length - 1] : defaultElement;
    }
    
    /* -------------------------------------------------- Operations -------------------------------------------------- */
    
    /**
     * Sets the element at the given index to the given element.
     */
    @Impure
    @NonFrozenRecipient
    public void set(@Index int index, E element) {
        elements[index] = element;
    }
    
    /**
     * Sets each element of this array to the given element.
     */
    @Impure
    @NonFrozenRecipient
    public @Chainable @Nonnull @NonFrozen FreezableArray<E> setAll(E element) {
        for (int i = 0; i < elements.length; i++) { elements[i] = element; }
        return this;
    }
    
    /* -------------------------------------------------- Cloneable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull E[] toArray() {
        return elements.clone();
    }
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableArray<E> clone() {
        return new FreezableArraySubclass<>(elements.clone());
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean equals(@Nullable Object object) {
        if (object == this) { return true; }
        if (object == null) { return false; }
        if (object instanceof FreezableArray) { return Arrays.equals(elements, ((FreezableArray) object).elements); }
        if (object instanceof Object[]) { return Arrays.equals(elements, (Object[]) object); }
        return false;
    }
    
    @Pure
    @Override
    public int hashCode() {
        return Arrays.deepHashCode(elements);
    }
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return join(Brackets.SQUARE);
    }
    
}
