package net.digitalid.utility.generator.exceptions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.exceptions.InternalException;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.validation.annotations.elements.NullableElements;

/**
 *
 */
public class FailedClassGenerationException extends InternalException {
    
    private final @Nullable SourcePosition sourcePosition;
    
    public @Nullable SourcePosition getSourcePosition() {
        return sourcePosition;
    }
    
    protected FailedClassGenerationException(@Nonnull String message, @Nullable SourcePosition sourcePosition, @Nullable Exception cause, @Nonnull @NullableElements Object... arguments) {
        super(message, cause, arguments);
        this.sourcePosition = sourcePosition;
    }
    
    public static FailedClassGenerationException with(@Nonnull String message, @Nonnull SourcePosition sourcePosition, @Nonnull @NullableElements String... arguments) {
        return new FailedClassGenerationException(message, sourcePosition, null, arguments);
    }
    
    public static FailedClassGenerationException with(@Nonnull String message, @Nonnull @NullableElements String... arguments) {
        return new FailedClassGenerationException(message, null, null, arguments);
    }
    
}
