package net.digitalid.utility.validation.annotations.math;

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
import net.digitalid.utility.validation.generators.NumericalContractGenerator;
import net.digitalid.utility.validation.interfaces.Numerical;

/**
 * This annotation indicates that a numeric value is negative.
 * 
 * @see NonNegative
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Generator(Negative.Generator.class)
@TargetTypes({long.class, int.class, short.class, byte.class, float.class, double.class, BigInteger.class, Numerical.class})
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface Negative {
    
    /* -------------------------------------------------- Generator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Generator extends NumericalContractGenerator {
        
        @Pure
        @Override
        public @Nonnull String getComparisonOperator() {
            return "<";
        }
        
    }
    
}
