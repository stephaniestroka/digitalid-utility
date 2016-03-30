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
 * This annotation indicates that the annotated parameter may not be captured by the callee.
 * This means that the callee may not keep or leak the reference to the annotated parameter.
 * Whether a parameter is modified, is indicated by {@link Modified} and {@link Unmodified}.
 * (This annotation only makes sense for {@link Mutable mutable} objects.)
 *
 * @see Captured
 * @see Capturable
 * @see NonCapturable
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface NonCaptured {}
