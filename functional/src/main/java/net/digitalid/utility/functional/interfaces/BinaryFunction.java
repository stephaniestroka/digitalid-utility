package net.digitalid.utility.functional.interfaces;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.tuples.Pair;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This functional interface models a binary function that maps an object of type {@code I0} and an object of type {@code I1} to an object of type {@code O}.
 */
@Immutable
@Functional
public interface BinaryFunction<I0, I1, O> {
    
    /* -------------------------------------------------- Evaluation -------------------------------------------------- */
    
    /**
     * Evaluates this function for the given objects.
     * All implementations of this method have to be side-effect-free.
     */
    @Pure
    public O evaluate(@NonCaptured @Unmodified I0 object0, @NonCaptured @Unmodified I1 object1);
    
    /**
     * Evaluates this function for the objects in the given pair.
     */
    @Pure
    public default O evaluate(@Nonnull Pair<I0, I1> pair) {
        return evaluate(pair.get0(), pair.get1());
    }
    
    /* -------------------------------------------------- Composition -------------------------------------------------- */
    
    /**
     * Returns the composition of this function followed by the given function.
     */
    @Pure
    public default <T> @Nonnull BinaryFunction<I0, I1, T> before(@Nonnull UnaryFunction<? super O, ? extends T> function) {
        return (object0, object1) -> function.evaluate(evaluate(object0, object1));
    }
    
    /**
     * Returns the composition of the given functions followed by this function.
     */
    @Pure
    public default <T0, T1> @Nonnull BinaryFunction<T0, T1, O> after(@Nonnull UnaryFunction<? super T0, ? extends I0> function0, @Nonnull UnaryFunction<? super T1, ? extends I1> function1) {
        return (object0, object1) -> evaluate(function0.evaluate(object0), function1.evaluate(object1));
    }
    
    /* -------------------------------------------------- Null Handling -------------------------------------------------- */
    
    /**
     * Returns a new function based on this function that propagates null instead of evaluating it.
     */
    @Pure
    public default @Nonnull BinaryFunction<I0, I1, O> propagateNull() {
        return (object0, object1) -> object0 != null && object1 != null ? evaluate(object0, object1) : null;
    }
    
    /**
     * Returns a new function based on this function that returns the given default value for null.
     */
    @Pure
    public default @Nonnull BinaryFunction<I0, I1, O> replaceNull(@Nonnull O defaultValue) {
        return (object0, object1) -> object0 != null && object1 != null ? evaluate(object0, object1) : defaultValue;
    }
    
}
