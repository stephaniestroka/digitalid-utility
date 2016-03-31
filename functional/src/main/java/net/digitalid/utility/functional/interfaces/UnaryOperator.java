package net.digitalid.utility.functional.interfaces;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This functional interface models a binary operator that maps two objects of type {@code T} to another object of type {@code T}.
 */
@Immutable
@Functional
public interface UnaryOperator<T> extends UnaryFunction<T, T> {
    
    /**
     * Returns a unary operator that always returns its input argument.
     */
    @Pure
    public static <T> @Nonnull UnaryOperator<T> identity() {
        return t -> t;
    }
    
}
