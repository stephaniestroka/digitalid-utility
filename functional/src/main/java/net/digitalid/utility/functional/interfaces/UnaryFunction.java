package net.digitalid.utility.functional.interfaces;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.functional.failable.FailableUnaryFunction;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This functional interface models a unary function that maps an object of type {@code INPUT} to an object of type {@code OUTPUT}.
 */
@Immutable
@Functional
public interface UnaryFunction<@Specifiable INPUT, @Specifiable OUTPUT> extends FailableUnaryFunction<INPUT, OUTPUT, RuntimeException> {
    
    /* -------------------------------------------------- Composition -------------------------------------------------- */
    
    /**
     * Returns the composition of this function followed by the given function.
     */
    @Pure
    public default <@Specifiable FINAL_OUTPUT> @Nonnull UnaryFunction<INPUT, FINAL_OUTPUT> before(@Nonnull UnaryFunction<? super OUTPUT, ? extends FINAL_OUTPUT> function) {
        return input -> function.evaluate(evaluate(input));
    }
    
    /**
     * Returns the composition of the given function followed by this function.
     */
    @Pure
    public default <@Specifiable INITIAL_INPUT> @Nonnull UnaryFunction<INITIAL_INPUT, OUTPUT> after(@Nonnull UnaryFunction<? super INITIAL_INPUT, ? extends INPUT> function) {
        return input -> evaluate(function.evaluate(input));
    }
    
    /* -------------------------------------------------- Null Handling -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull UnaryFunction<@Nullable INPUT, OUTPUT> replaceNull(@Captured OUTPUT defaultValue) {
        return input -> input != null ? evaluate(input) : defaultValue;
    }
    
    @Pure
    @Override
    public default @Nonnull UnaryFunction<@Nullable INPUT, @Nullable OUTPUT> propagateNull() {
        return replaceNull(null);
    }
    
    /* -------------------------------------------------- Mappings -------------------------------------------------- */
    
    /**
     * Returns a function that looks up the result in the given map or returns the given default value if the input is not found in the map.
     */
    @Pure
    public static <@Specifiable KEY, @Specifiable VALUE> @Nonnull UnaryFunction<KEY, VALUE> with(@Nonnull Map<? super KEY, ? extends VALUE> map, VALUE defaultValue) {
        return key -> {
            if (map.containsKey(key)) {
                return map.get(key);
            } else {
                return defaultValue;
            }
        };
    }
    
    /**
     * Returns a function that looks up the result in the given map or returns null if the input is not found in the map.
     */
    @Pure
    public static <@Specifiable KEY, @Specifiable VALUE> @Nonnull UnaryFunction<KEY, @Nullable VALUE> with(@Nonnull Map<? super KEY, ? extends VALUE> map) {
        return with(map, null);
    }
    
    /* -------------------------------------------------- Constant -------------------------------------------------- */
    
    /**
     * Returns a function that always returns the given output.
     */
    @Pure
    public static <@Specifiable OUTPUT> @Nonnull UnaryFunction<@Nullable Object, OUTPUT> constant(@Captured OUTPUT output) {
        return input -> output;
    }
    
}
