package net.digitalid.utility.freezable.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.digitalid.utility.freezable.Freezable;
import net.digitalid.utility.readonly.ReadOnly;
import net.digitalid.utility.validation.meta.TargetType;

/**
 * This annotation indicates that a field or a parameter is not {@link Freezable#isFrozen() frozen}.
 * 
 * @see Frozen
 * @see Freezable
 */
@Documented
@TargetType(ReadOnly.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface NonFrozen {}
