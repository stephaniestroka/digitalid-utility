package net.digitalid.utility.validation.validator;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.validation.annotations.meta.ValueValidator;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.processing.ErrorLogger;

/**
 * A value annotation validator validates the (return) value of the annotated variable (or method).
 * 
 * @see ValueValidator
 */
@Stateless
public interface ValueAnnotationValidator extends AnnotationHandler, ContractGenerator {
    
    /* -------------------------------------------------- Target Types -------------------------------------------------- */
    
    public static final @Nonnull FiniteIterable<@Nonnull Class<?>> targetTypes = FiniteIterable.of(Object.class);
    
    /**
     * Returns the types of values to which the surrounding annotation can be applied.
     */
    @Pure
    public default @Nonnull FiniteIterable<@Nonnull Class<?>> getTargetTypes() {
        return targetTypes;
    }
    
    /* -------------------------------------------------- Usage Check -------------------------------------------------- */
    
    @Pure
    @Override
    public default void checkUsage(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @NonCaptured @Modified @Nonnull ErrorLogger errorLogger) {
        if (getTargetTypes().matchNone(targetType -> ProcessingUtility.isRawlyAssignable(element, targetType))) {
            errorLogger.log("The element $ is not assignable to a target type of $.", SourcePosition.of(element, annotationMirror), element, getAnnotationNameWithLeadingAt());
        }
    }
    
    /* -------------------------------------------------- Utility -------------------------------------------------- */
    
    /**
     * Returns the name of the variable whose value is to be validated.
     */
    @Pure
    public static @Nonnull String getName(@Nonnull Element element) {
        return element.getKind() == ElementKind.METHOD ? "result" : element.getSimpleName().toString();
    }
    
}
