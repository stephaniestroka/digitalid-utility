package net.digitalid.utility.functional.interfaces;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.functional.failable.FailablePredicate;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This functional interface models a predicate that evaluates whether an object of type {@code INPUT} satisfies a condition.
 */
@Immutable
@Functional
public interface Predicate<@Specifiable INPUT> extends FailablePredicate<INPUT, RuntimeException> {
    
    /* -------------------------------------------------- Conjunction -------------------------------------------------- */
    
    /**
     * Returns the conjunction of this predicate with the given predicate.
     */
    @Pure
    public default @Nonnull Predicate<INPUT> and(@Nonnull Predicate<? super INPUT> predicate) {
        return input -> evaluate(input) && predicate.evaluate(input);
    }
    
    /**
     * Returns the conjunction of the given predicates.
     */
    @Pure
    public static <@Specifiable INPUT> @Nonnull Predicate<INPUT> and(@Nonnull FiniteIterable<@Nonnull ? extends Predicate<? super INPUT>> predicates) {
        return input -> {
            for (@Nonnull Predicate<? super INPUT> predicate : predicates) {
                if (!predicate.evaluate(input)) { return false; }
            }
            return true;
        };
    }
    
    /* -------------------------------------------------- Disjunction -------------------------------------------------- */
    
    /**
     * Returns the disjunction of this predicate with the given predicate.
     */
    @Pure
    public default @Nonnull Predicate<INPUT> or(@Nonnull Predicate<? super INPUT> predicate) {
        return input -> evaluate(input) || predicate.evaluate(input);
    }
    
    /**
     * Returns the disjunction of the given predicates.
     */
    @Pure
    @SafeVarargs
    public static <@Specifiable INPUT> @Nonnull Predicate<INPUT> or(@Nonnull @NonNullableElements Predicate<? super INPUT>... predicates) {
        return input -> {
            for (@Nonnull Predicate<? super INPUT> predicate : predicates) {
                if (predicate.evaluate(input)) { return true; }
            }
            return false;
        };
    }
    
    /**
     * Returns the disjunction of the given predicates.
     */
    @Pure
    public static <@Specifiable INPUT> @Nonnull Predicate<INPUT> or(@Nonnull FiniteIterable<@Nonnull ? extends Predicate<? super INPUT>> predicates) {
        return input -> {
            for (@Nonnull Predicate<? super INPUT> predicate : predicates) {
                if (predicate.evaluate(input)) { return true; }
            }
            return false;
        };
    }
    
    /* -------------------------------------------------- Negation -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull Predicate<INPUT> negate() {
        return input -> !evaluate(input);
    }
    
    /* -------------------------------------------------- Composition -------------------------------------------------- */
    
    /**
     * Returns the composition of the given function followed by this predicate.
     */
    @Pure
    public default <@Specifiable INITIAL_INPUT> @Nonnull Predicate<INITIAL_INPUT> after(@Nonnull UnaryFunction<? super INITIAL_INPUT, ? extends INPUT> function) {
        return input -> evaluate(function.evaluate(input));
    }
    
    /* -------------------------------------------------- Conversion -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull UnaryFunction<INPUT, @Nonnull Boolean> asFunction() {
        return this::evaluate;
    }
    
    /* -------------------------------------------------- Null Handling -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull Predicate<@Nullable INPUT> replaceNull(boolean defaultValue) {
        return input -> input != null ? evaluate(input) : defaultValue;
    }
    
    /* -------------------------------------------------- Constants -------------------------------------------------- */
    
    /**
     * Stores a predicate which always returns true.
     */
    public static final @Nonnull Predicate<@Nullable Object> ALWAYS_TRUE = input -> true;
    
    /**
     * Stores a predicate which always returns false.
     */
    public static final @Nonnull Predicate<@Nullable Object> ALWAYS_FALSE = input -> false;
    
}
