package net.digitalid.utility.freezable.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.digitalid.utility.validation.meta.TargetType;
import net.digitalid.utility.freezable.Freezable;

/**
 * This annotation indicates that a method should only be invoked on {@link NonFrozen non-frozen} objects.
 * 
 * @see Freezable
 */
@Documented
@Target(ElementType.METHOD)
@TargetType(Freezable.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface NonFrozenRecipient {}
