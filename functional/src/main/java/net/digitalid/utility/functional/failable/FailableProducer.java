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
import net.digitalid.utility.functional.interfaces.Producer;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This functional interface models a failable method that produces objects of type {@code OUTPUT} without requiring a parameter.
 */
@Mutable
@Functional
public interface FailableProducer<@Specifiable OUTPUT, @Unspecifiable EXCEPTION extends Exception> {
    
    /* -------------------------------------------------- Production -------------------------------------------------- */
    
    /**
     * Produces a result.
     */
    @Impure
    public @Capturable OUTPUT produce() throws EXCEPTION;
    
    /* -------------------------------------------------- Suppression -------------------------------------------------- */
    
    /**
     * Returns a producer that catches the exceptions of this producer, passes them to the given exception handler and returns the given default output instead.
     */
    @Pure
    public default @Nonnull Producer<OUTPUT> suppressExceptions(@Captured @Nonnull Consumer<@Nonnull ? super Exception> handler, @Captured OUTPUT defaultOutput) {
        return () -> {
            try {
                return produce();
            } catch (@Nonnull Exception exception) {
                handler.consume(exception);
                return defaultOutput;
            }
        };
    }
    
    /**
     * Returns a producer that catches the exceptions of this producer, passes them to the given exception handler and returns null instead.
     */
    @Pure
    public default @Nonnull Producer<@Nullable OUTPUT> suppressExceptions(@Captured @Nonnull Consumer<@Nonnull ? super Exception> handler) {
        return suppressExceptions(handler, null);
    }
    
    /**
     * Returns a producer that suppresses the exceptions of this producer and returns the given default output instead.
     */
    @Pure
    public default @Nonnull Producer<OUTPUT> suppressExceptions(@Captured OUTPUT defaultOutput) {
        return suppressExceptions(Consumer.DO_NOTHING, defaultOutput);
    }
    
    /**
     * Returns a producer that suppresses the exceptions of this producer and returns null instead.
     */
    @Pure
    public default @Nonnull Producer<@Nullable OUTPUT> suppressExceptions() {
        return suppressExceptions(Consumer.DO_NOTHING, null);
    }
    
    /* -------------------------------------------------- Composition -------------------------------------------------- */
    
    /**
     * Returns the composition of the given producer and function with a flexible exception type.
     */
    @Pure
    public static @Capturable <@Specifiable INTERMEDIATE, @Specifiable OUTPUT, @Unspecifiable EXCEPTION extends Exception> @Nonnull FailableProducer<OUTPUT, EXCEPTION> compose(@Captured @Nonnull FailableProducer<? extends INTERMEDIATE, ? extends EXCEPTION> producer, @Nonnull FailableUnaryFunction<? super INTERMEDIATE, ? extends OUTPUT, ? extends EXCEPTION> function) {
        return () -> function.evaluate(producer.produce());
    }
    
    /**
     * Returns the composition of this producer followed by the given function.
     * Unfortunately, it is not possible to make the exception type flexible.
     * 
     * @see #compose(net.digitalid.utility.functional.failable.FailableProducer, net.digitalid.utility.functional.failable.FailableUnaryFunction)
     */
    @Pure
    public default <@Specifiable FINAL_OUTPUT> @Nonnull FailableProducer<FINAL_OUTPUT, EXCEPTION> before(@Nonnull FailableUnaryFunction<? super OUTPUT, ? extends FINAL_OUTPUT, ? extends EXCEPTION> function) {
        return () -> function.evaluate(produce());
    }
    
    /* -------------------------------------------------- Conversion -------------------------------------------------- */
    
    /**
     * Returns this producer as a unary function that ignores its input.
     * This method may only be called if this producer is side-effect-free.
     */
    @Pure
    public default @Nonnull FailableUnaryFunction<@Nullable Object, OUTPUT, EXCEPTION> asFunction() {
        return input -> produce();
    }
    
    /* -------------------------------------------------- Synchronization -------------------------------------------------- */
    
    /**
     * Returns a producer that synchronizes on this producer.
     */
    @Pure
    public default @Nonnull FailableProducer<OUTPUT, EXCEPTION> synchronize() {
        return () -> {
            synchronized (this) {
                return produce();
            }
        };
    }
    
    /* -------------------------------------------------- Memoization -------------------------------------------------- */
    
    /**
     * Returns a producer that caches each object produced by this producer for the given duration in milliseconds.
     */
    @Pure
    public default @Nonnull FailableProducer<OUTPUT, EXCEPTION> memoize(long duration) {
        return new FailableProducer<OUTPUT, EXCEPTION>() {
            
            private OUTPUT cachedOutput = null;
            
            private long lastProduction = 0;
            
            @Impure
            @Override
            public OUTPUT produce() throws EXCEPTION {
                final long currentTime = System.currentTimeMillis();
                if (lastProduction == 0 || lastProduction + duration < currentTime) {
                    this.cachedOutput = FailableProducer.this.produce();
                    this.lastProduction = currentTime;
                }
                return cachedOutput;
            }
            
        };
    }
    
}
