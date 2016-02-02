package net.digitalid.utility.validation.annotations.reference;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.digitalid.utility.validation.annotations.meta.ValidateWith;
import net.digitalid.utility.validation.validator.AnnotationValidator;

/**
 * This annotation indicates that a method returns the recipient of the call (the 'this' object).
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ValidateWith(Chainable.Validator.class)
public @interface Chainable {
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    public static class Validator extends AnnotationValidator {
        // TODO: Generate the contract.
    }
    
}
