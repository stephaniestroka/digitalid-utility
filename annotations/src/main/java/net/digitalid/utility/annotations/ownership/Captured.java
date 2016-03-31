package net.digitalid.utility.annotations.ownership;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.annotations.type.Mutable;

/**
 * This annotation indicates that the annotated parameter is captured by the callee and that the
 * passed object may afterwards no longer be modified by the caller of the constructor or method.
 * Whether a parameter is modified, is indicated by {@link Modified} and {@link Unmodified},
 * which does not need to be specified if the parameter is captured anyway.
 * (This annotation only makes sense for {@link Mutable mutable} objects.)
 * 
 * @see Capturable
 * @see NonCaptured
 * @see NonCapturable
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Captured {}
