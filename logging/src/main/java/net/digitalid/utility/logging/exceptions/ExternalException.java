package net.digitalid.utility.logging.exceptions;

import net.digitalid.utility.exceptions.InternalException;
import net.digitalid.utility.immutable.collections.ImmutableList;
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
    
    protected ExternalException(String message, Exception cause, Object... arguments) {
        super(message == null ? "An external exception occurred." : FormatString.format(message, arguments), cause);
        
        this.arguments = ImmutableList.with(arguments);
        
        Log.warning("An external exception occurred:", this);
    }
    
    protected ExternalException(String message, Object... arguments) {
        this(message, null, arguments);
    }
    
    protected ExternalException(Exception cause) {
        this(null, cause);
    }
    
}
