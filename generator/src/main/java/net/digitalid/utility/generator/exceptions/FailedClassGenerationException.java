package net.digitalid.utility.generator.exceptions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.string.Strings;
import net.digitalid.utility.validation.annotations.elements.NullableElements;

/**
 * TODO: Code generation should not throw exceptions but rather handle the whole input.
 */
@Deprecated
public class FailedClassGenerationException extends RuntimeException {
    
    private final @Nullable SourcePosition sourcePosition;
    
    public @Nullable SourcePosition getSourcePosition() {
        return sourcePosition;
    }
    
    protected FailedClassGenerationException(@Nonnull String message, @Nullable SourcePosition sourcePosition, @Nullable Exception cause, @Nonnull @NullableElements Object... arguments) {
        super(Strings.format(message, arguments), cause);
        this.sourcePosition = sourcePosition;
    }
    
    public static FailedClassGenerationException with(@Nonnull String message, @Nonnull SourcePosition sourcePosition, @Nonnull @NullableElements Object... arguments) {
        return new FailedClassGenerationException(message, sourcePosition, null, arguments);
    }
    
    public static FailedClassGenerationException with(@Nonnull String message, @Nonnull @NullableElements Object... arguments) {
        return new FailedClassGenerationException(message, null, null, arguments);
    }
    
}
