package net.digitalid.utility.validation.annotations.testing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.processing.utility.TypeImporter;
import net.digitalid.utility.validation.annotations.meta.ValueValidator;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.contract.Contract;
import net.digitalid.utility.validation.processing.ErrorLogger;
import net.digitalid.utility.validation.validator.ValueAnnotationValidator;

/**
 * This annotation indicates that the annotated type is assignable to the given type.
 * 
 * @see UnassignableTo
 */
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
@ValueValidator(AssignableTo.Validator.class)
public @interface AssignableTo {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Returns the type to which the annotated type should be assignable.
     */
    @Nonnull Class<?> value();
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Validator extends ValueAnnotationValidator {
        
        private static final @Nonnull FiniteIterable<@Nonnull Class<?>> targetTypes = FiniteIterable.of(Object.class);
        
        @Pure
        @Override
        public @Nonnull FiniteIterable<@Nonnull Class<?>> getTargetTypes() {
            return targetTypes;
        }
        
        @Pure
        @Override
        public void checkUsage(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @NonCaptured @Modified @Nonnull ErrorLogger errorLogger) {
            final @Nullable Class<?> desiredType = ProcessingUtility.getClass(ProcessingUtility.getAnnotationValue(annotationMirror));
            if (desiredType != null && !ProcessingUtility.isAssignable(element, desiredType)) {
                errorLogger.log("The value is not assignable to $:", SourcePosition.of(element), desiredType.getCanonicalName());
            }
        }
        
        @Pure
        @Override
        public @Nonnull Contract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @NonCaptured @Modified @Nonnull TypeImporter typeImporter) {
            return Contract.with("true", "The universe got hacked if true is no longer true.", element);
        }
        
    }
    
}
