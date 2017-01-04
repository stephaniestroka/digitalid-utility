package net.digitalid.utility.conversion.exceptions;

import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.conversion.recovery.Check;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * A recovery exception indicates that the recovered data does not fulfill the preconditions of its class.
 * 
 * @see Check
 */
@Immutable
public abstract class RecoveryException extends ConversionException {
    
    /* -------------------------------------------------- Cause -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nullable Throwable getCause() {
        return null;
    }
    
}
