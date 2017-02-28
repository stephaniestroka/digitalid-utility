package net.digitalid.utility.functional.interfaces;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.generics.Unspecifiable;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.functional.failable.FailableUnaryOperator;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This functional interface models a unary operator that maps an object of type {@code TYPE} to another object of type {@code TYPE}.
 */
@Immutable
@Functional
public interface UnaryOperator<TYPE> extends UnaryFunction<TYPE, TYPE>, FailableUnaryOperator<TYPE, RuntimeException> {
    
    /* -------------------------------------------------- Composition -------------------------------------------------- */
    
    /**
     * Returns the composition of this operator followed by the given operator.
     */
    @Pure
    public default @Nonnull UnaryOperator<TYPE> before(@Nonnull UnaryOperator<TYPE> operator) {
        return input -> operator.evaluate(evaluate(input));
    }
    
    /**
     * Returns the composition of the given operator followed by this operator.
     */
    @Pure
    public default @Nonnull UnaryOperator<TYPE> after(@Nonnull UnaryOperator<TYPE> operator) {
        return input -> evaluate(operator.evaluate(input));
    }
    
    /* -------------------------------------------------- Null Handling -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull UnaryOperator<@Nullable TYPE> replaceNull(@Captured TYPE defaultOutput) {
        return input -> input != null ? evaluate(input) : defaultOutput;
    }
    
    @Pure
    @Override
    public default @Nonnull UnaryOperator<@Nullable TYPE> propagateNull() {
        return replaceNull(null);
    }
    
    /* -------------------------------------------------- Mappings -------------------------------------------------- */
    
    /**
     * Returns an operator that looks up the result in the given map or returns the given default value if the input is not found in the map.
     */
    @Pure
    public static <@Specifiable TYPE> @Nonnull UnaryOperator<TYPE> with(@Nonnull Map<? super TYPE, ? extends TYPE> map, TYPE defaultValue) {
        return key -> {
            if (map.containsKey(key)) {
                return map.get(key);
            } else {
                return defaultValue;
            }
        };
    }
    
    /**
     * Returns an operator that looks up the result in the given map or returns null if the input is not found in the map.
     */
    @Pure
    public static <@Unspecifiable TYPE> @Nonnull UnaryOperator<@Nullable TYPE> with(@Nonnull Map<? super TYPE, ? extends TYPE> map) {
        return with(map, null);
    }
    
    /* -------------------------------------------------- Constant -------------------------------------------------- */
    
    /**
     * Returns an operator that always returns the given output.
     */
    @Pure
    public static <@Specifiable TYPE> @Nonnull UnaryOperator<TYPE> constant(@Captured TYPE output) {
        return input -> output;
    }
    
    /* -------------------------------------------------- Identity -------------------------------------------------- */
    
    /**
     * Returns a unary operator that always returns its input argument.
     */
    @Pure
    public static <@Specifiable TYPE> @Nonnull UnaryOperator<TYPE> identity() {
        return input -> input;
    }
    
}
