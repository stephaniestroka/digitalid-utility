package net.digitalid.utility.validation.annotations.type;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation indicates that an interface is intended to be a <i>functional interface</i> as defined by the Java Language Specification.
 * It should only be used on interface declarations and one may not add more abstract methods later on as other code might depend on this.
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Functional {}
