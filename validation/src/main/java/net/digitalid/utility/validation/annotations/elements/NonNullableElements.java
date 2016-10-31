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

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.processing.utility.TypeImporter;
import net.digitalid.utility.validation.annotations.meta.ValueValidator;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.contract.Contract;
import net.digitalid.utility.validation.validators.IterableValidator;

/**
 * This annotation indicates that the elements of an {@link Iterable iterable} or array are {@link Nonnull non-nullable}.
 * Even though Java 1.8 now supports type annotations, this annotation is still useful for contract generation and arrays.
 * 
 * @see NullableElements
 */
@Documented
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.SOURCE)
@ValueValidator(NonNullableElements.Validator.class)
public @interface NonNullableElements {
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Validator extends IterableValidator {
        
        private static final @Nonnull FiniteIterable<@Nonnull Class<?>> targetTypes = FiniteIterable.of(Iterable.class, Object[].class);
        
        @Pure
        @Override
        public @Nonnull FiniteIterable<@Nonnull Class<?>> getTargetTypes() {
            return targetTypes;
        }
        
        /**
         * Returns whether all elements in the given iterable are non-null.
         */
        @Pure
        public static boolean validate(@NonCaptured @Unmodified @Nullable Iterable<?> iterable) {
            if (iterable == null) { return true; }
            for (@Nullable Object element : iterable) {
                if (element == null) { return false; }
            }
            return true;
        }
        
        /**
         * Returns whether all elements in the given array are non-null.
         */
        @Pure
        public static boolean validate(@NonCaptured @Unmodified @Nullable Object[] array) {
            if (array == null) { return true; }
            for (@Nullable Object element : array) {
                if (element == null) { return false; }
            }
            return true;
        }
        
        @Pure
        @Override
        public @Nonnull Contract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @NonCaptured @Modified @Nonnull TypeImporter typeImporter) {
            return Contract.with(typeImporter.importIfPossible(NonNullableElements.class) + ".Validator.validate(#)", "The # may not contain null.", element);
        }
        
    }
    
}
