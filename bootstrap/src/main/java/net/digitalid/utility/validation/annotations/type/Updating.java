package net.digitalid.utility.validation.annotations.type;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation indicates that the objects of the annotated class cannot be modified through
 * their methods but can still change because they represent other objects in a read-only mode.
 * It should always be safe to share updating objects between various instances and threads.
 * 
 * @see Mutable
 * @see Immutable
 */
@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Updating {}
