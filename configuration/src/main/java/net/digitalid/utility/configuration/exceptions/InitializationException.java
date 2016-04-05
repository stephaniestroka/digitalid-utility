package net.digitalid.utility.configuration.exceptions;

import javax.annotation.Nullable;

import net.digitalid.utility.exceptions.InternalException;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This exception indicates a failed initialization.
 * 
 * @see ConfigurationException
 * @see CyclicDependenciesException
 * @see MaskingInitializationException
 */
@Immutable
public class InitializationException extends InternalException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected InitializationException(@Nullable String message, @Nullable Exception cause, @Nullable Object... arguments) {
        super(message, cause, arguments);
    }
    
    protected InitializationException(@Nullable String message, @Nullable Object... arguments) {
        this(message, null, arguments);
    }
    
}
