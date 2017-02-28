package net.digitalid.utility.functional.exceptions;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

@Immutable
class IterationExceptionSubclass extends IterationException {
    
    /* -------------------------------------------------- Cause -------------------------------------------------- */
    
    private final @Nonnull Exception cause;
    
    @Pure
    @Override
    public @Nonnull Exception getCause() {
        return cause;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    IterationExceptionSubclass(@Nonnull Exception cause) {
        this.cause = cause;
    }
    
}
