package net.digitalid.utility.annotations.method;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation indicates that the annotated method has side-effects on the called object (or class).
 * Whether an impure method also modifies its parameters, is indicated by the {@link Modified} and {@link Unmodified} annotations.
 * 
 * @see Pure
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Impure {}
