package net.digitalid.utility.validation.annotations.order;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.digitalid.utility.validation.annotations.meta.TargetTypes;
import net.digitalid.utility.validation.annotations.meta.Validator;
import net.digitalid.utility.validation.annotations.method.Pure;
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
@Retention(RetentionPolicy.RUNTIME)
@Validator(Ascending.Validator.class)
@TargetTypes({Iterable.class, Object[].class})
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface Ascending {
    
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
