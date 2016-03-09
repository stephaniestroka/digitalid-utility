package net.digitalid.utility.logging.processing;

import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.immutable.collections.ImmutableMap;
import net.digitalid.utility.logging.Caller;
import net.digitalid.utility.logging.Level;
import net.digitalid.utility.logging.logger.FileLogger;
import net.digitalid.utility.logging.logger.Logger;
import net.digitalid.utility.string.FormatString;

/**
 * This class makes it easier to log messages during annotation processing.
 */
public class ProcessingLog {
    
    /* -------------------------------------------------- Setup -------------------------------------------------- */
    
    /**
     * Sets the output file of the logger to the given non-nullable name.
     */
    public static void setUp(String name) {
        Require.that(name != null).orThrow("The name may not be null.");
        
        Logger.logger.set(FileLogger.with("target/processor-logs/" + name + ".log"));
        Level.threshold.set(Level.VERBOSE);
        Caller.index.set(6);
    }
    
    /* -------------------------------------------------- Mapping -------------------------------------------------- */
    
    /**
     * Stores a mapping from this library's logging levels to the corresponding diagnostic kind.
     */
    private static final ImmutableMap<Level, Diagnostic.Kind> levelToKind = ImmutableMap.with(Level.VERBOSE, Diagnostic.Kind.OTHER).with(Level.DEBUGGING, Diagnostic.Kind.OTHER).with(Level.INFORMATION, Diagnostic.Kind.NOTE).with(Level.WARNING, Diagnostic.Kind.WARNING).with(Level.ERROR, Diagnostic.Kind.ERROR).build();
    
    /* -------------------------------------------------- Logging -------------------------------------------------- */
    
    /**
     * Logs the given non-nullable message with the given nullable position at the given non-nullable level.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    private static void log(Level level, CharSequence message, SourcePosition position, Object... arguments) {
        Require.that(level != null).orThrow("The level may not be null.");
        Require.that(message != null).orThrow("The message may not be null.");
        
        Logger.log(level, message + (position != null ? " " + position : ""), null, arguments);
        if (level.getValue() >= Level.INFORMATION.getValue() && StaticProcessingEnvironment.environment.isSet()) {
            if (position == null) {
                StaticProcessingEnvironment.environment.get().getMessager().printMessage(levelToKind.get(level), FormatString.format(message, arguments));
            } else if (position.getAnnotationValue() != null) {
                StaticProcessingEnvironment.environment.get().getMessager().printMessage(levelToKind.get(level), FormatString.format(message, arguments), position.getElement(), position.getAnnotationMirror(), position.getAnnotationValue());
            } else if (position.getAnnotationMirror() != null) {
                StaticProcessingEnvironment.environment.get().getMessager().printMessage(levelToKind.get(level), FormatString.format(message, arguments), position.getElement(), position.getAnnotationMirror());
            } else {
                StaticProcessingEnvironment.environment.get().getMessager().printMessage(levelToKind.get(level), FormatString.format(message, arguments), position.getElement());
            }
        }
    }
    
    /* -------------------------------------------------- Error -------------------------------------------------- */
    
    /**
     * Logs the given non-nullable message with the given nullable position as an error.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    public static void error(CharSequence message, SourcePosition position, Object... arguments) {
        log(Level.ERROR, message, position);
    }
    
    /**
     * Logs the given non-nullable message as an error.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    public static void error(CharSequence message, Object... arguments) {
        log(Level.ERROR, message, null);
    }
    
    /* -------------------------------------------------- Warning -------------------------------------------------- */
    
    /**
     * Logs the given non-nullable message with the given nullable position as a warning.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    public static void warning(CharSequence message, SourcePosition position, Object... arguments) {
        log(Level.WARNING, message, position);
    }
    
    /**
     * Logs the given non-nullable message as a warning.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    public static void warning(CharSequence message, Object... arguments) {
        log(Level.WARNING, message, null);
    }
    
    /* -------------------------------------------------- Information -------------------------------------------------- */
    
    /**
     * Logs the given non-nullable message with the given nullable position as information.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    public static void information(CharSequence message, SourcePosition position, Object... arguments) {
        log(Level.INFORMATION, message, position);
    }
    
    /**
     * Logs the given non-nullable message as information.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    public static void information(CharSequence message, Object... arguments) {
        log(Level.INFORMATION, message, null);
    }
    
    /* -------------------------------------------------- Debugging -------------------------------------------------- */
    
    /**
     * Logs the given non-nullable message with the given nullable position for debugging.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    public static void debugging(CharSequence message, SourcePosition position, Object... arguments) {
        log(Level.DEBUGGING, message, position);
    }
    
    /**
     * Logs the given non-nullable message for debugging.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    public static void debugging(CharSequence message, Object... arguments) {
        log(Level.DEBUGGING, message, null);
    }
    
    /* -------------------------------------------------- Verbose -------------------------------------------------- */
    
    /**
     * Logs the given non-nullable message with the given nullable position only in verbose mode.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    public static void verbose(CharSequence message, SourcePosition position, Object... arguments) {
        log(Level.VERBOSE, message, position);
    }
    
    /**
     * Logs the given non-nullable message only in verbose mode.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    public static void verbose(CharSequence message, Object... arguments) {
        log(Level.VERBOSE, message, null);
    }
    
    /* -------------------------------------------------- Utility -------------------------------------------------- */
    
    /**
     * Logs the elements which are annotated with one of the given annotations of the given non-nullable round environment.
     * 
     * @require annotations != null : "The annotations may not be null.";
     * @require roundEnvironment != null : "The round environment may not be null.";
     */
    public static void annotatedElements(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        Require.that(annotations != null).orThrow("The annotations may not be null.");
        Require.that(roundEnvironment != null).orThrow("The round environment may not be null.");
        
        for (TypeElement annotation : annotations) {
            for (Element element : roundEnvironment.getElementsAnnotatedWith(annotation)) {
                ProcessingLog.information("Found $ on", SourcePosition.of(element), "@" + annotation.getSimpleName());
            }
        }
    }
    
    /**
     * Logs the root elements of the given non-nullable round environment.
     * 
     * @require roundEnvironment != null : "The round environment may not be null.";
     */
    public static void rootElements(RoundEnvironment roundEnvironment) {
        Require.that(roundEnvironment != null).orThrow("The round environment may not be null.");
        
        for (Element rootElement : roundEnvironment.getRootElements()) {
            ProcessingLog.information("Found the " + rootElement.getKind().toString().toLowerCase() + " $.", rootElement.asType());
        }
    }
    
}
