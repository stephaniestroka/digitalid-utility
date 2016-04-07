package net.digitalid.utility.processing.logging;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.immutable.ImmutableMap;
import net.digitalid.utility.logging.Caller;
import net.digitalid.utility.logging.Level;
import net.digitalid.utility.logging.logger.FileLogger;
import net.digitalid.utility.logging.logger.Logger;
import net.digitalid.utility.processing.utility.StaticProcessingEnvironment;
import net.digitalid.utility.string.Strings;
import net.digitalid.utility.validation.annotations.type.Utility;

/**
 * This class makes it easier to log messages during annotation processing.
 */
@Utility
public class ProcessingLog {
    
    /* -------------------------------------------------- Setup -------------------------------------------------- */
    
    /**
     * Sets the output file of the logger to the given name.
     */
    @Impure
    public static void setUp(@Nonnull String name) {
        Require.that(name != null).orThrow("The name may not be null.");
        
        Logger.logger.set(FileLogger.with("target/processor-logs/" + name + ".log"));
        Level.threshold.set(Level.VERBOSE);
        Caller.index.set(6);
    }
    
    /* -------------------------------------------------- Mapping -------------------------------------------------- */
    
    /**
     * Stores a mapping from this library's logging levels to the corresponding diagnostic kind.
     */
    private static final @Nonnull ImmutableMap<@Nonnull Level, Diagnostic.@Nonnull Kind> levelToKind = ImmutableMap.with(Level.VERBOSE, Diagnostic.Kind.OTHER).with(Level.DEBUGGING, Diagnostic.Kind.OTHER).with(Level.INFORMATION, Diagnostic.Kind.NOTE).with(Level.WARNING, Diagnostic.Kind.WARNING).with(Level.ERROR, Diagnostic.Kind.ERROR).build();
    
    /* -------------------------------------------------- Logging -------------------------------------------------- */
    
    /**
     * Logs the given message with the given position at the given level.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Impure
    private static void log(@Nonnull Level level, @Nonnull CharSequence message, @Nullable SourcePosition position, @NonCaptured @Unmodified @Nullable Object... arguments) {
        Require.that(level != null).orThrow("The level may not be null.");
        Require.that(message != null).orThrow("The message may not be null.");
        
        Logger.log(level, message + (position != null ? " " + position : ""), null, arguments);
        if (level.getValue() >= Level.INFORMATION.getValue() && StaticProcessingEnvironment.environment.isSet()) {
            if (position == null) {
                StaticProcessingEnvironment.environment.get().getMessager().printMessage(levelToKind.get(level), Strings.format(message, arguments));
            } else if (position.getAnnotationValue() != null) {
                StaticProcessingEnvironment.environment.get().getMessager().printMessage(levelToKind.get(level), Strings.format(message, arguments), position.getElement(), position.getAnnotationMirror(), position.getAnnotationValue());
            } else if (position.getAnnotationMirror() != null) {
                StaticProcessingEnvironment.environment.get().getMessager().printMessage(levelToKind.get(level), Strings.format(message, arguments), position.getElement(), position.getAnnotationMirror());
            } else {
                StaticProcessingEnvironment.environment.get().getMessager().printMessage(levelToKind.get(level), Strings.format(message, arguments), position.getElement());
            }
        }
    }
    
    /* -------------------------------------------------- Error -------------------------------------------------- */
    
    /**
     * Logs the given message with the given position as an error.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Impure
    public static void error(@Nonnull CharSequence message, @Nullable SourcePosition position, @NonCaptured @Unmodified @Nullable Object... arguments) {
        log(Level.ERROR, message, position, arguments);
    }
    
    /**
     * Logs the given message as an error.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Impure
    public static void error(@Nonnull CharSequence message, @NonCaptured @Unmodified @Nullable Object... arguments) {
        log(Level.ERROR, message, null, arguments);
    }
    
    /* -------------------------------------------------- Warning -------------------------------------------------- */
    
    /**
     * Logs the given message with the given position as a warning.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Impure
    public static void warning(@Nonnull CharSequence message, @Nullable SourcePosition position, @NonCaptured @Unmodified @Nullable Object... arguments) {
        log(Level.WARNING, message, position, arguments);
    }
    
    /**
     * Logs the given message as a warning.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Impure
    public static void warning(@Nonnull CharSequence message, @NonCaptured @Unmodified @Nullable Object... arguments) {
        log(Level.WARNING, message, null, arguments);
    }
    
    /* -------------------------------------------------- Information -------------------------------------------------- */
    
    /**
     * Logs the given message with the given position as information.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Impure
    public static void information(@Nonnull CharSequence message, @Nullable SourcePosition position, @NonCaptured @Unmodified @Nullable Object... arguments) {
        log(Level.INFORMATION, message, position, arguments);
    }
    
    /**
     * Logs the given message as information.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Impure
    public static void information(@Nonnull CharSequence message, @NonCaptured @Unmodified @Nullable Object... arguments) {
        log(Level.INFORMATION, message, null, arguments);
    }
    
    /* -------------------------------------------------- Debugging -------------------------------------------------- */
    
    /**
     * Logs the given message with the given position for debugging.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Impure
    public static void debugging(@Nonnull CharSequence message, @Nullable SourcePosition position, @NonCaptured @Unmodified @Nullable Object... arguments) {
        log(Level.DEBUGGING, message, position, arguments);
    }
    
    /**
     * Logs the given message for debugging.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Impure
    public static void debugging(@Nonnull CharSequence message, @NonCaptured @Unmodified @Nullable Object... arguments) {
        log(Level.DEBUGGING, message, null, arguments);
    }
    
    /* -------------------------------------------------- Verbose -------------------------------------------------- */
    
    /**
     * Logs the given message with the given position only in verbose mode.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Impure
    public static void verbose(@Nonnull CharSequence message, @Nullable SourcePosition position, @NonCaptured @Unmodified @Nullable Object... arguments) {
        log(Level.VERBOSE, message, position, arguments);
    }
    
    /**
     * Logs the given message only in verbose mode.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Impure
    public static void verbose(@Nonnull CharSequence message, @NonCaptured @Unmodified @Nullable Object... arguments) {
        log(Level.VERBOSE, message, null, arguments);
    }
    
    /* -------------------------------------------------- Utility -------------------------------------------------- */
    
    /**
     * Logs the elements which are annotated with one of the given annotations of the given round environment.
     */
    @Impure
    public static void annotatedElements(@Nonnull FiniteIterable<@Nonnull ? extends TypeElement> annotations, @Nonnull RoundEnvironment roundEnvironment) {
        Require.that(annotations != null).orThrow("The annotations may not be null.");
        Require.that(roundEnvironment != null).orThrow("The round environment may not be null.");
        
        for (@Nonnull TypeElement annotation : annotations) {
            for (@Nonnull Element element : roundEnvironment.getElementsAnnotatedWith(annotation)) {
                ProcessingLog.information("Found $ on", SourcePosition.of(element), "@" + annotation.getSimpleName());
            }
        }
    }
    
    /**
     * Logs the root elements of the given round environment.
     */
    @Impure
    public static void rootElements(@Nonnull RoundEnvironment roundEnvironment) {
        Require.that(roundEnvironment != null).orThrow("The round environment may not be null.");
        
        for (@Nonnull Element rootElement : roundEnvironment.getRootElements()) {
            ProcessingLog.information("Found the " + rootElement.getKind().toString().toLowerCase() + " $.", rootElement.asType());
        }
    }
    
}
