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
package net.digitalid.utility.functional.interfaces;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.functional.failable.FailableProducer;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This functional interface models a method that produces objects of type {@code OUTPUT} without requiring a parameter.
 */
@Mutable
@Functional
public interface Producer<@Specifiable OUTPUT> extends FailableProducer<OUTPUT, RuntimeException> {
    
    /* -------------------------------------------------- Composition -------------------------------------------------- */
    
    /**
     * Returns the composition of this producer followed by the given function.
     */
    @Pure
    public default <@Specifiable FINAL_OUTPUT> @Nonnull Producer<FINAL_OUTPUT> before(@Nonnull UnaryFunction<? super OUTPUT, ? extends FINAL_OUTPUT> function) {
        return () -> function.evaluate(produce());
    }
    
    /* -------------------------------------------------- Conversion -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull UnaryFunction<@Nullable Object, OUTPUT> asFunction() {
        return input -> produce();
    }
    
    /* -------------------------------------------------- Synchronization -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull Producer<OUTPUT> synchronize() {
        return () -> {
            synchronized (this) {
                return produce();
            }
        };
    }
    
    /* -------------------------------------------------- Memoization -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull Producer<OUTPUT> memoize(long duration) {
        return new Producer<OUTPUT>() {
            
            private OUTPUT cachedOutput = null;
            
            private long lastProduction = 0;
            
            @Impure
            @Override
            public OUTPUT produce() {
                final long currentTime = System.currentTimeMillis();
                if (lastProduction == 0 || lastProduction + duration < currentTime) {
                    this.cachedOutput = Producer.this.produce();
                    this.lastProduction = currentTime;
                }
                return cachedOutput;
            }
            
        };
    }
    
    /* -------------------------------------------------- Constant -------------------------------------------------- */
    
    /**
     * Returns a producer that always produces the given output.
     */
    @Pure
    public static <@Specifiable OUTPUT> @Nonnull Producer<OUTPUT> constant(@Captured OUTPUT output) {
        return () -> output;
    }
    
}
