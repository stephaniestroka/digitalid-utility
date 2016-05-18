package net.digitalid.utility.validation.validator;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.collaboration.annotations.TODO;
import net.digitalid.utility.collaboration.enumerations.Author;
import net.digitalid.utility.immutable.ImmutableSet;
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
public abstract class ValueAnnotationValidator extends AnnotationHandler implements ContractGenerator {
    
    /* -------------------------------------------------- Target Types -------------------------------------------------- */
    
    /**
     * Returns the types of values to which the surrounding annotation can be applied.
     */
    @Pure
    @TODO(task = "Change the return type to FiniteIterable.", date = "2016-05-18", author = Author.KASPAR_ETTER)
    public abstract @Nonnull ImmutableSet<Class<?>> getTargetTypes();
    
    /* -------------------------------------------------- Usage Check -------------------------------------------------- */
    
    @Pure
    @Override
    @TODO(task = "Instead of looping through the target types, use predicate matching.", date = "2016-05-18", author = Author.KASPAR_ETTER)
    public void checkUsage(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @NonCaptured @Modified @Nonnull ErrorLogger errorLogger) {
        boolean elementAssignableToTargetType = false;
        for (@Nonnull Class<?> targetType : getTargetTypes()) {
            if (ProcessingUtility.isAssignable(element, targetType)) { elementAssignableToTargetType = true; }
        }
        if (!elementAssignableToTargetType) {
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
