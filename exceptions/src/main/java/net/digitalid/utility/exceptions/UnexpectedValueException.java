package net.digitalid.utility.exceptions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This exception indicates that a variable had an unexpected value.
 * For example, it is thrown if a switch statement over an enumeration needs a default case.
 */
@Immutable
public class UnexpectedValueException extends InternalException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected UnexpectedValueException(@Nonnull String variable, @Nullable Object value) {
        super("It was not expected that $ = $.", variable, value);
    }
    
    /**
     * Returns an unexpected value exception with the given variable and value.
     * 
     * @param variable the name of the variable with the unexpected value.
     * @param value the value of the given variable that was not expected.
     */
    @Pure
    public static @Nonnull UnexpectedValueException with(@Nonnull String variable, @Nullable Object value) {
        return new UnexpectedValueException(variable, value);
    }
    
}
