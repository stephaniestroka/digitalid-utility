package net.digitalid.utility.exceptions;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    
    private final List<Object> arguments;
    
    /**
     * Returns the arguments with which the message is formatted.
     * 
     * @see FormatString#format(java.lang.CharSequence, java.lang.Object...)
     */
    public List<Object> getArguments() {
        return arguments;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected InternalException(String message, Exception cause, Object... arguments) {
        super(message == null ? "An internal exception occurred." : FormatString.format(message, arguments), cause);
        
        this.arguments = Collections.unmodifiableList(Arrays.asList(arguments));
    }
    
    protected InternalException(String message, Object... arguments) {
        this(message, null, arguments);
    }
    
}
