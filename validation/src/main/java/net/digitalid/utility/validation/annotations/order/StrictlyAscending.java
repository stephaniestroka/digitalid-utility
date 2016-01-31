package net.digitalid.utility.validation.annotations.order;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.digitalid.utility.validation.annotations.meta.TargetTypes;
import net.digitalid.utility.validation.annotations.meta.ValidateWith;
import net.digitalid.utility.validation.validator.AnnotationValidator;

/**
 * This annotation indicates that the elements of an {@link Iterable iterable} are strictly ascending.
 * 
 * @see Ascending
 * @see Descending
 * @see StrictlyDescending
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@TargetTypes({Iterable.class, Object[].class})
@ValidateWith(StrictlyAscending.Validator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface StrictlyAscending {
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    public static class Validator extends AnnotationValidator {
        // TODO: Generate the contract. Maybe try to check that the elements of the annotated iterable are comparable.
    }
    
}
