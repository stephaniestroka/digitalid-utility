package net.digitalid.utility.validation.annotations.type;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

/**
 * This annotation indicates that the objects of the annotated type cannot be modified through
 * their methods but can still change because they represent other objects in a read-only mode.
 * It should always be safe to share read-only objects between various instances and threads.
 * 
 * @see Mutable
 * @see Immutable
 */
@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ReadOnly {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Returns the type to which the annotated type provides read-only access (or the type of this annotation as a default because null is not allowed).
     */
    @Nonnull Class<?> value() default ReadOnly.class;
    
}
