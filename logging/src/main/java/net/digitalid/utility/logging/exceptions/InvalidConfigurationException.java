package net.digitalid.utility.logging.exceptions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.string.Strings;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * TODO.
 */
@Immutable
public class InvalidConfigurationException extends ExternalException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected InvalidConfigurationException(@Nonnull String message, @Nullable Exception cause, @Captured @Nonnull @NullableElements Object... arguments) {
        super(message == null ? "An external exception occurred." : Strings.format(message, arguments), cause);
    }
    
    
}
