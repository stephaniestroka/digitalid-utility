package net.digitalid.utility.exceptions;

import net.digitalid.utility.immutable.collections.ImmutableList;
import net.digitalid.utility.string.FormatString;

/**
 * An internal exception indicates a wrong use of the library.
 * All custom runtime exceptions extend this class.
 * 
 * @see MissingSupportException
 * @see UnexpectedValueException
 * @see UnexpectedFailureException
 */
public abstract class InternalException extends RuntimeException {
    
    /* -------------------------------------------------- Arguments -------------------------------------------------- */
    
    private final ImmutableList<Object> arguments;
    
    /**
     * Returns the arguments with which the message is formatted.
     * 
     * @see FormatString#format(java.lang.CharSequence, java.lang.Object...)
     */
    public ImmutableList<Object> getArguments() {
        return arguments;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected InternalException(String message, Exception cause, Object... arguments) {
        super(message == null ? "An internal exception occurred." : FormatString.format(message, arguments), cause);
        
        this.arguments = ImmutableList.with(arguments);
    }
    
    protected InternalException(String message, Object... arguments) {
        this(message, null, arguments);
    }
    
}
