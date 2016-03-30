package net.digitalid.utility.validation.annotations.math.relative;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation indicates that a numeric value is greater than or equal to the given value.
 * 
 * @see LessThan
 * @see GreaterThan
 * @see LessThanOrEqualTo
 */
@Documented
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
public @interface GreaterThanOrEqualTo {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Returns the value which the annotated numeric value is greater than or equal to.
     */
    long value();
    
}
