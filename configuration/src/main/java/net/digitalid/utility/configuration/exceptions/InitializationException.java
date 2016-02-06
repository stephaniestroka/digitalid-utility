package net.digitalid.utility.configuration.exceptions;

import net.digitalid.utility.exceptions.InternalException;

/**
 * This exception indicates a failed initialization.
 * 
 * @see CyclicDependenciesException
 * @see MaskingInitializationException
 * @see InitializedConfigurationException
 * @see UninitializedConfigurationException
 */
public class InitializationException extends InternalException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected InitializationException(String message, Exception cause) {
        super(message == null ? "A problem occurred during initialization." : message, cause);
    }
    
    protected InitializationException(String message) {
        this(message, null);
    }
    
}
