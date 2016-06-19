package net.digitalid.utility.conversion.exceptions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.logging.exceptions.ExternalException;
import net.digitalid.utility.validation.annotations.elements.NullableElements;

public class FailedValueRecoveryException extends ExternalException {
    
    protected FailedValueRecoveryException(@Nullable String message, @Nullable Exception cause, @Captured @Nonnull @NullableElements Object... arguments) {
        super(message, cause, arguments);
    }
    
    public static @Nonnull FailedValueRecoveryException of(@Nonnull Exception cause) {
        return new FailedValueRecoveryException(cause.getMessage(), cause);
    }
    
}
