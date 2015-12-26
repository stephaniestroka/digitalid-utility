package net.digitalid.utility.system.converter.exceptions;

import javax.annotation.Nonnull;

public class RestoringException extends ConverterException {
    
    /**
     * Creates a new internal exception with the given message.
     *
     * @param message a string explaining the illegal operation.
     */
    private RestoringException(@Nonnull String message) {
        super(message);
    }

    // TODO: maybe move to package of converter, so that we can use the package visibility instead of 'public'.
    public static @Nonnull RestoringException get(@Nonnull String message) {
        return new RestoringException(message);
    }
}
