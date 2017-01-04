package net.digitalid.utility.throwable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Utility;

/**
 * This class provides useful operations on throwables.
 */
@Utility
public abstract class Throwables {
    
    /* -------------------------------------------------- Summary -------------------------------------------------- */
    
    /**
     * Returns a one-line summary of the given throwable.
     */
    @Pure
    public static @Nonnull String getSummary(@Nonnull Throwable throwable) {
        final @Nonnull StringBuilder message = new StringBuilder(throwable.getMessage());
        @Nullable Throwable cause = throwable.getCause();
        while (cause != null) {
            message.append(": ");
            if (cause instanceof NullPointerException) {
                message.append("A NullPointerException happened at: ").append(cause.getStackTrace()[0]).append(".");
            } else {
                message.append(cause.getMessage());
            }
            cause = cause.getCause();
        }
        return message.toString();
    }
    
}
