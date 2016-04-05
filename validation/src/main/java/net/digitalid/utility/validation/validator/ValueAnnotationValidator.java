package net.digitalid.utility.validation.validator;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.immutable.collections.ImmutableSet;
import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.validation.annotations.meta.ValueValidator;
import net.digitalid.utility.validation.annotations.type.Stateless;

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
    public abstract @Nonnull ImmutableSet<Class<?>> getTargetTypes();
    
    /* -------------------------------------------------- Usage Check -------------------------------------------------- */
    
    @Pure
    @Override
    public void checkUsage(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror) {
        boolean elementAssignableToTargetType = false;
        for (@Nonnull Class<?> targetType : getTargetTypes()) {
            ProcessingLog.debugging("Checking whether element $ is assignable to targetType $", element, targetType);
            if (ProcessingUtility.isAssignable(element, targetType)) { elementAssignableToTargetType = true; }
        }
        if (!elementAssignableToTargetType) {
            ProcessingLog.error("The element $ is not assignable to a target type of $.", SourcePosition.of(element, annotationMirror), element, getAnnotationNameWithLeadingAt());
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
