package net.digitalid.utility.functional.interfaces;

import java.util.Comparator;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This functional interface models a binary operator that maps two objects of type {@code T} to another object of type {@code T}.
 */
@Immutable
@Functional
public interface BinaryOperator<T> extends BinaryFunction<T, T, T> {
    
    /**
     * Returns a binary operator which returns the lesser of two objects according to the given comparator.
     */
    @Pure
    public static <T> @Nonnull BinaryOperator<T> min(@Nonnull Comparator<? super T> comparator) {
        return (a, b) -> comparator.compare(a, b) <= 0 ? a : b;
    }
    
    /**
     * Returns a binary operator which returns the greater of two objects according to the given comparator.
     */
    @Pure
    public static <T> @Nonnull BinaryOperator<T> max(@Nonnull Comparator<? super T> comparator) {
        return (a, b) -> comparator.compare(a, b) >= 0 ? a : b;
    }
    
}
