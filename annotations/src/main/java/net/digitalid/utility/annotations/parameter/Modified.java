package net.digitalid.utility.annotations.parameter;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This annotation indicates that the annotated parameter is modified by the callee.
 * Whether a given parameter is captured, on the other hand, is indicated by the
 * related but independent {@link Captured} and {@link NonCaptured} annotations.
 * (This annotation only makes sense for {@link Mutable mutable} objects.)
 * 
 * @see Unmodified
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Modified {}
