package net.digitalid.utility.validation.math;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigInteger;

import net.digitalid.utility.validation.meta.TargetType;
import net.digitalid.utility.validation.validator.annotation.ValidateWith;
import net.digitalid.utility.validation.validator.math.NonPositiveValidator;

/**
 * This annotation indicates that a numeric value is not positive.
 * 
 * @see Positive
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@ValidateWith(NonPositiveValidator.class)
@TargetType({long.class, int.class, short.class, byte.class, BigInteger.class})
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface NonPositive {}
