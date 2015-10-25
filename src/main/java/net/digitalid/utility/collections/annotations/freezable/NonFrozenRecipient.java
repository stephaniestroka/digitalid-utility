package net.digitalid.utility.collections.annotations.freezable;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.digitalid.utility.annotations.meta.TargetType;
import net.digitalid.utility.collections.freezable.Freezable;

/**
 * This annotation indicates that a method should only be invoked on {@link NonFrozen non-frozen} objects.
 * 
 * @see Freezable
 */
@Documented
@Target(ElementType.METHOD)
@TargetType(Freezable.class)
@Retention(RetentionPolicy.CLASS)
public @interface NonFrozenRecipient {}
