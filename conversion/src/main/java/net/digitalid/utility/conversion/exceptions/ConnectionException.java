package net.digitalid.utility.conversion.exceptions;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * The connection exception constitutes the upper bound of the converter’s generic exception.
 */
@Immutable
public abstract class ConnectionException extends ConversionException {
    
    /* -------------------------------------------------- Message -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull String getMessage() {
        return "The conversion failed due to an interrupted connection or violated constraints.";
    }
    
    /* -------------------------------------------------- Cause -------------------------------------------------- */
    
    @Pure
    @Override
    public abstract @Nonnull Exception getCause();
    
}
