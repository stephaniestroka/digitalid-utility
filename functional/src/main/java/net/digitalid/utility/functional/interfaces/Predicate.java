package net.digitalid.utility.functional.interfaces;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.functional.failable.FailablePredicate;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This functional interface models a predicate that evaluates whether an object of type {@code T} satisfies a condition.
 */
@Immutable
@Functional
public interface Predicate<T> extends FailablePredicate<T, RuntimeException> {
    
    /* -------------------------------------------------- Conjunction -------------------------------------------------- */
    
    /**
     * Returns the conjunction of this predicate with the given predicate.
     */
    @Pure
    public default @Nonnull Predicate<T> and(@Nonnull Predicate<? super T> predicate) {
        return object -> evaluate(object) && predicate.evaluate(object);
    }
    
    /**
     * Returns the conjunction of the given predicates.
     */
    @Pure
    @SafeVarargs
    public static <T> @Nonnull Predicate<T> and(@Nonnull @NonNullableElements Predicate<? super T>... predicates) {
        return object -> {
            for (@Nonnull Predicate<? super T> predicate : predicates) {
                if (!predicate.evaluate(object)) { return false; }
            }
            return true;
        };
    }
    
    /**
     * Returns the conjunction of the given predicates.
     */
    @Pure
    public static <T> @Nonnull Predicate<T> and(@Nonnull FiniteIterable<@Nonnull ? extends Predicate<? super T>> predicates) {
        return object -> {
            for (@Nonnull Predicate<? super T> predicate : predicates) {
                if (!predicate.evaluate(object)) { return false; }
            }
            return true;
        };
    }
    
    /* -------------------------------------------------- Disjunction -------------------------------------------------- */
    
    /**
     * Returns the disjunction of this predicate with the given predicate.
     */
    @Pure
    public default @Nonnull Predicate<T> or(@Nonnull Predicate<? super T> predicate) {
        return object -> evaluate(object) || predicate.evaluate(object);
    }
    
    /**
     * Returns the disjunction of the given predicates.
     */
    @Pure
    @SafeVarargs
    public static <T> @Nonnull Predicate<T> or(@Nonnull @NonNullableElements Predicate<? super T>... predicates) {
        return object -> {
            for (@Nonnull Predicate<? super T> predicate : predicates) {
                if (predicate.evaluate(object)) { return true; }
            }
            return false;
        };
    }
    
    /**
     * Returns the disjunction of the given predicates.
     */
    @Pure
    public static <T> @Nonnull Predicate<T> or(@Nonnull FiniteIterable<@Nonnull ? extends Predicate<? super T>> predicates) {
        return object -> {
            for (@Nonnull Predicate<? super T> predicate : predicates) {
                if (predicate.evaluate(object)) { return true; }
            }
            return false;
        };
    }
    
    /* -------------------------------------------------- Negation -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull Predicate<T> negate() {
        return object -> !evaluate(object);
    }
    
    /* -------------------------------------------------- Composition -------------------------------------------------- */
    
    /**
     * Returns the composition of the given function followed by this predicate.
     */
    @Pure
    public default <I> @Nonnull Predicate<I> after(@Nonnull UnaryFunction<? super I, ? extends T> function) {
        return object -> evaluate(function.evaluate(object));
    }
    
    /* -------------------------------------------------- Conversion -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull UnaryFunction<T, @Nonnull Boolean> asFunction() {
        return object -> evaluate(object);
    }
    
    /* -------------------------------------------------- Null Handling -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull Predicate<@Nullable T> replaceNull(boolean defaultValue) {
        return object -> object != null ? evaluate(object) : defaultValue;
    }
    
    /* -------------------------------------------------- Constants -------------------------------------------------- */
    
    /**
     * Stores a predicate which always returns true.
     */
    public static final @Nonnull Predicate<@Nullable Object> ALWAYS_TRUE = object -> true;
    
    /**
     * Stores a predicate which always returns false.
     */
    public static final @Nonnull Predicate<@Nullable Object> ALWAYS_FALSE = object -> false;
    
}
