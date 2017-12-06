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
package net.digitalid.utility.functional.nullable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.generics.Unspecifiable;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.functional.failable.FailableBinaryFunction;
import net.digitalid.utility.functional.failable.FailableConsumer;
import net.digitalid.utility.functional.failable.FailablePredicate;
import net.digitalid.utility.functional.failable.FailableUnaryFunction;
import net.digitalid.utility.validation.annotations.type.Utility;

/**
 * This utility class allows to evaluate functional interfaces only in case their input is not null.
 * 
 * @see Get
 */
@Utility
public class Evaluate {
    
    /* -------------------------------------------------- Consumer -------------------------------------------------- */
    
    /**
     * Lets the given consumer consume the given input if it is not null.
     */
    @Pure
    public static <@Unspecifiable INPUT, @Unspecifiable EXCEPTION extends Exception> void consumerIfNotNull(@Captured @Nullable INPUT input, @Nonnull FailableConsumer<? super INPUT, ? extends EXCEPTION> consumer) throws EXCEPTION {
        if (input != null) { consumer.consume(input); }
    }
    
    /* -------------------------------------------------- Predicate -------------------------------------------------- */
    
    /**
     * Evaluates the given predicate for the given input if it is not null or returns the given default output otherwise.
     */
    @Pure
    public static <@Unspecifiable INPUT, @Unspecifiable EXCEPTION extends Exception> boolean predicateIfNotNull(@NonCaptured @Unmodified @Nullable INPUT input, @Nonnull FailablePredicate<? super INPUT, ? extends EXCEPTION> predicate, boolean defaultOutput) throws EXCEPTION {
        return input != null ? predicate.evaluate(input) : defaultOutput;
    }
    
    /**
     * Evaluates the given predicate for the given input if it is not null or returns false otherwise.
     */
    @Pure
    public static <@Unspecifiable INPUT, @Unspecifiable EXCEPTION extends Exception> boolean predicateIfNotNull(@NonCaptured @Unmodified @Nullable INPUT input, @Nonnull FailablePredicate<? super INPUT, ? extends EXCEPTION> predicate) throws EXCEPTION {
        return Evaluate.predicateIfNotNull(input, predicate, false);
    }
    
    /* -------------------------------------------------- Unary Function -------------------------------------------------- */
    
    /**
     * Evaluates the given function for the given input if it is not null or returns the given default output otherwise.
     */
    @Pure
    public static <@Unspecifiable INPUT, @Specifiable OUTPUT, @Unspecifiable EXCEPTION extends Exception> OUTPUT functionIfNotNull(@NonCaptured @Unmodified @Nullable INPUT input, @Nonnull FailableUnaryFunction<? super INPUT, ? extends OUTPUT, ? extends EXCEPTION> function, @NonCaptured @Unmodified OUTPUT defaultOutput) throws EXCEPTION {
        return input != null ? function.evaluate(input) : defaultOutput;
    }
    
    /**
     * Evaluates the given function for the given input if it is not null or propagates null otherwise.
     */
    @Pure
    public static <@Unspecifiable INPUT, @Unspecifiable OUTPUT, @Unspecifiable EXCEPTION extends Exception> @Nullable OUTPUT functionIfNotNull(@NonCaptured @Unmodified @Nullable INPUT input, @Nonnull FailableUnaryFunction<? super INPUT, ? extends OUTPUT, ? extends EXCEPTION> function) throws EXCEPTION {
        return Evaluate.functionIfNotNull(input, function, null);
    }
    
    /* -------------------------------------------------- Binary Function -------------------------------------------------- */
    
    /**
     * Evaluates the given function for the given inputs if the first input is not null or returns the given default output otherwise.
     */
    @Pure
    public static <@Unspecifiable INPUT0, @Specifiable INPUT1, @Specifiable OUTPUT, @Unspecifiable EXCEPTION extends Exception> OUTPUT functionIfFirstNotNull(@NonCaptured @Unmodified @Nullable INPUT0 input0, @NonCaptured @Unmodified INPUT1 input1, @Nonnull FailableBinaryFunction<? super INPUT0, ? super INPUT1, ? extends OUTPUT, ? extends EXCEPTION> function, @NonCaptured @Unmodified OUTPUT defaultOutput) throws EXCEPTION {
        return input0 != null ? function.evaluate(input0, input1) : defaultOutput;
    }
    
    /**
     * Evaluates the given function for the given inputs if the first input is not null or propagates null otherwise.
     */
    @Pure
    public static <@Unspecifiable INPUT0, @Specifiable INPUT1, @Unspecifiable OUTPUT, @Unspecifiable EXCEPTION extends Exception> @Nullable OUTPUT functionIfFirstNotNull(@NonCaptured @Unmodified @Nullable INPUT0 input0, @NonCaptured @Unmodified INPUT1 input1, @Nonnull FailableBinaryFunction<? super INPUT0, ? super INPUT1, ? extends OUTPUT, ? extends EXCEPTION> function) throws EXCEPTION {
        return Evaluate.functionIfFirstNotNull(input0, input1, function, null);
    }
    
    /**
     * Evaluates the given function for the given inputs if both inputs are not null or returns the given default output otherwise.
     */
    @Pure
    public static <@Unspecifiable INPUT0, @Unspecifiable INPUT1, @Specifiable OUTPUT, @Unspecifiable EXCEPTION extends Exception> OUTPUT functionIfBothNotNull(@NonCaptured @Unmodified @Nullable INPUT0 input0, @NonCaptured @Unmodified @Nullable INPUT1 input1, @Nonnull FailableBinaryFunction<? super INPUT0, ? super INPUT1, ? extends OUTPUT, ? extends EXCEPTION> function, @NonCaptured @Unmodified OUTPUT defaultOutput) throws EXCEPTION {
        return input0 != null && input1 != null ? function.evaluate(input0, input1) : defaultOutput;
    }
    
    /**
     * Evaluates the given function for the given inputs if both inputs are not null or propagates null otherwise.
     */
    @Pure
    public static <@Unspecifiable INPUT0, @Unspecifiable INPUT1, @Unspecifiable OUTPUT, @Unspecifiable EXCEPTION extends Exception> @Nullable OUTPUT functionIfBothNotNull(@NonCaptured @Unmodified @Nullable INPUT0 input0, @NonCaptured @Unmodified @Nullable INPUT1 input1, @Nonnull FailableBinaryFunction<? super INPUT0, ? super INPUT1, ? extends OUTPUT, ? extends EXCEPTION> function) throws EXCEPTION {
        return Evaluate.functionIfBothNotNull(input0, input1, function, null);
    }
    
}
