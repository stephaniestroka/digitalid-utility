package net.digitalid.utility.exceptions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.immutable.collections.ImmutableList;
import net.digitalid.utility.string.Strings;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * An internal exception indicates a wrong use of the library.
 * All custom runtime exceptions extend this class.
 * 
 * @see MissingSupportException
 * @see UnexpectedValueException
 * @see UnexpectedFailureException
 */
@Immutable
public abstract class InternalException extends RuntimeException {
    
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
    
    protected InternalException(@Nullable String message, @Nullable Exception cause, @Nullable Object... arguments) {
        super(message == null ? "An internal exception occurred." : Strings.format(message, arguments), cause);
        
        this.arguments = ImmutableList.with(arguments);
    }
    
    protected InternalException(@Nullable String message, @Nullable Object... arguments) {
        this(message, null, arguments);
    }
    
}
