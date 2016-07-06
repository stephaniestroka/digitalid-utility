package net.digitalid.utility.validation.validators;

import java.io.File;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.validator.ValueAnnotationValidator;

/**
 * This class declares the target types for file annotations.
 * 
 * @see net.digitalid.utility.validation.annotations.file.existence
 * @see net.digitalid.utility.validation.annotations.file.kind
 * @see net.digitalid.utility.validation.annotations.file.path
 * @see net.digitalid.utility.validation.annotations.file.permission
 * @see net.digitalid.utility.validation.annotations.file.visibility
 */
@Stateless
public abstract class FileValidator implements ValueAnnotationValidator {
    
    /* -------------------------------------------------- Target Types -------------------------------------------------- */
    
    private static final @Nonnull FiniteIterable<@Nonnull Class<?>> targetTypes = FiniteIterable.of(File.class);
    
    @Pure
    @Override
    public @Nonnull FiniteIterable<@Nonnull Class<?>> getTargetTypes() {
        return targetTypes;
    }
    
}
