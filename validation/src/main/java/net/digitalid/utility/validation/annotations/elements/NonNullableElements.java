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

import net.digitalid.utility.validation.annotations.meta.Generator;
import net.digitalid.utility.validation.annotations.meta.TargetTypes;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.contract.Contract;
import net.digitalid.utility.validation.generator.ContractGenerator;
import net.digitalid.utility.validation.processing.TypeImporter;

/**
 * This annotation indicates that the elements of an {@link Iterable iterable} are {@link Nonnull non-nullable}.
 * (This annotation is only necessary until the source code can be transitioned to Java 1.8 with its type annotations).
 * 
 * @see NullableElements
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@TargetTypes({Iterable.class, Object[].class})
@Generator(NonNullableElements.Generator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface NonNullableElements {
    
    @Stateless
    public static class Generator extends ContractGenerator {
        
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
        public @Nonnull Contract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @Nonnull TypeImporter typeImporter) {
            return Contract.with(typeImporter.importIfPossible(NonNullableElements.class) + ".Generator.validate(#)", "The # may not contain null.", element);
        }
        
    }
    
}
