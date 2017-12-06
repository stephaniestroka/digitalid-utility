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
import net.digitalid.utility.functional.interfaces.BinaryOperator;
import net.digitalid.utility.functional.interfaces.Consumer;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This functional interface models a failable binary operator that maps two objects of type {@code TYPE} to another object of type {@code TYPE}.
 */
@Immutable
@Functional
public interface FailableBinaryOperator<@Specifiable TYPE, @Unspecifiable EXCEPTION extends Exception> extends FailableBinaryFunction<TYPE, TYPE, TYPE, EXCEPTION> {
    
    /* -------------------------------------------------- Suppression -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull BinaryOperator<TYPE> suppressExceptions(@Captured @Nonnull Consumer<@Nonnull ? super Exception> handler, @Captured TYPE defaultOutput) {
        return (input0, input1) -> {
            try {
                return evaluate(input0, input1);
            } catch (@Nonnull Exception exception) {
                handler.consume(exception);
                return defaultOutput;
            }
        };
    }
    
    @Pure
    @Override
    public default @Nonnull BinaryOperator<@Nullable TYPE> suppressExceptions(@Captured @Nonnull Consumer<@Nonnull ? super Exception> handler) {
        return suppressExceptions(handler, null);
    }
    
    @Pure
    @Override
    public default @Nonnull BinaryOperator<TYPE> suppressExceptions(@Captured TYPE defaultOutput) {
        return suppressExceptions(Consumer.DO_NOTHING, defaultOutput);
    }
    
    @Pure
    @Override
    public default @Nonnull BinaryOperator<@Nullable TYPE> suppressExceptions() {
        return suppressExceptions(Consumer.DO_NOTHING, null);
    }
    
    /* -------------------------------------------------- Composition -------------------------------------------------- */
    
    /**
     * Returns the composition of the given operators with a flexible exception type.
     */
    @Pure
    public static <@Specifiable TYPE, @Unspecifiable EXCEPTION extends Exception> @Nonnull FailableBinaryOperator<TYPE, EXCEPTION> compose(@Nonnull FailableBinaryOperator<TYPE, ? extends EXCEPTION> binaryOperator, @Nonnull FailableUnaryOperator<TYPE, ? extends EXCEPTION> unaryOperator) {
        return (input0, input1) -> unaryOperator.evaluate(binaryOperator.evaluate(input0, input1));
    }
    
    /**
     * Returns the composition of this operator followed by the given operator.
     * Unfortunately, it is not possible to make the exception type flexible.
     * 
     * @see #compose(net.digitalid.utility.functional.failable.FailableBinaryOperator, net.digitalid.utility.functional.failable.FailableUnaryOperator)
     */
    @Pure
    public default @Nonnull FailableBinaryOperator<TYPE, EXCEPTION> before(@Nonnull FailableUnaryOperator<TYPE, ? extends EXCEPTION> operator) {
        return (input0, input1) -> operator.evaluate(evaluate(input0, input1));
    }
    
    /**
     * Returns the composition of the given operators with a flexible exception type.
     */
    @Pure
    public static <@Specifiable TYPE, @Unspecifiable EXCEPTION extends Exception> @Nonnull FailableBinaryOperator<TYPE, EXCEPTION> compose(@Nonnull FailableUnaryOperator<TYPE, ? extends EXCEPTION> unaryOperator0, @Nonnull FailableUnaryOperator<TYPE, ? extends EXCEPTION> unaryOperator1, @Nonnull FailableBinaryOperator<TYPE, ? extends EXCEPTION> binaryOperator) {
        return (input0, input1) -> binaryOperator.evaluate(unaryOperator0.evaluate(input0), unaryOperator1.evaluate(input1));
    }
    
    /**
     * Returns the composition of the given operators followed by this operator.
     * Unfortunately, it is not possible to make the exception type flexible.
     * 
     * @see #compose(net.digitalid.utility.functional.failable.FailableUnaryOperator, net.digitalid.utility.functional.failable.FailableUnaryOperator, net.digitalid.utility.functional.failable.FailableBinaryOperator)
     */
    @Pure
    public default @Nonnull FailableBinaryOperator<TYPE, EXCEPTION> after(@Nonnull FailableUnaryOperator<TYPE, ? extends EXCEPTION> operator0, @Nonnull FailableUnaryOperator<TYPE, ? extends EXCEPTION> operator1) {
        return (input0, input1) -> evaluate(operator0.evaluate(input0), operator1.evaluate(input1));
    }
    
    /* -------------------------------------------------- Null Handling -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull FailableBinaryOperator<@Nullable TYPE, EXCEPTION> replaceNull(@Captured TYPE defaultOutput) {
        return (input0, input1) -> input0 != null && input1 != null ? evaluate(input0, input1) : defaultOutput;
    }
    
    @Pure
    @Override
    public default @Nonnull FailableBinaryOperator<@Nullable TYPE, EXCEPTION> propagateNull() {
        return replaceNull(null);
    }
    
}
