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

import net.digitalid.utility.validation.annotations.meta.Generator;
import net.digitalid.utility.validation.annotations.meta.TargetTypes;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.contract.Contract;
import net.digitalid.utility.validation.generator.ContractGenerator;
import net.digitalid.utility.validation.processing.TypeImporter;

/**
 * This annotation indicates that an {@link Iterable iterable} does not contain duplicates.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Generator(UniqueElements.Generator.class)
@TargetTypes({Iterable.class, Object[].class})
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface UniqueElements {
    
    /* -------------------------------------------------- Generator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Generator extends ContractGenerator {
        
        /**
         * Returns whether all elements in the given iterable are unique.
         */
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
        
        /**
         * Returns whether all elements in the given array are unique.
         */
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
        public @Nonnull Contract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @Nonnull TypeImporter typeImporter) {
            return Contract.with(typeImporter.importIfPossible(UniqueElements.class) + ".Generator.validate(#)", "The # may not contain duplicates.", element);
        }
        
    }
    
}
