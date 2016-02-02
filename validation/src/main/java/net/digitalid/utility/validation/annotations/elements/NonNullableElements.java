package net.digitalid.utility.validation.annotations.elements;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.meta.TargetTypes;
import net.digitalid.utility.validation.annotations.meta.ValidateWith;
import net.digitalid.utility.validation.validator.AnnotationValidator;

/**
 * This annotation indicates that the elements of an {@link Iterable iterable} are {@link Nonnull non-nullable}.
 * (This annotation is only necessary until the source code can be transitioned to Java 1.8 with its type annotations).
 * 
 * @see NullableElements
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@TargetTypes({Iterable.class, Object[].class})
@ValidateWith(NonNullableElements.Validator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface NonNullableElements {
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    public static class Validator extends AnnotationValidator {
        // TODO: Generate the contract.
    }
    
}
