package net.digitalid.utility.functional.nullable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.functional.failable.FailableBinaryFunction;
import net.digitalid.utility.functional.failable.FailableConsumer;
import net.digitalid.utility.functional.failable.FailablePredicate;
import net.digitalid.utility.functional.failable.FailableUnaryFunction;
import net.digitalid.utility.validation.annotations.type.Utility;

/**
 * This utility class allows to evaluate functional interfaces only in case their input is not null.
 * 
 * @see Get
 */
@Utility
public class Evaluate {
    
    /* -------------------------------------------------- Consumer -------------------------------------------------- */
    
    /**
     * Lets the given consumer consume the given input if it is not null.
     */
    @Pure
    public static <I, X extends Exception> void consumerIfNotNull(@Captured @Nullable I input, @Nonnull FailableConsumer<? super I, ? extends X> consumer) throws X {
        if (input != null) { consumer.consume(input); }
    }
    
    /* -------------------------------------------------- Predicate -------------------------------------------------- */
    
    /**
     * Evaluates the given predicate for the given input if it is not null or returns the given default output otherwise.
     */
    @Pure
    public static <I, X extends Exception> boolean predicateIfNotNull(@NonCaptured @Unmodified @Nullable I input, @Nonnull FailablePredicate<? super I, ? extends X> predicate, boolean defaultOutput) throws X {
        return input != null ? predicate.evaluate(input) : defaultOutput;
    }
    
    /**
     * Evaluates the given predicate for the given input if it is not null or returns false otherwise.
     */
    @Pure
    public static <I, X extends Exception> boolean predicateIfNotNull(@NonCaptured @Unmodified @Nullable I input, @Nonnull FailablePredicate<? super I, ? extends X> predicate) throws X {
        return Evaluate.predicateIfNotNull(input, predicate, false);
    }
    
    /* -------------------------------------------------- Unary Function -------------------------------------------------- */
    
    /**
     * Evaluates the given function for the given input if it is not null or returns the given default output otherwise.
     */
    @Pure
    public static <I, O, X extends Exception> O functionIfNotNull(@NonCaptured @Unmodified @Nullable I input, @Nonnull FailableUnaryFunction<? super I, ? extends O, ? extends X> function, @NonCaptured @Unmodified O defaultOutput) throws X {
        return input != null ? function.evaluate(input) : defaultOutput;
    }
    
    /**
     * Evaluates the given function for the given input if it is not null or propagates null otherwise.
     */
    @Pure
    public static <I, O, X extends Exception> @Nullable O functionIfNotNull(@NonCaptured @Unmodified @Nullable I input, @Nonnull FailableUnaryFunction<? super I, ? extends O, ? extends X> function) throws X {
        return Evaluate.functionIfNotNull(input, function, null);
    }
    
    /* -------------------------------------------------- Binary Function -------------------------------------------------- */
    
    /**
     * Evaluates the given function for the given inputs if the first input is not null or returns the given default output otherwise.
     */
    @Pure
    public static <I0, I1, O, X extends Exception> O functionIfFirstNotNull(@NonCaptured @Unmodified @Nullable I0 input0, @NonCaptured @Unmodified I1 input1, @Nonnull FailableBinaryFunction<? super I0, ? super I1, ? extends O, ? extends X> function, @NonCaptured @Unmodified O defaultOutput) throws X {
        return input0 != null ? function.evaluate(input0, input1) : defaultOutput;
    }
    
    /**
     * Evaluates the given function for the given inputs if the first input is not null or propagates null otherwise.
     */
    @Pure
    public static <I0, I1, O, X extends Exception> @Nullable O functionIfFirstNotNull(@NonCaptured @Unmodified @Nullable I0 input0, @NonCaptured @Unmodified I1 input1, @Nonnull FailableBinaryFunction<? super I0, ? super I1, ? extends O, ? extends X> function) throws X {
        return Evaluate.functionIfFirstNotNull(input0, input1, function, null);
    }
    
    /**
     * Evaluates the given function for the given inputs if both inputs are not null or returns the given default output otherwise.
     */
    @Pure
    public static <I0, I1, O, X extends Exception> O functionIfBothNotNull(@NonCaptured @Unmodified @Nullable I0 input0, @NonCaptured @Unmodified @Nullable I1 input1, @Nonnull FailableBinaryFunction<? super I0, ? super I1, ? extends O, ? extends X> function, @NonCaptured @Unmodified O defaultOutput) throws X {
        return input0 != null && input1 != null ? function.evaluate(input0, input1) : defaultOutput;
    }
    
    /**
     * Evaluates the given function for the given inputs if both inputs are not null or propagates null otherwise.
     */
    @Pure
    public static <I0, I1, O, X extends Exception> @Nullable O functionIfBothNotNull(@NonCaptured @Unmodified @Nullable I0 input0, @NonCaptured @Unmodified @Nullable I1 input1, @Nonnull FailableBinaryFunction<? super I0, ? super I1, ? extends O, ? extends X> function) throws X {
        return Evaluate.functionIfBothNotNull(input0, input1, function, null);
    }
    
}
