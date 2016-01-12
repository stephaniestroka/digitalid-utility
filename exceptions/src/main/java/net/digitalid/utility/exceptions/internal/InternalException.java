package net.digitalid.utility.exceptions.internal;

import javax.annotation.Nonnull;
import net.digitalid.utility.annotations.state.Immutable;
import net.digitalid.utility.annotations.state.Pure;
import net.digitalid.utility.exceptions.DigitalIDException;

/**
 * An internal exception indicates a wrong use of the library.
 */
@Immutable
public class InternalException extends DigitalIDException {
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new internal exception with the given message.
     * 
     * @param message a string explaining the illegal operation.
     */
    protected InternalException(@Nonnull String message) {
        super(message);
    }

    /**
     * Creates a new internal exception with the given message and the given exception.
     * 
     * @param message a string explaining the illegal operation.
     * @param exception an exception which is the cause of this exception.
     */
    protected InternalException(@Nonnull String message, @Nonnull Exception exception) {
        super(message, exception);
    }

    /**
     * Returns a new internal exception with the given exception.
     *
     * @param exception a cause of this exception.
     *
     * @return a new internal exception with the given exception.
     */
    @Pure
    public static @Nonnull InternalException get(@Nonnull Exception exception) {
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
    @Pure
    public static @Nonnull InternalException get(@Nonnull String message, @Nonnull Exception exception) {
        return new InternalException(message);
    }
    
    /**
     * Returns a new internal exception with the given message.
     * 
     * @param message a string explaining the illegal operation.
     * 
     * @return a new internal exception with the given message.
     */
    @Pure
    public static @Nonnull InternalException get(@Nonnull String message) {
        return new InternalException(message);
    }
    
}
