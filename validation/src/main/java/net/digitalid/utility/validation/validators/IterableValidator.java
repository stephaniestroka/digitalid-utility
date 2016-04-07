package net.digitalid.utility.validation.validators;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.immutable.ImmutableSet;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.validator.ValueAnnotationValidator;

/**
 * This class declares the target types for iterable annotations.
 * 
 * @see net.digitalid.utility.validation.annotations.elements
 */
@Stateless
public abstract class IterableValidator extends ValueAnnotationValidator {
    
    /* -------------------------------------------------- Target Types -------------------------------------------------- */
    
    private static final @Nonnull ImmutableSet<@Nonnull Class<?>> targetTypes = ImmutableSet.with(Iterable.class, Object[].class);
    
    @Pure
    @Override
    public @Nonnull ImmutableSet<@Nonnull Class<?>> getTargetTypes() {
        return targetTypes;
    }
    
}
