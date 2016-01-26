package net.digitalid.utility.processor;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.Completion;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import net.digitalid.utility.configuration.InitializationError;
import net.digitalid.utility.logging.Caller;
import net.digitalid.utility.logging.Level;
import net.digitalid.utility.logging.Version;
import net.digitalid.utility.logging.logger.FileLogger;
import net.digitalid.utility.logging.logger.Logger;

/**
 * This class is the parent of all custom annotation processors.
 */
public abstract class CustomProcessor implements Processor {
    
    /* -------------------------------------------------- Environment -------------------------------------------------- */
    
    /**
     * Stores the processing environment of this processor.
     */
    private ProcessingEnvironment processingEnvironment;
    
    /**
     * Returns the non-nullable processing environment of this processor.
     * 
     * @throws InitializationError if the processing environment was not set.
     */
    protected ProcessingEnvironment getProcessingEnvironment() throws InitializationError {
        if (processingEnvironment == null) {
            throw InitializationError.of("The processing environment was not set.");
        }
        return processingEnvironment;
    }
    
    /* -------------------------------------------------- Logging -------------------------------------------------- */
    
    /**
     * Sets the output file of the logger.
     */
    private static void setUpLogging() {
        Logger.configuration.set(FileLogger.of("target/processor-logs/processor.log"));
        Level.configuration.set(Level.VERBOSE);
        Version.configuration.set("1.0.0");
        Caller.configuration.set(6);
    }
    
    static { setUpLogging(); }
    
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
    
    /**
     * Logs the given non-nullable message with the given nullable position at the given non-nullable level.
     */
    private void log(Level level, CharSequence message, SourcePosition position) {
        assert level != null : "The given level is not null.";
        assert message != null : "The given message is not null.";
        
        Logger.log(level, getClass().getSimpleName() + ": " + message.toString() + (position != null ? " " + position : ""), null);
        if (level.getValue() >= Level.INFORMATION.getValue() && processingEnvironment != null) {
            if (position == null) {
                processingEnvironment.getMessager().printMessage(levelToKind.get(level), message);
            } else if (position.getAnnotationValue() != null) {
                processingEnvironment.getMessager().printMessage(levelToKind.get(level), message, position.getElement(), position.getAnnotationMirror(), position.getAnnotationValue());
            } else if (position.getAnnotationMirror() != null) {
                processingEnvironment.getMessager().printMessage(levelToKind.get(level), message, position.getElement(), position.getAnnotationMirror());
            } else {
                processingEnvironment.getMessager().printMessage(levelToKind.get(level), message, position.getElement());
            }
        }
    }
    
    /**
     * Logs the given non-nullable message with the given nullable position as an error.
     */
    protected void error(CharSequence message, SourcePosition position) {
        log(Level.ERROR, message, position);
    }
    
    /**
     * Logs the given non-nullable message as an error.
     */
    protected void error(CharSequence message) {
        log(Level.ERROR, message, null);
    }
    
    /**
     * Logs the given non-nullable message with the given nullable position as a warning.
     */
    protected void warning(CharSequence message, SourcePosition position) {
        log(Level.WARNING, message, position);
    }
    
    /**
     * Logs the given non-nullable message as a warning.
     */
    protected void warning(CharSequence message) {
        log(Level.WARNING, message, null);
    }
    
    /**
     * Logs the given non-nullable message with the given nullable position as information.
     */
    protected void information(CharSequence message, SourcePosition position) {
        log(Level.INFORMATION, message, position);
    }
    
    /**
     * Logs the given non-nullable message as information.
     */
    protected void information(CharSequence message) {
        log(Level.INFORMATION, message, null);
    }
    
    /**
     * Logs the given non-nullable message with the given nullable position for debugging.
     */
    protected void debugging(CharSequence message, SourcePosition position) {
        log(Level.DEBUGGING, message, position);
    }
    
    /**
     * Logs the given non-nullable message for debugging.
     */
    protected void debugging(CharSequence message) {
        log(Level.DEBUGGING, message, null);
    }
    
    /**
     * Logs the given non-nullable message with the given nullable position only in verbose mode.
     */
    protected void verbose(CharSequence message, SourcePosition position) {
        log(Level.VERBOSE, message, position);
    }
    
    /**
     * Logs the given non-nullable message only in verbose mode.
     */
    protected void verbose(CharSequence message) {
        log(Level.VERBOSE, message, null);
    }
    
    /* -------------------------------------------------- Initialization -------------------------------------------------- */
    
    @Override
    public void init(ProcessingEnvironment processingEnvironment) throws IllegalStateException {
        assert processingEnvironment != null : "The given processing environment is not null.";
        
        if (this.processingEnvironment != null) {
            throw new IllegalStateException("Cannot call init more than once.");
        }
        this.processingEnvironment = processingEnvironment;
        
        verbose("The annotation processor is initialized.");
    }
    
    /* -------------------------------------------------- Processing -------------------------------------------------- */
    
    /**
     * @see #process(java.util.Set, javax.annotation.processing.RoundEnvironment)
     */
    protected abstract boolean processAll(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment);
    
    @Override
    public final boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        verbose("The annotation processing is started for " + annotations + ".");
        final boolean result = processAll(annotations, roundEnvironment);
        verbose("The annotation processing is finished " + (result ? "with" : "without") + " claiming the annotations.");
        return result;
    }
    
    /* -------------------------------------------------- Annotations -------------------------------------------------- */
    
    /**
     * Converts the given non-nullable array to an unmodifiable set.
     * 
     * @require array != null : "The given array is not null.";
     * @ensure return != null : "The returned set is not null.";
     */
    protected static <T> Set<T> convertArrayToUnmodifiableSet(T[] array) {
        assert array != null : "The given array is not null.";
        
        final Set<T> set = new HashSet<>(array.length);
        for (final T s : array) { set.add(s); }
        return Collections.unmodifiableSet(set);
    }
    
    @Override
    public Set<String> getSupportedOptions() {
        final SupportedOptions supportedOptions = getClass().getAnnotation(SupportedOptions.class);
        if (supportedOptions == null) {
            return Collections.emptySet();
        } else {
            return convertArrayToUnmodifiableSet(supportedOptions.value());
        }
    }
    
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        final SupportedAnnotationTypes supportedAnnotationTypes = getClass().getAnnotation(SupportedAnnotationTypes.class);
        if  (supportedAnnotationTypes != null) {
            return convertArrayToUnmodifiableSet(supportedAnnotationTypes.value());
        } else {
            error("No SupportedAnnotationTypes annotation found on " + getClass().getName() + ".");
            return Collections.emptySet();
        }
    }
    
    @Override
    public SourceVersion getSupportedSourceVersion() {
        final SupportedSourceVersion supportedSourceVersion = getClass().getAnnotation(SupportedSourceVersion.class);
        if (supportedSourceVersion != null) {
            return supportedSourceVersion.value();
        } else {
            return SourceVersion.latestSupported();
        }
    }
    
    @Override
    public Iterable<? extends Completion> getCompletions(Element element, AnnotationMirror annotation, ExecutableElement member, String userText) {
        return Collections.emptyList();
    }
    
    /* -------------------------------------------------- Utility -------------------------------------------------- */
    
}
