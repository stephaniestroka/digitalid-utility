package net.digitalid.utility.exceptions;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

@Immutable
class UncheckedExceptionSubclass extends UncheckedException {
    
    /* -------------------------------------------------- Cause -------------------------------------------------- */
    
    private final @Nonnull Exception cause;
    
    @Pure
    @Override
    public @Nonnull Exception getCause() {
        return this.cause;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    UncheckedExceptionSubclass(@Nonnull Exception cause) {
        this.cause = cause;
    }
    
}
