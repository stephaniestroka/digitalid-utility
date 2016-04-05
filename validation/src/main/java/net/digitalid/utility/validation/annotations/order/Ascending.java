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
 * This annotation indicates that the elements of an {@link Iterable iterable} are ascending.
 * 
 * @see Descending
 * @see StrictlyAscending
 * @see StrictlyDescending
 */
@Documented
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
@ValueValidator(Ascending.Validator.class)
public @interface Ascending {
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Validator extends OrderingValidator {
        
        @Pure
        @Override
        protected boolean isStrictly() {
            return false;
        }
        
        @Pure
        @Override
        protected boolean isAscending() {
            return true;
        }
        
    }
    
}
