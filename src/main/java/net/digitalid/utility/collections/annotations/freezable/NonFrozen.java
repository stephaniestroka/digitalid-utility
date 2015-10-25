package net.digitalid.utility.collections.annotations.freezable;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.digitalid.utility.annotations.meta.TargetType;
import net.digitalid.utility.collections.freezable.Freezable;
import net.digitalid.utility.collections.readonly.ReadOnly;

/**
 * This annotation indicates that a field or a parameter is not {@link Freezable#isFrozen() frozen}.
 * 
 * @see Frozen
 * @see Freezable
 */
@Documented
@TargetType(ReadOnly.class)
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface NonFrozen {}
