package net.digitalid.utility.annotations.ownership;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.digitalid.utility.annotations.type.Mutable;

/**
 * This annotation indicates that the result of a method may not be captured by the caller.
 * Since this cannot be guaranteed, this annotation should be used as rarely as possible.
 * A valid use case, however, is a constructor that leaks a reference to the new object.
 * (This annotation only makes sense for {@link Mutable mutable} objects.)
 * 
 * @see Captured
 * @see Capturable
 * @see NonCaptured
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface NonCapturable {}
