package net.digitalid.utility.functional.failable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.functional.interfaces.Consumer;
import net.digitalid.utility.functional.interfaces.Predicate;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This functional interface models a failable predicate that evaluates whether an object of type {@code T} satisfies a condition.
 */
@Immutable
@Functional
public interface FailablePredicate<T, X extends Exception> {
    
    /* -------------------------------------------------- Evaluation -------------------------------------------------- */
    
    /**
     * Evaluates whether the given object satisfies this predicate.
     * All implementations of this method have to be side-effect-free.
     */
    @Pure
    public boolean evaluate(@NonCaptured @Unmodified T object) throws X;
    
    /* -------------------------------------------------- Suppression -------------------------------------------------- */
    
    /**
     * Returns a predicate that catches the exceptions of this predicate, passes them to the given exception handler and returns the given default output instead.
     */
    @Pure
    public default @Nonnull Predicate<T> suppressExceptions(@Captured @Nonnull Consumer<@Nonnull ? super Exception> handler, boolean defaultOutput) {
        return object -> {
            try {
                return evaluate(object);
            } catch (@Nonnull Exception exception) {
                handler.consume(exception);
                return defaultOutput;
            }
        };
    }
    
    /**
     * Returns a predicate that catches the exceptions of this predicate, passes them to the given exception handler and returns false instead.
     */
    @Pure
    public default @Nonnull Predicate<T> suppressExceptions(@Captured @Nonnull Consumer<@Nonnull ? super Exception> handler) {
        return suppressExceptions(handler, false);
    }
    
    /**
     * Returns a predicate that suppresses the exceptions of this predicate and returns the given default output instead.
     */
    @Pure
    public default @Nonnull Predicate<T> suppressExceptions(boolean defaultOutput) {
        return suppressExceptions(Consumer.DO_NOTHING, defaultOutput);
    }
    
    /**
     * Returns a predicate that suppresses the exceptions of this predicate and returns false instead.
     */
    @Pure
    public default @Nonnull Predicate<T> suppressExceptions() {
        return suppressExceptions(Consumer.DO_NOTHING, false);
    }
    
    /* -------------------------------------------------- Conjunction -------------------------------------------------- */
    
    /**
     * Returns the conjunction of this predicate with the given predicate.
     */
    @Pure
    public default @Nonnull FailablePredicate<T, X> and(@Nonnull FailablePredicate<? super T, ? extends X> predicate) {
        return object -> evaluate(object) && predicate.evaluate(object);
    }
    
    /**
     * Returns the conjunction of the given predicates.
     */
    @Pure
    @SafeVarargs
    public static <T, X extends Exception> @Nonnull FailablePredicate<T, X> and(@Nonnull @NonNullableElements FailablePredicate<? super T, ? extends X>... predicates) {
        return object -> {
            for (@Nonnull FailablePredicate<? super T, ? extends X> predicate : predicates) {
                if (!predicate.evaluate(object)) { return false; }
            }
            return true;
        };
    }
    
    /**
     * Returns the conjunction of the given predicates.
     */
    @Pure
    public static <T, X extends Exception> @Nonnull FailablePredicate<T, X> and(@Nonnull FiniteIterable<@Nonnull ? extends FailablePredicate<? super T, ? extends X>> predicates) {
        return object -> {
            for (@Nonnull FailablePredicate<? super T, ? extends X> predicate : predicates) {
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
    public default @Nonnull FailablePredicate<T, X> or(@Nonnull FailablePredicate<? super T, ? extends X> predicate) {
        return object -> evaluate(object) || predicate.evaluate(object);
    }
    
    /**
     * Returns the disjunction of the given predicates.
     */
    @Pure
    @SafeVarargs
    public static <T, X extends Exception> @Nonnull FailablePredicate<T, X> or(@Nonnull @NonNullableElements FailablePredicate<? super T, ? extends X>... predicates) {
        return object -> {
            for (@Nonnull FailablePredicate<? super T, ? extends X> predicate : predicates) {
                if (predicate.evaluate(object)) { return true; }
            }
            return false;
        };
    }
    
    /**
     * Returns the disjunction of the given predicates.
     */
    @Pure
    public static <T, X extends Exception> @Nonnull FailablePredicate<T, X> or(@Nonnull FiniteIterable<@Nonnull ? extends FailablePredicate<? super T, ? extends X>> predicates) {
        return object -> {
            for (@Nonnull FailablePredicate<? super T, ? extends X> predicate : predicates) {
                if (predicate.evaluate(object)) { return true; }
            }
            return false;
        };
    }
    
    /* -------------------------------------------------- Negation -------------------------------------------------- */
    
    /**
     * Returns the negation of this predicate.
     */
    @Pure
    public default @Nonnull FailablePredicate<T, X> negate() {
        return object -> !evaluate(object);
    }
    
    /* -------------------------------------------------- Composition -------------------------------------------------- */
    
    /**
     * Returns the composition of the given function and predicate with a flexible exception type.
     */
    @Pure
    public static <I, T, X extends Exception> @Nonnull FailablePredicate<I, X> compose(@Nonnull FailableUnaryFunction<? super I, ? extends T, ? extends X> function, @Nonnull FailablePredicate<? super T, ? extends X> predicate) {
        return object -> predicate.evaluate(function.evaluate(object));
    }
    
    /**
     * Returns the composition of the given function followed by this predicate.
     * Unfortunately, it is not possible to make the exception type flexible.
     * 
     * @see #compose(net.digitalid.utility.functional.failable.FailableUnaryFunction, net.digitalid.utility.functional.failable.FailablePredicate)
     */
    @Pure
    public default <I> @Nonnull FailablePredicate<I, X> after(@Nonnull FailableUnaryFunction<? super I, ? extends T, ? extends X> function) {
        return object -> evaluate(function.evaluate(object));
    }
    
    /* -------------------------------------------------- Conversion -------------------------------------------------- */
    
    /**
     * Returns this predicate as a unary function.
     */
    @Pure
    public default @Nonnull FailableUnaryFunction<T, @Nonnull Boolean, X> asFunction() {
        return object -> evaluate(object);
    }
    
    /* -------------------------------------------------- Null Handling -------------------------------------------------- */
    
    /**
     * Returns a new predicate based on this predicate that returns the given default value for null.
     */
    @Pure
    public default @Nonnull FailablePredicate<@Nullable T, X> replaceNull(boolean defaultValue) {
        return object -> object != null ? evaluate(object) : defaultValue;
    }
    
}
