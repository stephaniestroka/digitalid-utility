package net.digitalid.utility.exceptions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This exception indicates that the runtime system does not support a required feature.
 */
@Immutable
public class MissingSupportException extends InternalException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected MissingSupportException(@Nullable String message, @Nullable Exception cause, @Nonnull @NullableElements Object... arguments) {
        super(message, cause, arguments);
    }
    
    /**
     * Returns a missing support exception with the given message and cause.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Pure
    public static @Nonnull MissingSupportException with(@Nullable String message, @Nullable Exception cause, @Nonnull @NullableElements Object... arguments) {
        return new MissingSupportException(message, cause, arguments);
    }
    
    /**
     * Returns a missing support exception with the given message.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Pure
    public static @Nonnull MissingSupportException with(@Nullable String message, @Nonnull @NullableElements Object... arguments) {
        return new MissingSupportException(message, null, arguments);
    }
    
    /**
     * Returns a missing support exception with the given cause.
     */
    @Pure
    public static @Nonnull MissingSupportException with(@Nullable Exception cause) {
        return new MissingSupportException(null, cause);
    }
    
}
