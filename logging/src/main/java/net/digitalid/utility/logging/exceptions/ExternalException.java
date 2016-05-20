package net.digitalid.utility.logging.exceptions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
public abstract class ExternalException extends Exception {
    
    /* -------------------------------------------------- Arguments -------------------------------------------------- */
    
    private final @Nonnull ImmutableList<@Nullable Object> arguments;
    
    /**
     * Returns the arguments with which the message is formatted.
     * 
     * @see Strings#format(java.lang.CharSequence, java.lang.Object...)
     */
    public @Nonnull ImmutableList<@Nullable Object> getArguments() {
        return arguments;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ExternalException(@Nullable String message, @Nullable Exception cause, @Captured @Nonnull @NullableElements Object... arguments) {
        super(message == null ? "An external exception occurred." : Strings.format(message, arguments), cause);
        
        this.arguments = ImmutableList.with(arguments);
        
        Log.warning("An external exception occurred:", this);
    }
    
    protected ExternalException(@Nullable String message, @Captured @Nonnull @NullableElements Object... arguments) {
        this(message, null, arguments);
    }
    
    protected ExternalException(@Nullable Exception cause) {
        this(null, cause);
    }
    
}
