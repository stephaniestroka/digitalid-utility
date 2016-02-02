package net.digitalid.utility.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
import javax.lang.model.element.QualifiedNameable;
import javax.lang.model.element.TypeElement;

import net.digitalid.utility.logging.processing.AnnotationLog;
import net.digitalid.utility.logging.processing.AnnotationProcessing;
import net.digitalid.utility.string.NumberToString;
import net.digitalid.utility.string.StringPrefix;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class is the parent of all custom annotation processors.
 */
@Mutable
public abstract class CustomProcessor implements Processor {
    
    /* -------------------------------------------------- Initialization -------------------------------------------------- */
    
    @Override
    public void init(@Nonnull ProcessingEnvironment processingEnvironment) throws IllegalStateException {
        AnnotationProcessing.environment.set(processingEnvironment);
    }
    
    /* -------------------------------------------------- Processing -------------------------------------------------- */
    
    /**
     * Processes the given annotations in the first round and returns whether these annotation are claimed by this processor.
     * 
     * @see #process(java.util.Set, javax.annotation.processing.RoundEnvironment)
     */
    protected boolean processFirstRound(@Nonnull @NonNullableElements Set<? extends TypeElement> annotations, @Nonnull RoundEnvironment roundEnvironment) {
        for (@Nonnull TypeElement annotation : annotations) {
            for (@Nonnull Element element : roundEnvironment.getElementsAnnotatedWith(annotation)) {
                AnnotationLog.debugging("Found '@" + annotation.getSimpleName() + "' on '" + element.getEnclosingElement().getSimpleName() + "#" + element + "'.");
            }
        }
        return false;
    }
    
    /**
     * Stores whether this annotation processor is only interested in the first processing round.
     */
    private boolean onlyInterestedInFirstRound = false;
    
    /**
     * Processes the given annotations in the given round and returns whether these annotation are claimed by this processor.
     * 
     * @param round the round of annotation processing, starting with zero in the first round.
     * 
     * @see #process(java.util.Set, javax.annotation.processing.RoundEnvironment)
     */
    protected boolean process(@Nonnull @NonNullableElements Set<? extends TypeElement> annotations, @Nonnull RoundEnvironment roundEnvironment, @NonNegative int round) {
        this.onlyInterestedInFirstRound = true;
        if (round == 0) { return processFirstRound(annotations, roundEnvironment); } else { return false; }
    }
    
    /**
     * Stores the round of processing, starting with zero in the first round.
     */
    private int round = 0;
    
    @Override
    public final boolean process(@Nonnull @NonNullableElements Set<? extends TypeElement> annotations, @Nonnull RoundEnvironment roundEnvironment) {
        AnnotationLog.setUp(getClass().getSimpleName());
        
        if (round == 0) { 
            final @Nonnull Set<? extends Element> rootElements = roundEnvironment.getRootElements();
            final @Nonnull List<String> names = new ArrayList<>(rootElements.size());
            for (@Nonnull Element rootElement : rootElements) {
                if (rootElement.getKind().isClass() || rootElement.getKind().isInterface()) {
                    names.add(((QualifiedNameable) rootElement).getQualifiedName().toString());
                }
            }
            final @Nonnull String nameWithDot = StringPrefix.longestCommonPrefix(names.toArray(new String[names.size()]));
            final @Nonnull String name = nameWithDot.endsWith(".") ? nameWithDot.substring(0, nameWithDot.length() - 1) : nameWithDot;
            AnnotationLog.information(getClass().getSimpleName() + " invoked " + (name.isEmpty() ? "" : " for project '" + name + "'") + ":\n");
        }
        
        if (onlyInterestedInFirstRound && round > 0) { return false; }
        
        AnnotationLog.information("Process " + annotations + " in the " + NumberToString.getOrdinal(round + 1) + " round.");
        final boolean result = process(annotations, roundEnvironment, round);
        AnnotationLog.information("Finish " + (result ? "with" : "without") + " claiming the annotations.\n" + (roundEnvironment.processingOver() || onlyInterestedInFirstRound ? "\n" : ""));
        this.round += 1;
        return result;
    }
    
    /* -------------------------------------------------- Annotations -------------------------------------------------- */
    
    /**
     * Converts the given non-nullable array to an unmodifiable set.
     */
    @Pure
    protected static @Nonnull <T> Set<T> convertArrayToUnmodifiableSet(@Nonnull T[] array) {
        final @Nonnull Set<T> set = new HashSet<>(array.length);
        for (@Nullable T s : array) { set.add(s); }
        return Collections.unmodifiableSet(set);
    }
    
    @Pure
    @Override
    public @Nonnull Set<String> getSupportedOptions() {
        final @Nullable SupportedOptions supportedOptions = getClass().getAnnotation(SupportedOptions.class);
        if (supportedOptions != null) {
            return convertArrayToUnmodifiableSet(supportedOptions.value());
        } else {
            return Collections.emptySet();
        }
    }
    
    @Pure
    @Override
    public @Nonnull Set<String> getSupportedAnnotationTypes() {
        final @Nullable SupportedAnnotationTypes supportedAnnotationTypes = getClass().getAnnotation(SupportedAnnotationTypes.class);
        if  (supportedAnnotationTypes != null) {
            return convertArrayToUnmodifiableSet(supportedAnnotationTypes.value());
        } else {
            AnnotationLog.error("No SupportedAnnotationTypes annotation found on " + getClass().getName() + ".");
            return Collections.emptySet();
        }
    }
    
    @Pure
    @Override
    public SourceVersion getSupportedSourceVersion() {
        final @Nullable SupportedSourceVersion supportedSourceVersion = getClass().getAnnotation(SupportedSourceVersion.class);
        if (supportedSourceVersion != null) {
            return supportedSourceVersion.value();
        } else {
            return SourceVersion.latestSupported();
        }
    }
    
    @Pure
    @Override
    public @Nonnull Iterable<? extends Completion> getCompletions(@Nonnull Element element, @Nonnull AnnotationMirror annotation, @Nonnull ExecutableElement member, @Nonnull String userText) {
        return Collections.emptyList();
    }
    
}
