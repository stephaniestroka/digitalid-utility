package net.digitalid.utility.logging.exceptions;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.digitalid.utility.exceptions.InternalException;
import net.digitalid.utility.logging.Log;
import net.digitalid.utility.string.FormatString;

/**
 * An external exception is caused by another party.
 * All custom non-runtime exceptions extend this class.
 * 
 * @see InternalException
 */
public abstract class ExternalException extends Exception {
    
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
    
    protected ExternalException(String message, Exception cause, Object... arguments) {
        super(message == null ? "An external exception occurred." : FormatString.format(message, arguments), cause);
        
        this.arguments = Collections.unmodifiableList(Arrays.asList(arguments));
        
        Log.warning("An external exception occurred:", this);
    }
    
    protected ExternalException(String message, Object... arguments) {
        this(message, null, arguments);
    }
    
    protected ExternalException(Exception cause) {
        this(null, cause);
    }
    
}
