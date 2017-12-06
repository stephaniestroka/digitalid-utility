/*
 * Copyright (C) 2017 Synacts GmbH, Switzerland (info@synacts.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
 * This functional interface models a failable method that consumes objects of type {@code INPUT} without returning a result.
 */
@Mutable
@Functional
public interface FailableConsumer<@Specifiable INPUT, @Unspecifiable EXCEPTION extends Exception> {
    
    /* -------------------------------------------------- Consumption -------------------------------------------------- */
    
    /**
     * Consumes the given input.
     */
    @Impure
    public void consume(@Captured INPUT input) throws EXCEPTION;
    
    /* -------------------------------------------------- Suppression -------------------------------------------------- */
    
    /**
     * Returns a consumer that suppresses the exceptions of this consumer and passes them to the given exception handler instead.
     */
    @Pure
    public default @Capturable @Nonnull Consumer<INPUT> suppressExceptions(@Captured @Nonnull Consumer<@Nonnull ? super Exception> handler) {
        return input -> {
            try {
                consume(input);
            } catch (@Nonnull Exception exception) {
                handler.consume(exception);
            }
        };
    }
    
    /**
     * Returns a consumer that suppresses the exceptions of this consumer.
     */
    @Pure
    public default @Capturable @Nonnull Consumer<INPUT> suppressExceptions() {
        return suppressExceptions(Consumer.DO_NOTHING);
    }
    
    /* -------------------------------------------------- Composition -------------------------------------------------- */
    
    /**
     * Returns the composition of the given consumers with a flexible exception type.
     */
    @Pure
    public static @Capturable <@Specifiable INPUT, @Unspecifiable EXCEPTION extends Exception> @Nonnull FailableConsumer<INPUT, EXCEPTION> compose(@Captured @Nonnull FailableConsumer<? super INPUT, ? extends EXCEPTION> consumer0, @Captured @Nonnull FailableConsumer<? super INPUT, ? extends EXCEPTION> consumer1) {
        return input -> { consumer0.consume(input); consumer1.consume(input); };
    }
    
    /**
     * Returns the composition of this consumer followed by the given consumer.
     * Unfortunately, it is not possible to make the exception type flexible as well.
     * 
     * @see #compose(net.digitalid.utility.functional.failable.FailableConsumer, net.digitalid.utility.functional.failable.FailableConsumer)
     */
    @Pure
    public default @Capturable <@Specifiable SUBTYPE extends INPUT> @Nonnull FailableConsumer<SUBTYPE, EXCEPTION> before(@Captured @Nonnull FailableConsumer<? super SUBTYPE, ? extends EXCEPTION> consumer) {
        return input -> { consume(input); consumer.consume(input); };
    }
    
    /**
     * Returns the composition of the given consumer followed by this consumer.
     * Unfortunately, it is not possible to make the exception type flexible as well.
     * 
     * @see #compose(net.digitalid.utility.functional.failable.FailableConsumer, net.digitalid.utility.functional.failable.FailableConsumer)
     */
    @Pure
    public default @Capturable <@Specifiable SUBTYPE extends INPUT> @Nonnull FailableConsumer<SUBTYPE, EXCEPTION> after(@Captured @Nonnull FailableConsumer<? super SUBTYPE, ? extends EXCEPTION> consumer) {
        return input -> { consumer.consume(input); consume(input); };
    }
    
    /**
     * Returns the composition of the given function followed by this consumer.
     * Unfortunately, it is not possible to make the exception type flexible as well.
     */
    @Pure
    public default @Capturable <@Specifiable INITIAL_INPUT> @Nonnull FailableConsumer<INITIAL_INPUT, EXCEPTION> after(@Nonnull FailableUnaryFunction<? super INITIAL_INPUT, ? extends INPUT, ? extends EXCEPTION> function) {
        return input -> consume(function.evaluate(input));
    }
    
    /* -------------------------------------------------- Conversion -------------------------------------------------- */
    
    /**
     * Returns this consumer as a unary function that always returns null.
     * This method may only be called if this consumer is side-effect-free.
     */
    @Pure
    @SuppressWarnings("null")
    public default @Nonnull FailableUnaryFunction<INPUT, @Nullable Void, EXCEPTION> asFunction() {
        return input -> { consume(input); return null; };
    }
    
    /* -------------------------------------------------- Synchronization -------------------------------------------------- */
    
    /**
     * Returns a consumer that synchronizes on this consumer.
     */
    @Pure
    public default @Nonnull FailableConsumer<INPUT, EXCEPTION> synchronize() {
        return input -> {
            synchronized (this) {
                consume(input);
            }
        };
    }
    
}
