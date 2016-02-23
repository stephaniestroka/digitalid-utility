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
    
    private final Object[] arguments;
    
    /**
     * Returns the arguments with which the message is formatted.
     * 
     * @see FormatString
     */
    public List<Object> getArguments() {
        return Collections.unmodifiableList(Arrays.asList(arguments));
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected InternalException(String message, Exception cause, Object... arguments) {
        super(message == null ? "An internal exception occurred." : FormatString.format(message, arguments), cause);
        
        this.arguments = arguments;
    }
    
    protected InternalException(String message, Object... arguments) {
        this(message, null, arguments);
    }
    
}
