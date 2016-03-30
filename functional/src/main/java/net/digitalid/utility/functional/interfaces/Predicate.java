package net.digitalid.utility.functional.interfaces;

import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This functional interface models a predicate that evaluates whether an object of type {@code T} satisfies a condition.
 */
public interface Predicate<T> {
    
    /* -------------------------------------------------- Evaluation -------------------------------------------------- */
    
    /**
     * Evaluates whether the given object satisfies this predicate.
     * All implementations of this method have to be side-effect-free.
     */
    @Pure
    public boolean evaluate(T object);
    
    /* -------------------------------------------------- Conjunction -------------------------------------------------- */
    
    /**
     * Returns the conjunction of this predicate with the given predicate.
     */
    @Pure
    public default Predicate<T> and(Predicate<? super T> predicate) {
        return object -> evaluate(object) && predicate.evaluate(object);
    }
    
    /**
     * Returns the conjunction of the given predicates.
     */
    @Pure
    public static <T> Predicate<T> and(FiniteIterable<? extends Predicate<? super T>> predicates) {
        return object -> {
                for (Predicate<? super T> predicate : predicates) {
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
    public default Predicate<T> or(Predicate<? super T> predicate) {
        return object -> evaluate(object) || predicate.evaluate(object);
    }
    
    /**
     * Returns the disjunction of the given predicates.
     */
    @Pure
    public static <T> Predicate<T> or(FiniteIterable<? extends Predicate<? super T>> predicates) {
        return object -> {
                for (Predicate<? super T> predicate : predicates) {
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
    public default Predicate<T> negate() {
        return object -> !evaluate(object);
    }
    
    /* -------------------------------------------------- Composition -------------------------------------------------- */
    
    /**
     * Returns the composition of the given function followed by this predicate.
     */
    @Pure
    public default <I> Predicate<I> after(UnaryFunction<? super I, ? extends T> function) {
        return object -> evaluate(function.evaluate(object));
    }
    
    /* -------------------------------------------------- Conversion -------------------------------------------------- */
    
    /**
     * Returns this predicate as a unary function.
     */
    @Pure
    public default UnaryFunction<T, Boolean> asFunction() {
        return object -> evaluate(object);
    }
    
}
