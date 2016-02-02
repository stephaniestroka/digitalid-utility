package net.digitalid.utility.validation.annotations.index;

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
 * This annotation indicates that an index is valid for inserting an element in a {@link Collection collection}.
 * Such an index is valid if it is greater or equal to zero and less than or equal to the number of elements (usually given by {@link Collection#size()}).
 */
@Documented
@TargetTypes(int.class)
@Retention(RetentionPolicy.RUNTIME)
@ValidateWith(IndexForInsertion.Validator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD})
public @interface IndexForInsertion {
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    public static class Validator extends AnnotationValidator {
        // TODO: Check that the annotation is only used withon collections with a size() method. Generate the contract.
    }
    
}
