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

import net.digitalid.utility.validation.annotations.meta.TargetTypes;
import net.digitalid.utility.validation.annotations.meta.Validator;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.processing.TypeImporter;
import net.digitalid.utility.validation.validator.AnnotationValidator;
import net.digitalid.utility.validation.validator.GeneratedContract;

/**
 * This annotation indicates that an {@link Iterable iterable} does not contain duplicates.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Validator(UniqueElements.Validator.class)
@TargetTypes({Iterable.class, Object[].class})
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface UniqueElements {
    
    @Stateless
    public static class Validator extends AnnotationValidator {
        
        @Pure
        public static boolean validate(@Nullable Iterable<?> iterable) {
            if (iterable == null) { return true; }
            final @Nonnull HashSet<Object> set = new HashSet<>();
            for (@Nullable Object element : iterable) {
                if (set.contains(element)) { return false; }
                else { set.add(element); }
            }
            return true;
        }
        
        @Pure
        public static boolean validate(@Nullable Object[] array) {
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
        public @Nonnull GeneratedContract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @Nonnull TypeImporter typeImporter) {
            return GeneratedContract.with(typeImporter.importIfPossible(UniqueElements.class) + ".Validator.validate(#)", "The # may not contain duplicates.", element);
        }
        
    }
    
}
