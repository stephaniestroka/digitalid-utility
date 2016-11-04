package net.digitalid.utility.validation.annotations.order;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.meta.ValueValidator;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.validators.OrderingValidator;

/**
 * This annotation indicates that the elements of an {@link Iterable iterable} are strictly descending.
 * 
 * @see Ascending
 * @see Descending
 * @see StrictlyAscending
 */
@Documented
@Target({ElementType.TYPE_USE, ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
@ValueValidator(StrictlyDescending.Validator.class)
public @interface StrictlyDescending {
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Validator extends OrderingValidator {
        
        @Pure
        @Override
        protected boolean isStrictly() {
            return true;
        }
        
        @Pure
        @Override
        protected boolean isAscending() {
            return false;
        }
        
    }
    
}
