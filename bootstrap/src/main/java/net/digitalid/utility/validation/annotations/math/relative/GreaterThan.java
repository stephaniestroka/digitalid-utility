package net.digitalid.utility.validation.annotations.math.relative;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation indicates that a numeric value is greater than the given value.
 * 
 * @see LessThan
 * @see LessThanOrEqualTo
 * @see GreaterThanOrEqualTo
 */
@Documented
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
public @interface GreaterThan {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Returns the value which the annotated numeric value is greater than.
     */
    long value();
    
}
