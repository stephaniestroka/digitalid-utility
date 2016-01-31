package net.digitalid.utility.processor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;
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

import net.digitalid.utility.logging.processing.AnnotationLog;
import net.digitalid.utility.logging.processing.AnnotationProcessor;

/**
 * This class is the parent of all custom annotation processors.
 */
public abstract class CustomProcessor implements Processor {
    
    /* -------------------------------------------------- Utility -------------------------------------------------- */
    
    /**
     * Returns the name of this annotation processor.
     */
    protected @Nonnull String getName() {
        return getClass().getSimpleName();
    }
    
    /**
     * Logs the root elements of the given round environment.
     */
    protected void logRootElements(@Nonnull RoundEnvironment roundEnvironment) {
        for (final Element rootElement : roundEnvironment.getRootElements()) {
            AnnotationLog.information(rootElement.asType().toString() + " of kind " + rootElement.getKind());
        }
    }
    
    /* -------------------------------------------------- Initialization -------------------------------------------------- */
    
    @Override
    public void init(@Nonnull ProcessingEnvironment processingEnvironment) throws IllegalStateException {
        assert processingEnvironment != null : "The given processing environment is not null.";
        
        if (AnnotationProcessor.environment.isSet()) {
            throw new IllegalStateException("Cannot call init more than once.");
        }
        AnnotationProcessor.environment.set(processingEnvironment);
        
        AnnotationLog.verbose(getName() + " is initialized.");
    }
    
    /* -------------------------------------------------- Processing -------------------------------------------------- */
    
    /**
     * Processes the given annotations in the first round and returns whether these annotation are claimed by this processor.
     * 
     * @see #process(java.util.Set, javax.annotation.processing.RoundEnvironment)
     */
    protected boolean processFirstRound(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        logRootElements(roundEnvironment);
        return false;
    }
    
    /**
     * Processes the given annotations in the given round and returns whether these annotation are claimed by this processor.
     * 
     * @param round the round of annotation processing, starting with zero in the first round.
     * 
     * @see #process(java.util.Set, javax.annotation.processing.RoundEnvironment)
     */
    protected boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment, int round) {
        if (round == 0) { return processFirstRound(annotations, roundEnvironment); } else { return false; }
    }
    
    /**
     * Stores the round of processing, starting with zero in the first round.
     */
    private int round = 0;
    
    @Override
    public final boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        AnnotationLog.verbose(getName() + " starts processing " + annotations + " in the " + round + " round.");
        final boolean result = process(annotations, roundEnvironment, round);
        AnnotationLog.verbose(getName() + " finishes " + (result ? "with" : "without") + " claiming the annotations.");
        this.round += 1;
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
            AnnotationLog.error("No SupportedAnnotationTypes annotation found on " + getClass().getName() + ".");
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
    
}
