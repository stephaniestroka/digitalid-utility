package net.digitalid.utility.logging.processing;

import java.util.HashMap;
import java.util.Map;

import javax.tools.Diagnostic;

import net.digitalid.utility.logging.Caller;
import net.digitalid.utility.logging.Level;
import net.digitalid.utility.logging.Version;
import net.digitalid.utility.logging.logger.FileLogger;
import net.digitalid.utility.logging.logger.Logger;

/**
 * This class makes it easier to log messages during annotation processing.
 */
public class AnnotationLog {
    
    /* -------------------------------------------------- Setup -------------------------------------------------- */
    
    /**
     * Sets the output file of the logger.
     */
    public static void setUp() {
        Logger.logger.set(FileLogger.with("target/processor-logs/processor.log"));
        Level.threshold.set(Level.VERBOSE);
        Version.string.set("1.0.0");
        Caller.index.set(6);
    }
    
    /* -------------------------------------------------- Mapping -------------------------------------------------- */
    
    /**
     * Stores a mapping from this library's logging levels to the corresponding diagnostic kind.
     */
    private static final Map<Level, Diagnostic.Kind> levelToKind = new HashMap<>(5);
    
    static {
        levelToKind.put(Level.VERBOSE, Diagnostic.Kind.OTHER);
        levelToKind.put(Level.DEBUGGING, Diagnostic.Kind.OTHER);
        levelToKind.put(Level.INFORMATION, Diagnostic.Kind.NOTE);
        levelToKind.put(Level.WARNING, Diagnostic.Kind.WARNING);
        levelToKind.put(Level.ERROR, Diagnostic.Kind.ERROR);
    }
    
    /* -------------------------------------------------- Logging -------------------------------------------------- */
    
    /**
     * Logs the given non-nullable message with the given nullable position at the given non-nullable level.
     */
    private static void log(Level level, CharSequence message, SourcePosition position) {
        assert level != null : "The given level is not null.";
        assert message != null : "The given message is not null.";
        
        Logger.log(level, message.toString() + (position != null ? " " + position : ""), null);
        if (level.getValue() >= Level.INFORMATION.getValue() && AnnotationProcessor.environment.isSet()) {
            if (position == null) {
                AnnotationProcessor.environment.get().getMessager().printMessage(levelToKind.get(level), message);
            } else if (position.getAnnotationValue() != null) {
                AnnotationProcessor.environment.get().getMessager().printMessage(levelToKind.get(level), message, position.getElement(), position.getAnnotationMirror(), position.getAnnotationValue());
            } else if (position.getAnnotationMirror() != null) {
                AnnotationProcessor.environment.get().getMessager().printMessage(levelToKind.get(level), message, position.getElement(), position.getAnnotationMirror());
            } else {
                AnnotationProcessor.environment.get().getMessager().printMessage(levelToKind.get(level), message, position.getElement());
            }
        }
    }
    
    /* -------------------------------------------------- Error -------------------------------------------------- */
    
    /**
     * Logs the given non-nullable message with the given nullable position as an error.
     */
    public static void error(CharSequence message, SourcePosition position) {
        log(Level.ERROR, message, position);
    }
    
    /**
     * Logs the given non-nullable message as an error.
     */
    public static void error(CharSequence message) {
        log(Level.ERROR, message, null);
    }
    
    /* -------------------------------------------------- Warning -------------------------------------------------- */
    
    /**
     * Logs the given non-nullable message with the given nullable position as a warning.
     */
    public static void warning(CharSequence message, SourcePosition position) {
        log(Level.WARNING, message, position);
    }
    
    /**
     * Logs the given non-nullable message as a warning.
     */
    public static void warning(CharSequence message) {
        log(Level.WARNING, message, null);
    }
    
    /* -------------------------------------------------- Information -------------------------------------------------- */
    
    /**
     * Logs the given non-nullable message with the given nullable position as information.
     */
    public static void information(CharSequence message, SourcePosition position) {
        log(Level.INFORMATION, message, position);
    }
    
    /**
     * Logs the given non-nullable message as information.
     */
    public static void information(CharSequence message) {
        log(Level.INFORMATION, message, null);
    }
    
    /* -------------------------------------------------- Debugging -------------------------------------------------- */
    
    /**
     * Logs the given non-nullable message with the given nullable position for debugging.
     */
    public static void debugging(CharSequence message, SourcePosition position) {
        log(Level.DEBUGGING, message, position);
    }
    
    /**
     * Logs the given non-nullable message for debugging.
     */
    public static void debugging(CharSequence message) {
        log(Level.DEBUGGING, message, null);
    }
    
    /* -------------------------------------------------- Verbose -------------------------------------------------- */
    
    /**
     * Logs the given non-nullable message with the given nullable position only in verbose mode.
     */
    public static void verbose(CharSequence message, SourcePosition position) {
        log(Level.VERBOSE, message, position);
    }
    
    /**
     * Logs the given non-nullable message only in verbose mode.
     */
    public static void verbose(CharSequence message) {
        log(Level.VERBOSE, message, null);
    }
    
}
