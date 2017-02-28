package net.digitalid.utility.functional.interfaces;

import java.util.Comparator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.functional.failable.FailableBinaryOperator;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This functional interface models a binary operator that maps two objects of type {@code TYPE} to another object of type {@code TYPE}.
 */
@Immutable
@Functional
public interface BinaryOperator<@Specifiable TYPE> extends BinaryFunction<TYPE, TYPE, TYPE>, FailableBinaryOperator<TYPE, RuntimeException> {
    
    /* -------------------------------------------------- Composition -------------------------------------------------- */
    
    /**
     * Returns the composition of this operator followed by the given operator.
     */
    @Pure
    public default @Nonnull BinaryOperator<TYPE> before(@Nonnull UnaryOperator<TYPE> operator) {
        return (input0, input1) -> operator.evaluate(evaluate(input0, input1));
    }
    
    /**
     * Returns the composition of the given operators followed by this operator.
     */
    @Pure
    public default @Nonnull BinaryOperator<TYPE> after(@Nonnull UnaryOperator<TYPE> operator0, @Nonnull UnaryOperator<TYPE> operator1) {
        return (input0, input1) -> evaluate(operator0.evaluate(input0), operator1.evaluate(input1));
    }
    
    /* -------------------------------------------------- Null Handling -------------------------------------------------- */
    
    @Pure
    @Override
    public default @Nonnull BinaryOperator<@Nullable TYPE> replaceNull(@Captured TYPE defaultValue) {
        return (input0, input1) -> input0 != null && input1 != null ? evaluate(input0, input1) : defaultValue;
    }
    
    @Pure
    @Override
    public default @Nonnull BinaryOperator<@Nullable TYPE> propagateNull() {
        return replaceNull(null);
    }
    
    /* -------------------------------------------------- Constant -------------------------------------------------- */
    
    /**
     * Returns a binary function that always returns the given output.
     */
    @Pure
    public static <@Specifiable TYPE> @Nonnull BinaryOperator<TYPE> constant(@Captured TYPE output) {
        return (input0, input1) -> output;
    }
    
    /* -------------------------------------------------- Comparisons -------------------------------------------------- */
    
    /**
     * Returns a binary operator which returns the lesser of two objects according to the given comparator.
     */
    @Pure
    public static <@Specifiable TYPE> @Nonnull BinaryOperator<TYPE> min(@Nonnull Comparator<? super TYPE> comparator) {
        return (input0, input1) -> comparator.compare(input0, input1) <= 0 ? input0 : input1;
    }
    
    /**
     * Returns a binary operator which returns the greater of two objects according to the given comparator.
     */
    @Pure
    public static <@Specifiable TYPE> @Nonnull BinaryOperator<TYPE> max(@Nonnull Comparator<? super TYPE> comparator) {
        return (input0, input1) -> comparator.compare(input0, input1) >= 0 ? input0 : input1;
    }
    
}
