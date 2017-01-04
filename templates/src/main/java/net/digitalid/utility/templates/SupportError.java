package net.digitalid.utility.templates;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.errors.InitializationError;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This error indicates that the runtime system does not support a required feature.
 */
@Immutable
@GenerateBuilder
@GenerateSubclass
public abstract class SupportError extends InitializationError {
    
    /* -------------------------------------------------- Message -------------------------------------------------- */
    
    @Pure
    @Override
    public abstract @Nonnull String getMessage();
    
    /* -------------------------------------------------- Cause -------------------------------------------------- */
    
    @Pure
    @Override
    public abstract @Nonnull Throwable getCause();
    
}
