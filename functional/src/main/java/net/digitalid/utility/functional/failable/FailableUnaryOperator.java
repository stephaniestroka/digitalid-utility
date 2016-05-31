package net.digitalid.utility.functional.failable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.functional.interfaces.Consumer;
import net.digitalid.utility.functional.interfaces.UnaryOperator;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This functional interface models a failable unary operator that maps an object of type {@code T} to another object of type {@code T}.
 */
@Immutable
@Functional
public interface FailableUnaryOperator<T, X extends Exception> extends FailableUnaryFunction<T, T, X> {
    
    /* -------------------------------------------------- Suppression -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull UnaryOperator<T> suppressExceptions(@Captured @Nonnull Consumer<@Nonnull ? super Exception> handler, @Captured T defaultOutput) {
        return object -> {
            try {
                return evaluate(object);
            } catch (@Nonnull Exception exception) {
                handler.consume(exception);
                return defaultOutput;
            }
        };
    }
    
    @Pure
    @Override
    public default @Nonnull UnaryOperator<@Nullable T> suppressExceptions(@Captured @Nonnull Consumer<@Nonnull ? super Exception> handler) {
        return suppressExceptions(handler, null);
    }
    
    @Pure
    @Override
    public default @Nonnull UnaryOperator<T> suppressExceptions(@Captured T defaultOutput) {
        return suppressExceptions(Consumer.DO_NOTHING, defaultOutput);
    }
    
    @Pure
    @Override
    public default @Nonnull UnaryOperator<@Nullable T> suppressExceptions() {
        return suppressExceptions(Consumer.DO_NOTHING, null);
    }
    
    /* -------------------------------------------------- Composition -------------------------------------------------- */
    
    /**
     * Returns the composition of the given operators with a flexible exception type.
     */
    @Pure
    public static <T, X extends Exception> @Nonnull FailableUnaryOperator<T, X> compose(@Nonnull FailableUnaryOperator<T, ? extends X> operator0, @Nonnull FailableUnaryOperator<T, ? extends X> operator1) {
        return object -> operator1.evaluate(operator0.evaluate(object));
    }
    
    /**
     * Returns the composition of this operator followed by the given operator.
     * Unfortunately, it is not possible to make the exception type flexible.
     * 
     * @see #compose(net.digitalid.utility.functional.failable.FailableUnaryOperator, net.digitalid.utility.functional.failable.FailableUnaryOperator)
     */
    @Pure
    public default @Nonnull FailableUnaryOperator<T, X> before(@Nonnull FailableUnaryOperator<T, ? extends X> operator) {
        return object -> operator.evaluate(evaluate(object));
    }
    
    /**
     * Returns the composition of the given operator followed by this operator.
     * Unfortunately, it is not possible to make the exception type flexible.
     * 
     * @see #compose(net.digitalid.utility.functional.failable.FailableUnaryOperator, net.digitalid.utility.functional.failable.FailableUnaryOperator)
     */
    @Pure
    public default @Nonnull FailableUnaryOperator<T, X> after(@Nonnull FailableUnaryOperator<T, ? extends X> operator) {
        return object -> evaluate(operator.evaluate(object));
    }
    
    /* -------------------------------------------------- Null Handling -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull FailableUnaryOperator<@Nullable T, X> replaceNull(@Captured T defaultOutput) {
        return object -> object != null ? evaluate(object) : defaultOutput;
    }
    
    @Pure
    @Override
    public default @Nonnull FailableUnaryOperator<@Nullable T, X> propagateNull() {
        return replaceNull(null);
    }
    
}
