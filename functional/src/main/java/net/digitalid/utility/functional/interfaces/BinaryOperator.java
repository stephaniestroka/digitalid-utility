package net.digitalid.utility.functional.interfaces;

import java.util.Comparator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.functional.failable.FailableBinaryOperator;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This functional interface models a binary operator that maps two objects of type {@code T} to another object of type {@code T}.
 */
@Immutable
@Functional
public interface BinaryOperator<T> extends BinaryFunction<T, T, T>, FailableBinaryOperator<T, RuntimeException> {
    
    /* -------------------------------------------------- Composition -------------------------------------------------- */
    
    /**
     * Returns the composition of this operator followed by the given operator.
     */
    @Pure
    public default @Nonnull BinaryOperator<T> before(@Nonnull UnaryOperator<T> operator) {
        return (object0, object1) -> operator.evaluate(evaluate(object0, object1));
    }
    
    /**
     * Returns the composition of the given operators followed by this operator.
     */
    @Pure
    public default @Nonnull BinaryOperator<T> after(@Nonnull UnaryOperator<T> operator0, @Nonnull UnaryOperator<T> operator1) {
        return (object0, object1) -> evaluate(operator0.evaluate(object0), operator1.evaluate(object1));
    }
    
    /* -------------------------------------------------- Null Handling -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull BinaryOperator<@Nullable T> replaceNull(@Captured T defaultValue) {
        return (object0, object1) -> object0 != null && object1 != null ? evaluate(object0, object1) : defaultValue;
    }
    
    @Pure
    @Override
    public default @Nonnull BinaryOperator<@Nullable T> propagateNull() {
        return replaceNull(null);
    }
    
    /* -------------------------------------------------- Constant -------------------------------------------------- */
    
    /**
     * Returns a binary function that always returns the given object.
     */
    @Pure
    public static <T> @Nonnull BinaryOperator<T> constant(@Captured T object) {
        return (object0, object1) -> object;
    }
    
    /* -------------------------------------------------- Comparisons -------------------------------------------------- */
    
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
