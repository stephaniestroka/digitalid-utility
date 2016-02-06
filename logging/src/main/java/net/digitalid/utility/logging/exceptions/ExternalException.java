package net.digitalid.utility.logging.exceptions;

import net.digitalid.utility.exceptions.InternalException;
import net.digitalid.utility.logging.Log;

/**
 * An external exception is caused by another party.
 * All custom non-runtime exceptions extend this class.
 * 
 * @see InternalException
 */
public abstract class ExternalException extends Exception {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ExternalException(String message, Exception cause) {
        super(message == null ? "An external exception occurred." : message, cause);
        
        Log.warning("An external exception occurred:", this);
    }
    
    protected ExternalException(String message) {
        this(message, null);
    }
    
    protected ExternalException(Exception cause) {
        this(null, cause);
    }
    
}
