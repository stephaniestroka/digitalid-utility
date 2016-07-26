package net.digitalid.utility.exceptions.utility;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;

/**
 * Utility methods for exception handling.
 */
public final class ExceptionUtility {
    
    /**
     * Returns a one-line summary of the throwable.
     */
    @Pure
    public static @Nonnull String getThrowableSummary(@Nonnull Throwable throwable) {
        final @Nonnull StringBuilder message = new StringBuilder(throwable.getMessage());
        @Nullable Throwable cause = throwable.getCause();
        while (cause != null) {
            final @Nonnull String messagePart;
            if (cause instanceof NullPointerException) {
                messagePart = "A NullPointerException happened at: " + cause.getStackTrace()[0] + ".";
            } else {
                messagePart = cause.getMessage();
            }
            message.append(": ").append(messagePart);
            cause = cause.getCause();
        }
        return message.toString();
    }
    
}
