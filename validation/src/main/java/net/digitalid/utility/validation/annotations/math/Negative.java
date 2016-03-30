package net.digitalid.utility.validation.annotations.math;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.meta.ValueValidator;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.validators.NumericalValueValidator;

/**
 * This annotation indicates that a numeric value is negative.
 * 
 * @see NonNegative
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@ValueValidator(Negative.Validator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface Negative {
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Validator extends NumericalValueValidator {
        
        @Pure
        @Override
        public @Nonnull String getComparisonOperator() {
            return "<";
        }
        
    }
    
}
