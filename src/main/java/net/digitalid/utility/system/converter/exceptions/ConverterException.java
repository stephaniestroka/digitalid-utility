package net.digitalid.utility.system.converter.exceptions;

import javax.annotation.Nonnull;
import net.digitalid.utility.system.exceptions.internal.InternalException;

public abstract class ConverterException extends Exception {

    /**
     * Creates a new internal exception with the given message.
     *
     * @param message a string explaining the illegal operation.
     */
    protected ConverterException(@Nonnull String message) {
        super(message);
    }
    
    protected ConverterException(@Nonnull String message, @Nonnull Throwable throwable) {
        super(message, throwable);
    }
}
