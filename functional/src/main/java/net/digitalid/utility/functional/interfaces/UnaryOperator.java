package net.digitalid.utility.functional.interfaces;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.functional.failable.FailableUnaryOperator;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This functional interface models a unary operator that maps an object of type {@code T} to another object of type {@code T}.
 */
@Immutable
@Functional
public interface UnaryOperator<T> extends UnaryFunction<T, T>, FailableUnaryOperator<T, RuntimeException> {
    
    /* -------------------------------------------------- Composition -------------------------------------------------- */
    
    /**
     * Returns the composition of this operator followed by the given operator.
     */
    @Pure
    public default @Nonnull UnaryOperator<T> before(@Nonnull UnaryOperator<T> operator) {
        return object -> operator.evaluate(evaluate(object));
    }
    
    /**
     * Returns the composition of the given operator followed by this operator.
     */
    @Pure
    public default @Nonnull UnaryOperator<T> after(@Nonnull UnaryOperator<T> operator) {
        return object -> evaluate(operator.evaluate(object));
    }
    
    /* -------------------------------------------------- Null Handling -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull UnaryOperator<@Nullable T> replaceNull(@Captured T defaultOutput) {
        return object -> object != null ? evaluate(object) : defaultOutput;
    }
    
    @Pure
    @Override
    public default @Nonnull UnaryOperator<@Nullable T> propagateNull() {
        return replaceNull(null);
    }
    
    /* -------------------------------------------------- Mappings -------------------------------------------------- */
    
    /**
     * Returns an operator that looks up the result in the given map or returns the given default value if the input is not found in the map.
     */
    @Pure
    public static <T> @Nonnull UnaryOperator<T> with(@Nonnull Map<? super T, ? extends T> map, T defaultValue) {
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
    public static <T> @Nonnull UnaryOperator<@Nullable T> with(@Nonnull Map<? super T, ? extends T> map) {
        return with(map, null);
    }
    
    /* -------------------------------------------------- Constant -------------------------------------------------- */
    
    /**
     * Returns an operator that always returns the given object.
     */
    @Pure
    public static <T> @Nonnull UnaryOperator<T> constant(@Captured T object) {
        return input -> object;
    }
    
    /* -------------------------------------------------- Identity -------------------------------------------------- */
    
    /**
     * Returns a unary operator that always returns its input argument.
     */
    @Pure
    public static <T> @Nonnull UnaryOperator<T> identity() {
        return t -> t;
    }
    
}
