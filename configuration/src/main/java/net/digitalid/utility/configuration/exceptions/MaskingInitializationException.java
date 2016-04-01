package net.digitalid.utility.configuration.exceptions;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This exception allows to mask other exceptions as an initialization exception.
 */
@Immutable
public class MaskingInitializationException extends InitializationException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected MaskingInitializationException(@Nonnull Exception cause) {
        super("A problem occurred during initialization.", cause);
    }
    
    /**
     * Returns a masking initialization exception with the given cause.
     */
    @Pure
    public static @Nonnull MaskingInitializationException with(@Nonnull Exception cause) {
        return new MaskingInitializationException(cause);
    }
    
}
