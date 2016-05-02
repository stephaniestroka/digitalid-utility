package net.digitalid.utility.annotations.ownership;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This annotation indicates that the result of a method can be captured by the caller.
 * No reference to the returned object may be kept by the callee (or any other object).
 * The results of constructors are always assumed to be capturable unless the
 * constructor itself is annotated with the {@link NonCapturable} annotation.
 * (This annotation only makes sense for {@link Mutable mutable} objects.)
 * 
 * @see Captured
 * @see NonCaptured
 * @see NonCapturable
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Capturable {}
