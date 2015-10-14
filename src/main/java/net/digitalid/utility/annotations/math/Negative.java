package net.digitalid.utility.annotations.math;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigInteger;
import net.digitalid.utility.annotations.meta.TargetType;

/**
 * This annotation indicates that a numeric value is negative.
 * 
 * @see NonNegative
 * 
 * @author Kaspar Etter
 * @version 1.0.0
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@TargetType({long.class, int.class, short.class, byte.class, BigInteger.class})
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface Negative {}
