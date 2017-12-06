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
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.functional.interfaces.Consumer;
import net.digitalid.utility.functional.interfaces.UnaryFunction;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This functional interface models a failable unary function that maps an object of type {@code INPUT} to an object of type {@code OUTPUT}.
 */
@Immutable
@Functional
public interface FailableUnaryFunction<@Specifiable INPUT, @Specifiable OUTPUT, @Unspecifiable EXCEPTION extends Exception> {
    
    /* -------------------------------------------------- Evaluation -------------------------------------------------- */
    
    /**
     * Evaluates this function for the given object.
     * All implementations of this method have to be side-effect-free.
     */
    @Pure
    public OUTPUT evaluate(@NonCaptured @Unmodified INPUT input) throws EXCEPTION;
    
    /* -------------------------------------------------- Suppression -------------------------------------------------- */
    
    /**
     * Returns a function that catches the exceptions of this function, passes them to the given exception handler and returns the given default output instead.
     */
    @Pure
    public default @Nonnull UnaryFunction<INPUT, OUTPUT> suppressExceptions(@Captured @Nonnull Consumer<@Nonnull ? super Exception> handler, @Captured OUTPUT defaultOutput) {
        return input -> {
            try {
                return evaluate(input);
            } catch (@Nonnull Exception exception) {
                handler.consume(exception);
                return defaultOutput;
            }
        };
    }
    
    /**
     * Returns a function that catches the exceptions of this function, passes them to the given exception handler and returns null instead.
     */
    @Pure
    public default @Nonnull UnaryFunction<INPUT, @Nullable OUTPUT> suppressExceptions(@Captured @Nonnull Consumer<@Nonnull ? super Exception> handler) {
        return suppressExceptions(handler, null);
    }
    
    /**
     * Returns a function that suppresses the exceptions of this function and returns the given default output instead.
     */
    @Pure
    public default @Nonnull UnaryFunction<INPUT, OUTPUT> suppressExceptions(@Captured OUTPUT defaultOutput) {
        return suppressExceptions(Consumer.DO_NOTHING, defaultOutput);
    }
    
    /**
     * Returns a function that suppresses the exceptions of this function and returns null instead.
     */
    @Pure
    public default @Nonnull UnaryFunction<INPUT, @Nullable OUTPUT> suppressExceptions() {
        return suppressExceptions(Consumer.DO_NOTHING, null);
    }
    
    /* -------------------------------------------------- Composition -------------------------------------------------- */
    
    /**
     * Returns the composition of the given functions with a flexible exception type.
     */
    @Pure
    public static <@Specifiable INPUT, @Specifiable INTERMEDIATE, @Specifiable OUTPUT, @Unspecifiable EXCEPTION extends Exception> @Nonnull FailableUnaryFunction<INPUT, OUTPUT, EXCEPTION> compose(@Nonnull FailableUnaryFunction<? super INPUT, ? extends INTERMEDIATE, ? extends EXCEPTION> function0, @Nonnull FailableUnaryFunction<? super INTERMEDIATE, ? extends OUTPUT, ? extends EXCEPTION> function1) {
        return input -> function1.evaluate(function0.evaluate(input));
    }
    
    /**
     * Returns the composition of this function followed by the given function.
     * Unfortunately, it is not possible to make the exception type flexible.
     * 
     * @see #compose(net.digitalid.utility.functional.failable.FailableUnaryFunction, net.digitalid.utility.functional.failable.FailableUnaryFunction)
     */
    @Pure
    public default <@Specifiable FINAL_OUTPUT> @Nonnull FailableUnaryFunction<INPUT, FINAL_OUTPUT, EXCEPTION> before(@Nonnull FailableUnaryFunction<? super OUTPUT, ? extends FINAL_OUTPUT, ? extends EXCEPTION> function) {
        return input -> function.evaluate(evaluate(input));
    }
    
    /**
     * Returns the composition of the given function followed by this function.
     * Unfortunately, it is not possible to make the exception type flexible.
     * 
     * @see #compose(net.digitalid.utility.functional.failable.FailableUnaryFunction, net.digitalid.utility.functional.failable.FailableUnaryFunction)
     */
    @Pure
    public default <@Specifiable INITIAL_INPUT> @Nonnull FailableUnaryFunction<INITIAL_INPUT, OUTPUT, EXCEPTION> after(@Nonnull FailableUnaryFunction<? super INITIAL_INPUT, ? extends INPUT, ? extends EXCEPTION> function) {
        return input -> evaluate(function.evaluate(input));
    }
    
    /* -------------------------------------------------- Null Handling -------------------------------------------------- */
    
    /**
     * Returns a new function based on this function that returns the given default output for null.
     */
    @Pure
    public default @Nonnull FailableUnaryFunction<@Nullable INPUT, OUTPUT, EXCEPTION> replaceNull(@Captured OUTPUT defaultOutput) {
        return input -> input != null ? evaluate(input) : defaultOutput;
    }
    
    /**
     * Returns a new function based on this function that propagates null instead of evaluating it.
     */
    @Pure
    public default @Nonnull FailableUnaryFunction<@Nullable INPUT, @Nullable OUTPUT, EXCEPTION> propagateNull() {
        return replaceNull(null);
    }
    
}
