package net.digitalid.utility.validation.validators;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.validator.ValueAnnotationValidator;

/**
 * This class declares the target types for iterable annotations.
 * 
 * @see net.digitalid.utility.validation.annotations.elements
 * @see OrderingValidator
 */
@Stateless
public abstract class IterableValidator extends ValueAnnotationValidator {
    
    /* -------------------------------------------------- Target Types -------------------------------------------------- */
    
    private static final @Nonnull FiniteIterable<@Nonnull Class<?>> targetTypes = FiniteIterable.of(Iterable.class, Object[].class, boolean[].class, char[].class, byte[].class, short[].class, int[].class, long[].class, float[].class, double[].class);
    
    @Pure
    @Override
    public @Nonnull FiniteIterable<@Nonnull Class<?>> getTargetTypes() {
        return targetTypes;
    }
    
}
