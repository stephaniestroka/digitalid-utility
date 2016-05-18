package net.digitalid.utility.validation.processing;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.logging.Level;
import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.validator.AnnotationHandler;

/**
 * This class allows to inject other behavior into usage checks for testing.
 * This class extends {@link ProcessingLog} only for being able to call {@link ProcessingLog#log(net.digitalid.utility.logging.Level, java.lang.CharSequence, net.digitalid.utility.processing.logging.SourcePosition, java.lang.Object...)} directly.
 * 
 * @see AnnotationHandler#checkUsage(javax.lang.model.element.Element, javax.lang.model.element.AnnotationMirror, net.digitalid.utility.validation.processing.ErrorLogger)
 */
@Mutable
public class ErrorLogger extends ProcessingLog {
    
    /**
     * Stores an instance of the error logger.
     */
    public static final @Nonnull ErrorLogger INSTANCE = new ErrorLogger();
    
    /**
     * Logs the given message with the given position as an error.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Impure
    public void log(@Nonnull CharSequence message, @Nullable SourcePosition position, @NonCaptured @Unmodified @Nullable Object... arguments) {
        log(Level.ERROR, message, position, arguments);
    }
    
}
