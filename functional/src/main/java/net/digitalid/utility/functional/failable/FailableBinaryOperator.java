package net.digitalid.utility.functional.failable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.functional.interfaces.BinaryOperator;
import net.digitalid.utility.functional.interfaces.Consumer;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This functional interface models a failable binary operator that maps two objects of type {@code T} to another object of type {@code T}.
 */
@Immutable
@Functional
public interface FailableBinaryOperator<T, X extends Exception> extends FailableBinaryFunction<T, T, T, X> {
    
    /* -------------------------------------------------- Suppression -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull BinaryOperator<T> suppressExceptions(@Captured @Nonnull Consumer<@Nonnull ? super Exception> handler, @Captured T defaultOutput) {
        return (object0, object1) -> {
            try {
                return evaluate(object0, object1);
            } catch (@Nonnull Exception exception) {
                handler.consume(exception);
                return defaultOutput;
            }
        };
    }
    
    @Pure
    @Override
    public default @Nonnull BinaryOperator<@Nullable T> suppressExceptions(@Captured @Nonnull Consumer<@Nonnull ? super Exception> handler) {
        return suppressExceptions(handler, null);
    }
    
    @Pure
    @Override
    public default @Nonnull BinaryOperator<T> suppressExceptions(@Captured T defaultOutput) {
        return suppressExceptions(Consumer.DO_NOTHING, defaultOutput);
    }
    
    @Pure
    @Override
    public default @Nonnull BinaryOperator<@Nullable T> suppressExceptions() {
        return suppressExceptions(Consumer.DO_NOTHING, null);
    }
    
    /* -------------------------------------------------- Composition -------------------------------------------------- */
    
    /**
     * Returns the composition of the given operators with a flexible exception type.
     */
    @Pure
    public static <T, X extends Exception> @Nonnull FailableBinaryOperator<T, X> compose(@Nonnull FailableBinaryOperator<T, ? extends X> binaryOperator, @Nonnull FailableUnaryOperator<T, ? extends X> unaryOperator) {
        return (object0, object1) -> unaryOperator.evaluate(binaryOperator.evaluate(object0, object1));
    }
    
    /**
     * Returns the composition of this operator followed by the given operator.
     * Unfortunately, it is not possible to make the exception type flexible.
     * 
     * @see #compose(net.digitalid.utility.functional.failable.FailableBinaryOperator, net.digitalid.utility.functional.failable.FailableUnaryOperator)
     */
    @Pure
    public default @Nonnull FailableBinaryOperator<T, X> before(@Nonnull FailableUnaryOperator<T, ? extends X> operator) {
        return (object0, object1) -> operator.evaluate(evaluate(object0, object1));
    }
    
    /**
     * Returns the composition of the given operators with a flexible exception type.
     */
    @Pure
    public static <T, X extends Exception> @Nonnull FailableBinaryOperator<T, X> compose(@Nonnull FailableUnaryOperator<T, ? extends X> unaryOperator0, @Nonnull FailableUnaryOperator<T, ? extends X> unaryOperator1, @Nonnull FailableBinaryOperator<T, ? extends X> binaryOperator) {
        return (object0, object1) -> binaryOperator.evaluate(unaryOperator0.evaluate(object0), unaryOperator1.evaluate(object1));
    }
    
    /**
     * Returns the composition of the given operators followed by this operator.
     * Unfortunately, it is not possible to make the exception type flexible.
     * 
     * @see #compose(net.digitalid.utility.functional.failable.FailableUnaryOperator, net.digitalid.utility.functional.failable.FailableUnaryOperator, net.digitalid.utility.functional.failable.FailableBinaryOperator)
     */
    @Pure
    public default @Nonnull FailableBinaryOperator<T, X> after(@Nonnull FailableUnaryOperator<T, ? extends X> operator0, @Nonnull FailableUnaryOperator<T, ? extends X> operator1) {
        return (object0, object1) -> evaluate(operator0.evaluate(object0), operator1.evaluate(object1));
    }
    
    /* -------------------------------------------------- Null Handling -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull FailableBinaryOperator<@Nullable T, X> replaceNull(@Captured T defaultOutput) {
        return (object0, object1) -> object0 != null && object1 != null ? evaluate(object0, object1) : defaultOutput;
    }
    
    @Pure
    @Override
    public default @Nonnull FailableBinaryOperator<@Nullable T, X> propagateNull() {
        return replaceNull(null);
    }
    
}
