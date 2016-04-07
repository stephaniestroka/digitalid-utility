package net.digitalid.utility.annotations.parameter;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.digitalid.utility.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.type.ReadOnly;

/**
 * This annotation indicates that the annotated parameter remains referenced
 * by the callee but is typically left {@link Unmodified unmodified} thereafter.
 * This is often used in the constructor of {@link ReadOnly read-only} objects.
 * (This annotation only makes sense for {@link Mutable mutable} objects.)
 * 
 * @see ReadOnly
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
public @interface Referenced {}
