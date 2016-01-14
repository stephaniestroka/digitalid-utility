package net.digitalid.utility.validation.reference;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation indicates that a parameter may not be captured by the callee.
 * (The callee must not keep or leak a reference to the passed parameter.)
 *
 * @see Capturable
 * @see Captured
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD})
public @interface NonCapturable {}
