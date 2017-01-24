package net.digitalid.utility.conversion.exceptions;

import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.collaboration.annotations.TODO;
import net.digitalid.utility.collaboration.enumerations.Author;
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
    @TODO(task = "Why do we have to repeat this declaration in order to get null as the default cause?", date = "2017-01-24", author = Author.KASPAR_ETTER)
    public abstract @Nullable Throwable getCause();
    
}
