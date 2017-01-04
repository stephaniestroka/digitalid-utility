package net.digitalid.utility.exceptions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.throwable.CustomThrowable;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * An external exception indicates an abnormal condition that is caused by an external component.
 * This means that also formally correct programs can throw external exceptions.
 * All custom non-runtime exceptions extend this class.
 * 
 * @see InternalException
 */
@Immutable
public abstract class ExternalException extends Exception implements CustomThrowable {
    
    /* -------------------------------------------------- Message -------------------------------------------------- */
    
    @Pure
    @Override
    public abstract @Nonnull String getMessage();
    
    /* -------------------------------------------------- Cause -------------------------------------------------- */
    
    @Pure
    @Override
    public abstract @Nullable Throwable getCause();
    
}
