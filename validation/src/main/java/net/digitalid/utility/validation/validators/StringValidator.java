package net.digitalid.utility.validation.validators;

import javax.annotation.Nonnull;

import net.digitalid.utility.immutable.collections.ImmutableSet;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.validator.ValueAnnotationValidator;

/**
 * This class declares the target types for string annotations.
 * 
 * @see net.digitalid.utility.validation.annotations.string
 */
@Stateless
public abstract class StringValidator extends ValueAnnotationValidator {
    
    /* -------------------------------------------------- Target Types -------------------------------------------------- */
    
    private static final @Nonnull ImmutableSet<Class<?>> targetTypes = ImmutableSet.<Class<?>>with(CharSequence.class);
    
    @Pure
    @Override
    public @Nonnull ImmutableSet<Class<?>> getTargetTypes() {
        return targetTypes;
    }
    
}
