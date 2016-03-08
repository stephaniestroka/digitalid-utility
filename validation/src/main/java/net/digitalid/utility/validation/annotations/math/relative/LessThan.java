package net.digitalid.utility.validation.annotations.math.relative;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.meta.ValueValidator;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.validators.ValueRelativeNumericalValueValidator;

/**
 * This annotation indicates that a numeric value is less than the given value.
 * 
 * @see GreaterThan
 * @see LessThanOrEqualTo
 * @see GreaterThanOrEqualTo
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@ValueValidator(LessThan.Validator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface LessThan {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Returns the value which the annotated numeric value is less than.
     */
    long value();
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Validator extends ValueRelativeNumericalValueValidator {
        
        @Pure
        @Override
        public @Nonnull String getComparisonOperator() {
            return "<";
        }
        
    }
    
}
