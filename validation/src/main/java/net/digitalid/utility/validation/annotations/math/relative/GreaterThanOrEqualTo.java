package net.digitalid.utility.validation.annotations.math.relative;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigInteger;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.meta.Generator;
import net.digitalid.utility.validation.annotations.meta.TargetTypes;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.generators.ValueRelativeNumericalContractGenerator;
import net.digitalid.utility.validation.interfaces.Numerical;

/**
 * This annotation indicates that a numeric value is greater than or equal to the given value.
 * 
 * @see LessThan
 * @see GreaterThan
 * @see LessThanOrEqualTo
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Generator(GreaterThanOrEqualTo.Generator.class)
@TargetTypes({long.class, int.class, short.class, byte.class, float.class, double.class, BigInteger.class, Numerical.class})
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface GreaterThanOrEqualTo {
    
    /**
     * Returns the value which the annotated numeric value is greater than or equal to.
     */
    long value();
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Generator extends ValueRelativeNumericalContractGenerator {
        
        @Pure
        @Override
        public @Nonnull String getComparisonOperator() {
            return ">=";
        }
        
    }
    
}
