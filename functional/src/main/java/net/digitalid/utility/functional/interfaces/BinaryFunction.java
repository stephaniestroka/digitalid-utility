package net.digitalid.utility.functional.interfaces;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.tuples.Pair;

/**
 * This functional interface models a binary function that maps an object of type {@code I0} and an object of type {@code I1} to an object of type {@code O}.
 */
public interface BinaryFunction<I0, I1, O> {
    
    /* -------------------------------------------------- Evaluation -------------------------------------------------- */
    
    /**
     * Evaluates this function for the given objects.
     * All implementations of this method have to be side-effect-free.
     */
    @Pure
    public O evaluate(I0 object0, I1 object1);
    
    /**
     * Evaluates this function for the objects in the given pair.
     */
    @Pure
    public default O evaluate(Pair<I0, I1> pair) {
        return evaluate(pair.get0(), pair.get1());
    }
    
    /* -------------------------------------------------- Composition -------------------------------------------------- */
    
    /**
     * Returns the composition of this function followed by the given function.
     */
    @Pure
    public default <T> BinaryFunction<I0, I1, T> before(UnaryFunction<? super O, ? extends T> function) {
        return (object0, object1) -> function.evaluate(evaluate(object0, object1));
    }
    
    /**
     * Returns the composition of the given functions followed by this function.
     */
    @Pure
    public default <T0, T1> BinaryFunction<T0, T1, O> after(UnaryFunction<? super T0, ? extends I0> function0, UnaryFunction<? super T1, ? extends I1> function1) {
        return (object0, object1) -> evaluate(function0.evaluate(object0), function1.evaluate(object1));
    }
    
}
