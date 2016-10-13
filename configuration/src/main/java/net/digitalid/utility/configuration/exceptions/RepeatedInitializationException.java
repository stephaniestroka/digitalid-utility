package net.digitalid.utility.configuration.exceptions;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This exception indicates that the library has already been initialized.
 * 
 * @see Configuration#initializeAllConfigurations()
 */
@Immutable
public class RepeatedInitializationException extends InitializationException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected RepeatedInitializationException() {
        super("The library has already been initialized!");
    }
    
    /**
     * Returns a repeated initialization exception.
     */
    @Pure
    public static @Nonnull RepeatedInitializationException withNoArguments() {
        return new RepeatedInitializationException();
    }
    
}
