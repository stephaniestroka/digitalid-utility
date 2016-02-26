package net.digitalid.utility.validation.annotations.elements;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import net.digitalid.utility.validation.annotations.meta.TargetTypes;
import net.digitalid.utility.validation.annotations.meta.Validator;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.processing.TypeImporter;
import net.digitalid.utility.validation.validator.AnnotationValidator;
import net.digitalid.utility.validation.validator.GeneratedContract;

/**
 * This annotation indicates that the elements of an {@link Iterable iterable} are {@link Nonnull non-nullable}.
 * (This annotation is only necessary until the source code can be transitioned to Java 1.8 with its type annotations).
 * 
 * @see NullableElements
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@TargetTypes({Iterable.class, Object[].class})
@Validator(NonNullableElements.Validator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface NonNullableElements {
    
    @Stateless
    public static class Validator extends AnnotationValidator {
        
        @Pure
        public boolean validate(@Nullable Iterable<?> iterable) {
            if (iterable == null) { return true; }
            for (@Nullable Object element : iterable) {
                if (element == null) { return false; }
            }
            return true;
        }
        
        @Pure
        public boolean validate(@Nullable Object[] array) {
            if (array == null) { return true; }
            for (@Nullable Object element : array) {
                if (element == null) { return false; }
            }
            return true;
        }
        
        @Pure
        @Override
        public @Nonnull GeneratedContract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @Nonnull TypeImporter typeImporter) {
            typeImporter.importIfPossible(NonNullableElements.class);
            return GeneratedContract.with("NonNullableElements.Validator.validate(#)", "The # may not contain null.", element);
        }
        
    }
    
}
