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
import net.digitalid.utility.string.NumberToString;
import net.digitalid.utility.string.StringPrefix;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class is the parent of all custom annotation processors.
 */
@Mutable
public abstract class CustomProcessor implements Processor {
    
    /* -------------------------------------------------- Initialization -------------------------------------------------- */
    
    @Override
    public void init(@Nonnull ProcessingEnvironment processingEnvironment) throws IllegalStateException {
        if (AnnotationProcessor.environment.isSet()) {
            throw new IllegalStateException("Cannot call init more than once.");
        }
        AnnotationProcessor.environment.set(processingEnvironment);
        AnnotationLog.setUp(getClass().getSimpleName());
    }
    
    /* -------------------------------------------------- Processing -------------------------------------------------- */
    
    /**
     * Processes the given annotations in the first round and returns whether these annotation are claimed by this processor.
     * 
     * @see #process(java.util.Set, javax.annotation.processing.RoundEnvironment)
     */
    protected boolean processFirstRound(@Nonnull @NonNullableElements Set<? extends TypeElement> annotations, @Nonnull RoundEnvironment roundEnvironment) {
        AnnotationLog.rootElements(roundEnvironment);
        return false;
    }
    
    /**
     * Processes the given annotations in the given round and returns whether these annotation are claimed by this processor.
     * 
     * @param round the round of annotation processing, starting with zero in the first round.
     * 
     * @see #process(java.util.Set, javax.annotation.processing.RoundEnvironment)
     */
    protected boolean process(@Nonnull @NonNullableElements Set<? extends TypeElement> annotations, @Nonnull RoundEnvironment roundEnvironment, @NonNegative int round) {
        if (round == 0) { return processFirstRound(annotations, roundEnvironment); } else { return false; }
    }
    
    /**
     * Stores the round of processing, starting with zero in the first round.
     */
    private int round = 0;
    
    @Override
    public final boolean process(@Nonnull @NonNullableElements Set<? extends TypeElement> annotations, @Nonnull RoundEnvironment roundEnvironment) {
        if (round == 0) { 
            final @Nonnull Set<? extends Element> rootElements = roundEnvironment.getRootElements();
            final @Nonnull String[] names = new String[rootElements.size()];
            int i = 0;
            for (@Nonnull Element rootElement : rootElements) {
                names[i++] = rootElement.asType().toString();
            }
            final @Nonnull String nameWithDot = StringPrefix.longestCommonPrefix(names);
            final @Nonnull String name = nameWithDot.endsWith(".") ? nameWithDot.substring(0, nameWithDot.length() - 1) : nameWithDot;
            AnnotationLog.information(getClass().getSimpleName() + " invoked " + (name.isEmpty() ? "" : " for project " + name) + ":\n");
        }
        
        AnnotationLog.information("Process " + annotations + " in the " + NumberToString.getOrdinal(round + 1) + " round.");
        final boolean result = process(annotations, roundEnvironment, round);
        AnnotationLog.information("Finish " + (result ? "with" : "without") + " claiming the annotations.\n" + (roundEnvironment.processingOver() ? "\n" : ""));
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
