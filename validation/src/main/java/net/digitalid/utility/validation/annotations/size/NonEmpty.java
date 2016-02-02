package net.digitalid.utility.validation.annotations.size;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;

import net.digitalid.utility.validation.annotations.meta.TargetTypes;
import net.digitalid.utility.validation.annotations.meta.ValidateWith;
import net.digitalid.utility.validation.validator.AnnotationValidator;

/**
 * This annotation indicates that a {@link Collection collection}, array or string {@link Collection#isEmpty() is not empty}.
 * 
 * @see Empty
 * @see NonEmptyOrSingle
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@ValidateWith(NonEmpty.Validator.class)
@TargetTypes({Collection.class, Countable.class, Object[].class, CharSequence.class})
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface NonEmpty {
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    public static class Validator extends AnnotationValidator {
        // TODO: Generate the contract.
    }
    
}
