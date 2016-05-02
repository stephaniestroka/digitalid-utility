package net.digitalid.utility.annotations.method;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;

/**
 * This annotation indicates that the annotated method has no side-effects (other than caching) on the called object (or class).
 * A pure method may, however, modify its parameters, which is indicated by the {@link Modified} and {@link Unmodified} annotations.
 * A pure method is guaranteed to return the same result on repeated invocations (unless other threads modify the object in the meantime).
 * 
 * @see Impure
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Pure {}
