package net.digitalid.utility.exceptions.internal;

import net.digitalid.utility.exceptions.DigitalIDException;

/**
 * An internal exception indicates a wrong use of the library.
 */
public class InternalException extends DigitalIDException {
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new internal exception with the given message.
     * 
     * @param message a string explaining the illegal operation.
     */
    protected InternalException(String message) {
        super(message);
    }

    /**
     * Creates a new internal exception with the given message and the given exception.
     * 
     * @param message a string explaining the illegal operation.
     * @param exception an exception which is the cause of this exception.
     */
    protected InternalException(String message, Exception exception) {
        super(message, exception);
    }

    /**
     * Returns a new internal exception with the given exception.
     *
     * @param exception a cause of this exception.
     *
     * @return a new internal exception with the given exception.
     */
    public static InternalException get(Exception exception) {
        return new InternalException(exception.getMessage(), exception);
    }
    
    /**
     * Returns a new internal exception with the given message and the given exception.
     *
     * @param message a string explaining the illegal operation.
     * @param exception a cause of this exception.
     *
     * @return a new internal exception with the given message and the given exception.
     */
    public static InternalException get(String message, Exception exception) {
        return new InternalException(message);
    }
    
    /**
     * Returns a new internal exception with the given message.
     * 
     * @param message a string explaining the illegal operation.
     * 
     * @return a new internal exception with the given message.
     */
    public static InternalException get(String message) {
        return new InternalException(message);
    }
    
}
