package net.digitalid.utility.functional.interfaces;

import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This functional interface models a predicate that evaluates whether an object satisfies a condition.
 */
public interface Predicate<T> {
    
    /**
     * Evaluates whether the given object satisfies this predicate.
     * All implementations of this method have to be side-effect-free.
     */
    @Pure
    public boolean evaluate(T object);
    
    /**
     * Returns the conjunction of this predicate with the given predicate.
     */
    @Pure
    public default Predicate<T> and(Predicate<? super T> predicate) {
        return object -> evaluate(object) && predicate.evaluate(object);
    }
    
    /**
     * Returns the disjunction of this predicate with the given predicate.
     */
    @Pure
    public default Predicate<T> or(Predicate<? super T> predicate) {
        return object -> evaluate(object) || predicate.evaluate(object);
    }
    
    /**
     * Returns the negation of this predicate.
     */
    @Pure
    public default Predicate<T> negate() {
        return object -> !evaluate(object);
    }
    
}
