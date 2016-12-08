package net.digitalid.utility.logging.exceptions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.exceptions.InternalException;
import net.digitalid.utility.immutable.ImmutableList;
import net.digitalid.utility.logging.Log;
import net.digitalid.utility.string.Strings;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * An external exception is caused by another party.
 * All custom non-runtime exceptions extend this class.
 * 
 * @see InternalException
 */
@Immutable
public /* TODO: abstract */ class ExternalException extends Exception {
    
    /* -------------------------------------------------- Arguments -------------------------------------------------- */
    
    private final @Nonnull ImmutableList<@Nullable Object> arguments;
    
    /**
     * Returns the arguments with which the message is formatted.
     * 
     * @see Strings#format(java.lang.CharSequence, java.lang.Object...)
     */
    @Pure
    public @Nonnull ImmutableList<@Nullable Object> getArguments() {
        return arguments;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ExternalException(@Nullable String message, @Nullable Exception cause, @Captured @Nonnull @NullableElements Object... arguments) {
        super(message == null ? "An external exception occurred." : Strings.format(message, arguments), cause);
        
        this.arguments = ImmutableList.withElements(arguments);
        
        Log.warning("An external exception occurred:", this);
    }
    
    protected ExternalException(@Nullable String message, @Captured @Nonnull @NullableElements Object... arguments) {
        this(message, null, arguments);
    }
    
    protected ExternalException(@Nullable Exception cause) {
        this(null, cause);
    }
    
    // TODO: The following methods were just added for convenience. This class should be made abstract again with the new exception hierarchy!
    
    /**
     * Returns an external exception with the given message and cause.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Pure
    public static @Nonnull ExternalException with(@Nullable String message, @Nullable Exception cause, @Nonnull @NullableElements Object... arguments) {
        return new ExternalException(message, cause, arguments);
    }
    
    /**
     * Returns an external exception with the given message.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Pure
    public static @Nonnull ExternalException with(@Nullable String message, @Nonnull @NullableElements Object... arguments) {
        return new ExternalException(message, null, arguments);
    }
    
    /**
     * Returns an external exception with the given cause.
     */
    @Pure
    public static @Nonnull ExternalException with(@Nullable Exception cause) {
        return new ExternalException(null, cause);
    }
    
}
