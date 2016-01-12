package net.digitalid.utility.annotations.reference;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation indicates that a parameter is captured by the callee and that the passed object
 * should afterwards no longer be modified by the caller of the constructor or (setter) method.
 * 
 * @see Capturable
 * @see NonCapturable
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Captured {}
