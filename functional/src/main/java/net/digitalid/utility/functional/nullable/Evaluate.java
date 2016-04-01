package net.digitalid.utility.functional.nullable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.functional.interfaces.BinaryFunction;
import net.digitalid.utility.functional.interfaces.Consumer;
import net.digitalid.utility.functional.interfaces.Predicate;
import net.digitalid.utility.functional.interfaces.UnaryFunction;
import net.digitalid.utility.validation.annotations.type.Utility;

/**
 * This utility class allows to evaluate functional interfaces only in case their input is not null.
 */
@Utility
public class Evaluate {
    
    /* -------------------------------------------------- Consumer -------------------------------------------------- */
    
    /**
     * Lets the given consumer consume the given input if it is not null.
     */
    @Pure
    public static <I> void ifNotNull(@Captured @Nullable I input, @Nonnull Consumer<? super I> consumer) {
        if (input != null) { consumer.consume(input); }
    }
    
    /* -------------------------------------------------- Predicate -------------------------------------------------- */
    
    /**
     * Evaluates the given predicate for the given input if it is not null or returns the given default output otherwise.
     */
    @Pure
    public static <I> boolean ifNotNull(@NonCaptured @Unmodified @Nullable I input, @Nonnull Predicate<? super I> predicate, boolean defaultOutput) {
        return input != null ? predicate.evaluate(input) : defaultOutput;
    }
    
    /**
     * Evaluates the given predicate for the given input if it is not null or returns false otherwise.
     */
    @Pure
    public static <I> boolean ifNotNull(@NonCaptured @Unmodified @Nullable I input, @Nonnull Predicate<? super I> predicate) {
        return ifNotNull(input, predicate, false);
    }
    
    /* -------------------------------------------------- Unary Function -------------------------------------------------- */
    
    /**
     * Evaluates the given function for the given input if it is not null or returns the given default output otherwise.
     */
    @Pure
    public static <I, O> O ifNotNull(@NonCaptured @Unmodified @Nullable I input, @Nonnull UnaryFunction<? super I, ? extends O> function, @NonCaptured @Unmodified O defaultOutput) {
        return input != null ? function.evaluate(input) : defaultOutput;
    }
    
    /**
     * Evaluates the given function for the given input if it is not null or propagates null otherwise.
     */
    @Pure
    public static <I, O> O ifNotNull(@NonCaptured @Unmodified @Nullable I input, @Nonnull UnaryFunction<? super I, ? extends O> function) {
        return ifNotNull(input, function, null);
    }
    
    /* -------------------------------------------------- Binary Function -------------------------------------------------- */
    
    /**
     * Evaluates the given function for the given inputs if the first input is not null or returns the given default output otherwise.
     */
    @Pure
    public static <I0, I1, O> O ifFirstNotNull(@NonCaptured @Unmodified @Nullable I0 input0, @NonCaptured @Unmodified I1 input1, @Nonnull BinaryFunction<? super I0, ? super I1, ? extends O> function, @NonCaptured @Unmodified O defaultOutput) {
        return input0 != null ? function.evaluate(input0, input1) : defaultOutput;
    }
    
    /**
     * Evaluates the given function for the given inputs if the first input is not null or propagates null otherwise.
     */
    @Pure
    public static <I0, I1, O> O ifFirstNotNull(@NonCaptured @Unmodified @Nullable I0 input0, @NonCaptured @Unmodified I1 input1, @Nonnull BinaryFunction<? super I0, ? super I1, ? extends O> function) {
        return ifFirstNotNull(input0, input1, function, null);
    }
    
    /**
     * Evaluates the given function for the given inputs if both inputs are not null or returns the given default output otherwise.
     */
    @Pure
    public static <I0, I1, O> O ifBothNotNull(@NonCaptured @Unmodified @Nullable I0 input0, @NonCaptured @Unmodified @Nullable I1 input1, @Nonnull BinaryFunction<? super I0, ? super I1, ? extends O> function, @NonCaptured @Unmodified O defaultOutput) {
        return input0 != null && input1 != null ? function.evaluate(input0, input1) : defaultOutput;
    }
    
    /**
     * Evaluates the given function for the given inputs if both inputs are not null or propagates null otherwise.
     */
    @Pure
    public static <I0, I1, O> O ifBothNotNull(@NonCaptured @Unmodified @Nullable I0 input0, @NonCaptured @Unmodified @Nullable I1 input1, @Nonnull BinaryFunction<? super I0, ? super I1, ? extends O> function) {
        return ifBothNotNull(input0, input1, function, null);
    }
    
}
