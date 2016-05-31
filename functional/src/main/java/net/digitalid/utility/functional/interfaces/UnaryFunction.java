package net.digitalid.utility.functional.interfaces;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.functional.failable.FailableUnaryFunction;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This functional interface models a unary function that maps an object of type {@code I} to an object of type {@code O}.
 */
@Immutable
@Functional
public interface UnaryFunction<I, O> extends FailableUnaryFunction<I, O, RuntimeException> {
    
    /* -------------------------------------------------- Composition -------------------------------------------------- */
    
    /**
     * Returns the composition of this function followed by the given function.
     */
    @Pure
    public default <T> @Nonnull UnaryFunction<I, T> before(@Nonnull UnaryFunction<? super O, ? extends T> function) {
        return object -> function.evaluate(evaluate(object));
    }
    
    /**
     * Returns the composition of the given function followed by this function.
     */
    @Pure
    public default <T> @Nonnull UnaryFunction<T, O> after(@Nonnull UnaryFunction<? super T, ? extends I> function) {
        return object -> evaluate(function.evaluate(object));
    }
    
    /* -------------------------------------------------- Null Handling -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull UnaryFunction<@Nullable I, O> replaceNull(@Captured O defaultValue) {
        return object -> object != null ? evaluate(object) : defaultValue;
    }
    
    @Pure
    @Override
    public default @Nonnull UnaryFunction<@Nullable I, @Nullable O> propagateNull() {
        return replaceNull(null);
    }
    
    /* -------------------------------------------------- Mappings -------------------------------------------------- */
    
    /**
     * Returns a function that looks up the result in the given map or returns the given default value if the input is not found in the map.
     */
    @Pure
    public static <K, V> @Nonnull UnaryFunction<K, V> with(@Nonnull Map<? super K, ? extends V> map, V defaultValue) {
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
    public static <K, V> @Nonnull UnaryFunction<K, @Nullable V> with(@Nonnull Map<? super K, ? extends V> map) {
        return with(map, null);
    }
    
    /* -------------------------------------------------- Constant -------------------------------------------------- */
    
    /**
     * Returns a function that always returns the given object.
     */
    @Pure
    public static <O> @Nonnull UnaryFunction<@Nullable Object, O> constant(@Captured O object) {
        return input -> object;
    }
    
}
