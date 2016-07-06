package net.digitalid.utility.processor;

import java.io.FileNotFoundException;
import java.lang.annotation.Annotation;
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
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.QualifiedNameable;
import javax.lang.model.element.TypeElement;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.annotations.state.Unmodifiable;
import net.digitalid.utility.circumfixes.Brackets;
import net.digitalid.utility.circumfixes.Quotes;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.logging.Log;
import net.digitalid.utility.logging.exceptions.InvalidConfigurationException;
import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processing.utility.StaticProcessingEnvironment;
import net.digitalid.utility.processor.annotations.SupportedAnnotations;
import net.digitalid.utility.string.Strings;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class is the parent of all custom annotation processors.
 */
@Mutable
public abstract class CustomProcessor implements Processor {
    
    /* -------------------------------------------------- Initialization -------------------------------------------------- */
    
    @Impure
    @Override
    public void init(@Nonnull ProcessingEnvironment processingEnvironment) throws IllegalStateException {
        StaticProcessingEnvironment.environment.set(processingEnvironment);
    }
    
    /* -------------------------------------------------- Utility -------------------------------------------------- */
    
    /**
     * Returns the project name based on the common prefix of the packages in the given round environment.
     */
    @Pure
    protected @Nonnull String getProjectName(@Nonnull RoundEnvironment roundEnvironment) {
        final @Nonnull Set<@Nonnull ? extends Element> rootElements = roundEnvironment.getRootElements();
        final @Nonnull List<@Nonnull String> qualifiedTypeNames = new ArrayList<>(rootElements.size());
        for (@Nonnull Element rootElement : rootElements) {
            if (rootElement.getKind().isClass() || rootElement.getKind().isInterface()) {
                qualifiedTypeNames.add(((QualifiedNameable) rootElement).getQualifiedName().toString());
            }
        }
        final @Nonnull String longestCommonPrefixWithDot = Strings.longestCommonPrefix(qualifiedTypeNames.toArray(new String[qualifiedTypeNames.size()]));
        return longestCommonPrefixWithDot.contains(".") ? longestCommonPrefixWithDot.substring(0, longestCommonPrefixWithDot.lastIndexOf('.')) : longestCommonPrefixWithDot;
    }
    
    /* -------------------------------------------------- Processing -------------------------------------------------- */
    
    /**
     * Processes the given annotations in the first round (that means on the non-generated files).
     * 
     * @see #process(java.util.Set, javax.annotation.processing.RoundEnvironment)
     */
    @Impure
    protected void processFirstRound(@Nonnull FiniteIterable<@Nonnull ? extends TypeElement> annotations, @Nonnull RoundEnvironment roundEnvironment) {
        ProcessingLog.annotatedElements(annotations, roundEnvironment);
    }
    
    /**
     * Stores whether this annotation processor is only interested in the first processing round.
     */
    private boolean onlyInterestedInFirstRound = false;
    
    /**
     * Processes the given annotations in the given round.
     * 
     * @param round the round of annotation processing, starting with zero in the first round.
     * 
     * @see #process(java.util.Set, javax.annotation.processing.RoundEnvironment)
     */
    @Impure
    protected void process(@Nonnull FiniteIterable<@Nonnull ? extends TypeElement> annotations, @Nonnull RoundEnvironment roundEnvironment, @NonNegative int round) {
        if (round == 0) { processFirstRound(annotations, roundEnvironment); }
        this.onlyInterestedInFirstRound = true;
    }
    
    /**
     * Returns whether the given annotations are claimed by this annotation processor with the given round environment.
     */
    @Pure
    protected boolean consumeAnnotations(@Nonnull FiniteIterable<@Nonnull ? extends TypeElement> annotations, @Nonnull RoundEnvironment roundEnvironment) {
        return false;
    }
    
    /**
     * Stores the round of processing, starting with zero in the first round.
     */
    private int round = 0;
    
    @Impure
    @Override
    public final boolean process(@NonCaptured @Unmodified @Nonnull Set<@Nonnull ? extends TypeElement> typeElements, @Nonnull RoundEnvironment roundEnvironment) {
        try {
            try {
                ProcessingLog.initialize(getClass().getSimpleName());
            } catch (@Nonnull InvalidConfigurationException exception) {
                Log.error("The logging configuration is invalid:", exception);
            } catch (@Nonnull FileNotFoundException exception) {
                Log.error("Could not find the logging file:", exception);
            }
            
            if (round == 0) {
                final @Nonnull String projectName = getProjectName(roundEnvironment);
                ProcessingLog.information(getClass().getSimpleName() + " invoked" + (projectName.isEmpty() ? "" : " for project " + Quotes.inSingle(projectName)) + ":\n");
            }
            
            final @Nonnull FiniteIterable<@Nonnull ? extends TypeElement> annotations = FiniteIterable.of(typeElements);
            final boolean annotationsConsumed = consumeAnnotations(annotations, roundEnvironment);
            if (round == 0 || !onlyInterestedInFirstRound) {
                ProcessingLog.information("Process " + annotations.join(Brackets.SQUARE) + " in the " + Strings.getOrdinal(round + 1) + " round.");
                process(annotations, roundEnvironment, round++);
                ProcessingLog.information("Finish " + (annotationsConsumed ? "with" : "without") + " claiming the annotations.\n" + (roundEnvironment.processingOver() || onlyInterestedInFirstRound ? "\n" : ""));
            }
            
            return annotationsConsumed;
        } catch (@Nonnull Throwable throwable) {
            Log.error("The compilation failed due to the following problem:", throwable);
            throw throwable;
        }
    }
    
    /* -------------------------------------------------- Annotations -------------------------------------------------- */
    
    /**
     * Converts the given array to an unmodifiable set.
     */
    @Pure
    protected @Capturable <T> @Unmodifiable @Nonnull Set<@Nonnull T> convertArrayToUnmodifiableSet(@NonCaptured @Unmodified @Nonnull @NonNullableElements T[] array) {
        final @Nonnull @NonNullableElements Set<T> set = new HashSet<>(array.length);
        for (@Nonnull T s : array) { set.add(s); }
        return Collections.unmodifiableSet(set);
    }
    
    @Pure
    @Override
    public @Capturable @Unmodifiable @Nonnull Set<@Nonnull String> getSupportedOptions() {
        final @Nullable SupportedOptions supportedOptions = getClass().getAnnotation(SupportedOptions.class);
        if (supportedOptions != null) {
            return convertArrayToUnmodifiableSet(supportedOptions.value());
        } else {
            return Collections.emptySet();
        }
    }
    
    @Pure
    @Override
    public @Capturable @Unmodifiable @Nonnull Set<@Nonnull String> getSupportedAnnotationTypes() {
        final @Nullable SupportedAnnotations supportedAnnotations = getClass().getAnnotation(SupportedAnnotations.class);
        if (supportedAnnotations != null) {
            final @Nonnull Set<@Nonnull String> result = new HashSet<>(supportedAnnotations.value().length + supportedAnnotations.prefix().length);
            for (@Nonnull Class<? extends Annotation> annotation : supportedAnnotations.value()) {
                result.add(annotation.getCanonicalName());
            }
            for (@Nonnull String prefix : supportedAnnotations.prefix()) {
                result.add(prefix + "*");
            }
            return Collections.unmodifiableSet(result);
        } else {
            ProcessingLog.error("No '@SupportedAnnotations' annotation found on $.", getClass().getName());
            return Collections.emptySet();
        }
    }
    
    @Pure
    @Override
    public @Nonnull SourceVersion getSupportedSourceVersion() {
        final @Nullable SupportedSourceVersion supportedSourceVersion = getClass().getAnnotation(SupportedSourceVersion.class);
        if (supportedSourceVersion != null) {
            return supportedSourceVersion.value();
        } else {
            return SourceVersion.latestSupported();
        }
    }
    
    @Pure
    @Override
    public @Nonnull FiniteIterable<? extends Completion> getCompletions(@Nonnull Element element, @Nonnull AnnotationMirror annotation, @Nonnull ExecutableElement member, @Nonnull String userText) {
        return FiniteIterable.of();
    }
    
}
