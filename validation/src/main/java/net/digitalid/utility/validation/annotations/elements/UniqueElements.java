package net.digitalid.utility.validation.annotations.elements;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashSet;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.processing.utility.TypeImporter;
import net.digitalid.utility.validation.annotations.meta.ValueValidator;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.contract.Contract;
import net.digitalid.utility.validation.validators.IterableValidator;

/**
 * This annotation indicates that an {@link Iterable iterable} does not contain duplicates.
 */
@Documented
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
@ValueValidator(UniqueElements.Validator.class)
public @interface UniqueElements {
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Validator extends IterableValidator {
        
        /**
         * Returns whether all elements in the given iterable are unique.
         */
        @Pure
        public static boolean validate(@NonCaptured @Unmodified @Nullable Iterable<?> iterable) {
            if (iterable == null) { return true; }
            final @Nonnull HashSet<Object> set = new HashSet<>();
            for (@Nullable Object element : iterable) {
                if (set.contains(element)) { return false; }
                else { set.add(element); }
            }
            return true;
        }
        
        /**
         * Returns whether all elements in the given array are unique.
         */
        @Pure
        public static boolean validate(@NonCaptured @Unmodified @Nullable Object[] array) {
            if (array == null) { return true; }
            final @Nonnull HashSet<Object> set = new HashSet<>();
            for (@Nullable Object element : array) {
                if (set.contains(element)) { return false; }
                else { set.add(element); }
            }
            return true;
        }
        
        @Pure
        @Override
        public @Nonnull Contract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @NonCaptured @Modified @Nonnull TypeImporter typeImporter) {
            return Contract.with(typeImporter.importIfPossible(UniqueElements.class) + ".Validator.validate(#)", "The # may not contain duplicates.", element);
        }
        
    }
    
}
