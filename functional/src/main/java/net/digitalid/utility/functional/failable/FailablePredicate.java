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
import net.digitalid.utility.functional.interfaces.Predicate;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This functional interface models a failable predicate that evaluates whether an input of type {@code INPUT} satisfies a condition.
 */
@Immutable
@Functional
public interface FailablePredicate<@Specifiable INPUT, @Unspecifiable EXCEPTION extends Exception> {
    
    /* -------------------------------------------------- Evaluation -------------------------------------------------- */
    
    /**
     * Evaluates whether the given input satisfies this predicate.
     * All implementations of this method have to be side-effect-free.
     */
    @Pure
    public boolean evaluate(@NonCaptured @Unmodified INPUT input) throws EXCEPTION;
    
    /* -------------------------------------------------- Suppression -------------------------------------------------- */
    
    /**
     * Returns a predicate that catches the exceptions of this predicate, passes them to the given exception handler and returns the given default output instead.
     */
    @Pure
    public default @Nonnull Predicate<INPUT> suppressExceptions(@Captured @Nonnull Consumer<@Nonnull ? super Exception> handler, boolean defaultOutput) {
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
     * Returns a predicate that catches the exceptions of this predicate, passes them to the given exception handler and returns false instead.
     */
    @Pure
    public default @Nonnull Predicate<INPUT> suppressExceptions(@Captured @Nonnull Consumer<@Nonnull ? super Exception> handler) {
        return suppressExceptions(handler, false);
    }
    
    /**
     * Returns a predicate that suppresses the exceptions of this predicate and returns the given default output instead.
     */
    @Pure
    public default @Nonnull Predicate<INPUT> suppressExceptions(boolean defaultOutput) {
        return suppressExceptions(Consumer.DO_NOTHING, defaultOutput);
    }
    
    /**
     * Returns a predicate that suppresses the exceptions of this predicate and returns false instead.
     */
    @Pure
    public default @Nonnull Predicate<INPUT> suppressExceptions() {
        return suppressExceptions(Consumer.DO_NOTHING, false);
    }
    
    /* -------------------------------------------------- Conjunction -------------------------------------------------- */
    
    /**
     * Returns the conjunction of this predicate with the given predicate.
     */
    @Pure
    public default @Nonnull FailablePredicate<INPUT, EXCEPTION> and(@Nonnull FailablePredicate<? super INPUT, ? extends EXCEPTION> predicate) {
        return input -> evaluate(input) && predicate.evaluate(input);
    }
    
    /**
     * Returns the conjunction of the given predicates.
     */
    @Pure
    public static <@Specifiable INPUT, @Unspecifiable EXCEPTION extends Exception> @Nonnull FailablePredicate<INPUT, EXCEPTION> and(@Nonnull FiniteIterable<@Nonnull ? extends FailablePredicate<? super INPUT, ? extends EXCEPTION>> predicates) {
        return input -> {
            for (@Nonnull FailablePredicate<? super INPUT, ? extends EXCEPTION> predicate : predicates) {
                if (!predicate.evaluate(input)) { return false; }
            }
            return true;
        };
    }
    
    /* -------------------------------------------------- Disjunction -------------------------------------------------- */
    
    /**
     * Returns the disjunction of this predicate with the given predicate.
     */
    @Pure
    public default @Nonnull FailablePredicate<INPUT, EXCEPTION> or(@Nonnull FailablePredicate<? super INPUT, ? extends EXCEPTION> predicate) {
        return input -> evaluate(input) || predicate.evaluate(input);
    }
    
    /**
     * Returns the disjunction of the given predicates.
     */
    @Pure
    @SafeVarargs
    public static <@Specifiable INPUT, @Unspecifiable EXCEPTION extends Exception> @Nonnull FailablePredicate<INPUT, EXCEPTION> or(@Nonnull @NonNullableElements FailablePredicate<? super INPUT, ? extends EXCEPTION>... predicates) {
        return input -> {
            for (@Nonnull FailablePredicate<? super INPUT, ? extends EXCEPTION> predicate : predicates) {
                if (predicate.evaluate(input)) { return true; }
            }
            return false;
        };
    }
    
    /**
     * Returns the disjunction of the given predicates.
     */
    @Pure
    public static <@Specifiable INPUT, @Unspecifiable EXCEPTION extends Exception> @Nonnull FailablePredicate<INPUT, EXCEPTION> or(@Nonnull FiniteIterable<@Nonnull ? extends FailablePredicate<? super INPUT, ? extends EXCEPTION>> predicates) {
        return input -> {
            for (@Nonnull FailablePredicate<? super INPUT, ? extends EXCEPTION> predicate : predicates) {
                if (predicate.evaluate(input)) { return true; }
            }
            return false;
        };
    }
    
    /* -------------------------------------------------- Negation -------------------------------------------------- */
    
    /**
     * Returns the negation of this predicate.
     */
    @Pure
    public default @Nonnull FailablePredicate<INPUT, EXCEPTION> negate() {
        return input -> !evaluate(input);
    }
    
    /* -------------------------------------------------- Composition -------------------------------------------------- */
    
    /**
     * Returns the composition of the given function and predicate with a flexible exception type.
     */
    @Pure
    public static <@Specifiable INPUT, @Specifiable INTERMEDIATE, @Unspecifiable EXCEPTION extends Exception> @Nonnull FailablePredicate<INPUT, EXCEPTION> compose(@Nonnull FailableUnaryFunction<? super INPUT, ? extends INTERMEDIATE, ? extends EXCEPTION> function, @Nonnull FailablePredicate<? super INTERMEDIATE, ? extends EXCEPTION> predicate) {
        return input -> predicate.evaluate(function.evaluate(input));
    }
    
    /**
     * Returns the composition of the given function followed by this predicate.
     * Unfortunately, it is not possible to make the exception type flexible.
     * 
     * @see #compose(net.digitalid.utility.functional.failable.FailableUnaryFunction, net.digitalid.utility.functional.failable.FailablePredicate)
     */
    @Pure
    public default <@Specifiable INITIAL_INPUT> @Nonnull FailablePredicate<INITIAL_INPUT, EXCEPTION> after(@Nonnull FailableUnaryFunction<? super INITIAL_INPUT, ? extends INPUT, ? extends EXCEPTION> function) {
        return input -> evaluate(function.evaluate(input));
    }
    
    /* -------------------------------------------------- Conversion -------------------------------------------------- */
    
    /**
     * Returns this predicate as a unary function.
     */
    @Pure
    public default @Nonnull FailableUnaryFunction<INPUT, @Nonnull Boolean, EXCEPTION> asFunction() {
        return this::evaluate;
    }
    
    /* -------------------------------------------------- Null Handling -------------------------------------------------- */
    
    /**
     * Returns a new predicate based on this predicate that returns the given default value for null.
     */
    @Pure
    public default @Nonnull FailablePredicate<@Nullable INPUT, EXCEPTION> replaceNull(boolean defaultValue) {
        return input -> input != null ? evaluate(input) : defaultValue;
    }
    
}
