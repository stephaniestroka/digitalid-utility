package net.digitalid.utility.functional.exceptions;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This exception allows to tunnel checked exceptions through the methods of {@link Iterable}.
 */
@Immutable
public class FailedIterationException extends RuntimeException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected FailedIterationException(@Nonnull Exception exception) {
        super("The iteration failed.", exception);
    }
    
    /**
     * Returns a failed iteration exception to tunnel the given exception through the methods of {@link Iterable}.
     */
    @Pure
    public static @Nonnull FailedIterationException with(@Nonnull Exception exception) {
        return new FailedIterationException(exception);
    }
    
}
