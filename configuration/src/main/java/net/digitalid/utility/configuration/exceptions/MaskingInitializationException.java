package net.digitalid.utility.configuration.exceptions;

/**
 * This exception allows to mask other exceptions as an initialization exception.
 */
public class MaskingInitializationException extends InitializationException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected MaskingInitializationException(Exception cause) {
        super("A problem occurred during initialization.", cause);
    }
    
    /**
     * Returns a masking initialization exception with the given cause.
     */
    public static MaskingInitializationException with(Exception cause) {
        return new MaskingInitializationException(cause);
    }
    
}
