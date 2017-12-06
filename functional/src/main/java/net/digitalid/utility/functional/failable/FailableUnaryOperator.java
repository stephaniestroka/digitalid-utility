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
import net.digitalid.utility.functional.interfaces.Consumer;
import net.digitalid.utility.functional.interfaces.UnaryOperator;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This functional interface models a failable unary operator that maps an object of type {@code TYPE} to another object of type {@code TYPE}.
 */
@Immutable
@Functional
public interface FailableUnaryOperator<@Specifiable TYPE, @Unspecifiable EXCEPTION extends Exception> extends FailableUnaryFunction<TYPE, TYPE, EXCEPTION> {
    
    /* -------------------------------------------------- Suppression -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull UnaryOperator<TYPE> suppressExceptions(@Captured @Nonnull Consumer<@Nonnull ? super Exception> handler, @Captured TYPE defaultOutput) {
        return input -> {
            try {
                return evaluate(input);
            } catch (@Nonnull Exception exception) {
                handler.consume(exception);
                return defaultOutput;
            }
        };
    }
    
    @Pure
    @Override
    public default @Nonnull UnaryOperator<@Nullable TYPE> suppressExceptions(@Captured @Nonnull Consumer<@Nonnull ? super Exception> handler) {
        return suppressExceptions(handler, null);
    }
    
    @Pure
    @Override
    public default @Nonnull UnaryOperator<TYPE> suppressExceptions(@Captured TYPE defaultOutput) {
        return suppressExceptions(Consumer.DO_NOTHING, defaultOutput);
    }
    
    @Pure
    @Override
    public default @Nonnull UnaryOperator<@Nullable TYPE> suppressExceptions() {
        return suppressExceptions(Consumer.DO_NOTHING, null);
    }
    
    /* -------------------------------------------------- Composition -------------------------------------------------- */
    
    /**
     * Returns the composition of the given operators with a flexible exception type.
     */
    @Pure
    public static <@Specifiable TYPE, @Unspecifiable EXCEPTION extends Exception> @Nonnull FailableUnaryOperator<TYPE, EXCEPTION> compose(@Nonnull FailableUnaryOperator<TYPE, ? extends EXCEPTION> operator0, @Nonnull FailableUnaryOperator<TYPE, ? extends EXCEPTION> operator1) {
        return input -> operator1.evaluate(operator0.evaluate(input));
    }
    
    /**
     * Returns the composition of this operator followed by the given operator.
     * Unfortunately, it is not possible to make the exception type flexible.
     * 
     * @see #compose(net.digitalid.utility.functional.failable.FailableUnaryOperator, net.digitalid.utility.functional.failable.FailableUnaryOperator)
     */
    @Pure
    public default @Nonnull FailableUnaryOperator<TYPE, EXCEPTION> before(@Nonnull FailableUnaryOperator<TYPE, ? extends EXCEPTION> operator) {
        return input -> operator.evaluate(evaluate(input));
    }
    
    /**
     * Returns the composition of the given operator followed by this operator.
     * Unfortunately, it is not possible to make the exception type flexible.
     * 
     * @see #compose(net.digitalid.utility.functional.failable.FailableUnaryOperator, net.digitalid.utility.functional.failable.FailableUnaryOperator)
     */
    @Pure
    public default @Nonnull FailableUnaryOperator<TYPE, EXCEPTION> after(@Nonnull FailableUnaryOperator<TYPE, ? extends EXCEPTION> operator) {
        return input -> evaluate(operator.evaluate(input));
    }
    
    /* -------------------------------------------------- Null Handling -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull FailableUnaryOperator<@Nullable TYPE, EXCEPTION> replaceNull(@Captured TYPE defaultOutput) {
        return input -> input != null ? evaluate(input) : defaultOutput;
    }
    
    @Pure
    @Override
    public default @Nonnull FailableUnaryOperator<@Nullable TYPE, EXCEPTION> propagateNull() {
        return replaceNull(null);
    }
    
}
