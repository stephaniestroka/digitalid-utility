package net.digitalid.utility.functional.failable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.generics.Unspecifiable;
import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.functional.interfaces.Consumer;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This functional interface models a failable method that consumes objects of type {@code TYPE} without returning a result.
 */
@Mutable
@Functional
public interface FailableConsumer<@Specifiable TYPE, @Unspecifiable EXCEPTION extends Exception> {
    
    /* -------------------------------------------------- Consumption -------------------------------------------------- */
    
    /**
     * Consumes the given object.
     */
    @Impure
    public void consume(@Captured TYPE object) throws EXCEPTION;
    
    /* -------------------------------------------------- Suppression -------------------------------------------------- */
    
    /**
     * Returns a consumer that suppresses the exceptions of this consumer and passes them to the given exception handler instead.
     */
    @Pure
    public default @Capturable @Nonnull Consumer<TYPE> suppressExceptions(@Captured @Nonnull Consumer<@Nonnull ? super Exception> handler) {
        return object -> {
            try {
                consume(object);
            } catch (@Nonnull Exception exception) {
                handler.consume(exception);
            }
        };
    }
    
    /**
     * Returns a consumer that suppresses the exceptions of this consumer.
     */
    @Pure
    public default @Capturable @Nonnull Consumer<TYPE> suppressExceptions() {
        return suppressExceptions(Consumer.DO_NOTHING);
    }
    
    /* -------------------------------------------------- Composition -------------------------------------------------- */
    
    /**
     * Returns the composition of the given consumers with a flexible exception type.
     */
    @Pure
    public static @Capturable <@Specifiable TYPE, @Unspecifiable EXCEPTION extends Exception> @Nonnull FailableConsumer<TYPE, EXCEPTION> compose(@Captured @Nonnull FailableConsumer<? super TYPE, ? extends EXCEPTION> consumer0, @Captured @Nonnull FailableConsumer<? super TYPE, ? extends EXCEPTION> consumer1) {
        return object -> { consumer0.consume(object); consumer1.consume(object); };
    }
    
    /**
     * Returns the composition of this consumer followed by the given consumer.
     * Unfortunately, it is not possible to make the exception type flexible as well.
     * 
     * @see #compose(net.digitalid.utility.functional.failable.FailableConsumer, net.digitalid.utility.functional.failable.FailableConsumer)
     */
    @Pure
    public default @Capturable <@Specifiable SUBTYPE extends TYPE> @Nonnull FailableConsumer<SUBTYPE, EXCEPTION> before(@Captured @Nonnull FailableConsumer<? super SUBTYPE, ? extends EXCEPTION> consumer) {
        return object -> { consume(object); consumer.consume(object); };
    }
    
    /**
     * Returns the composition of the given consumer followed by this consumer.
     * Unfortunately, it is not possible to make the exception type flexible as well.
     * 
     * @see #compose(net.digitalid.utility.functional.failable.FailableConsumer, net.digitalid.utility.functional.failable.FailableConsumer)
     */
    @Pure
    public default @Capturable <@Specifiable SUBTYPE extends TYPE> @Nonnull FailableConsumer<SUBTYPE, EXCEPTION> after(@Captured @Nonnull FailableConsumer<? super SUBTYPE, ? extends EXCEPTION> consumer) {
        return object -> { consumer.consume(object); consume(object); };
    }
    
    /**
     * Returns the composition of the given function followed by this consumer.
     * Unfortunately, it is not possible to make the exception type flexible as well.
     */
    @Pure
    public default @Capturable <@Specifiable INPUT> @Nonnull FailableConsumer<INPUT, EXCEPTION> after(@Nonnull FailableUnaryFunction<? super INPUT, ? extends TYPE, ? extends EXCEPTION> function) {
        return object -> consume(function.evaluate(object));
    }
    
    /* -------------------------------------------------- Conversion -------------------------------------------------- */
    
    /**
     * Returns this consumer as a unary function that always returns null.
     * This method may only be called if this consumer is side-effect-free.
     */
    @Pure
    @SuppressWarnings("null")
    public default @Nonnull FailableUnaryFunction<TYPE, @Nullable Void, EXCEPTION> asFunction() {
        return object -> { consume(object); return null; };
    }
    
    /* -------------------------------------------------- Synchronization -------------------------------------------------- */
    
    /**
     * Returns a consumer that synchronizes on this consumer.
     */
    @Pure
    public default @Nonnull FailableConsumer<TYPE, EXCEPTION> synchronize() {
        return object -> {
            synchronized (this) {
                consume(object);
            }
        };
    }
    
}
