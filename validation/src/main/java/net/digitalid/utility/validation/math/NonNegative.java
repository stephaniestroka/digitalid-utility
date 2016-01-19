package net.digitalid.utility.validation.math;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigInteger;

import net.digitalid.utility.validation.meta.TargetType;
import net.digitalid.utility.validation.validator.annotation.ValidateWith;
import net.digitalid.utility.validation.validator.math.NonNegativeValidator;

/**
 * This annotation indicates that a numeric value is not negative.
 * 
 * @see Negative
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@TargetType({long.class, int.class, short.class, byte.class, BigInteger.class})
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.CONSTRUCTOR})
@ValidateWith(NonNegativeValidator.class)
public @interface NonNegative {}
