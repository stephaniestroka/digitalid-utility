package net.digitalid.utility.validation.annotations.elements;
  
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nullable;

import net.digitalid.utility.validation.annotations.meta.ValueValidator;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.validators.ElementsValidator;

/**
 * This annotation indicates that the elements of an {@link Iterable iterable} or array are {@link Nullable nullable}.
 * Even though Java 1.8 now supports type annotations, this annotation is still useful to avoid ambiguity in case of arrays.
 * 
 * @see NonNullableElements
 */
@Documented
@Target({ElementType.TYPE_USE, ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.SOURCE)
@ValueValidator(NullableElements.Validator.class)
public @interface NullableElements {
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Validator extends ElementsValidator {}
    
}
