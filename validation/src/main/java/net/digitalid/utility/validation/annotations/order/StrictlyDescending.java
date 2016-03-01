package net.digitalid.utility.validation.annotations.order;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.digitalid.utility.validation.annotations.meta.Generator;
import net.digitalid.utility.validation.annotations.meta.TargetTypes;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.generators.OrderingContractGenerator;

/**
 * This annotation indicates that the elements of an {@link Iterable iterable} are strictly descending.
 * 
 * @see Ascending
 * @see Descending
 * @see StrictlyAscending
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@TargetTypes({Iterable.class, Object[].class})
@Generator(StrictlyDescending.Validator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface StrictlyDescending {
    
    @Stateless
    public static class Validator extends OrderingContractGenerator {
        
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
