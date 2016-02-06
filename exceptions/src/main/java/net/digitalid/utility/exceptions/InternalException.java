package net.digitalid.utility.exceptions;

/**
 * An internal exception indicates a wrong use of the library.
 * All custom runtime exceptions extend this class.
 * 
 * @see MissingSupportException
 * @see UnexpectedValueException
 * @see UnexpectedFailureException
 */
public abstract class InternalException extends RuntimeException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected InternalException(String message, Exception cause) {
        super(message == null ? "An internal exception occurred." : message, cause);
    }
    
    protected InternalException(String message) {
        this(message, null);
    }
    
}
