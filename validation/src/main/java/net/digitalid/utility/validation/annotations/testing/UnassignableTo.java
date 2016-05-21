package net.digitalid.utility.validation.annotations.testing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.validation.annotations.meta.ValueValidator;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.processing.ErrorLogger;
import net.digitalid.utility.validation.validator.ValueAnnotationValidator;

/**
 * This annotation indicates that the annotated type is unassignable to the given type.
 * 
 * @see AssignableTo
 */
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
@ValueValidator(UnassignableTo.Validator.class)
public @interface UnassignableTo {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Returns the type to which the annotated type should be unassignable.
     */
    @Nonnull Class<?> value();
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Validator implements ValueAnnotationValidator {
        
        @Pure
        @Override
        public void checkUsage(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @NonCaptured @Modified @Nonnull ErrorLogger errorLogger) {
            final @Nonnull TypeMirror declaredType = ProcessingUtility.getType(element);
            final @Nullable Class<?> desiredType = ProcessingUtility.getClass(ProcessingUtility.getAnnotationValue(annotationMirror));
            if (desiredType != null && ProcessingUtility.isRawlyAssignable(declaredType, desiredType)) {
                errorLogger.log("The value of type $ is rawly assignable to $:", SourcePosition.of(element), ProcessingUtility.getSimpleName(declaredType), desiredType.getSimpleName());
            }
        }
        
    }
    
}
