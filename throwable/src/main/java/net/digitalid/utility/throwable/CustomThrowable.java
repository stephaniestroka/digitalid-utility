package net.digitalid.utility.throwable;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This interface extends the {@link Throwable} class with useful methods.
 */
@Immutable
public interface CustomThrowable {
    
    /* -------------------------------------------------- Summary -------------------------------------------------- */
    
    /**
     * Returns a one-line summary of this external exception.
     */
    @Pure
    public default @Nonnull String getSummary() {
        return Throwables.getSummary((Throwable) this);
    }
    
}
