package net.digitalid.utility.errors;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

@Immutable
class SupportErrorSubclass extends SupportError {
    
    /* -------------------------------------------------- Message -------------------------------------------------- */
    
    private final @Nonnull String message;
    
    @Pure
    @Override
    public @Nonnull String getMessage() {
        return this.message;
    }
    
    /* -------------------------------------------------- Cause -------------------------------------------------- */
    
    private final @Nonnull Throwable cause;
    
    @Pure
    @Override
    public @Nonnull Throwable getCause() {
        return this.cause;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    SupportErrorSubclass(@Nonnull String message, @Nonnull Throwable cause) {
        this.message = message;
        this.cause = cause;
    }
    
}
