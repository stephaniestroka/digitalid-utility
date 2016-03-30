package net.digitalid.utility.annotations.state;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.digitalid.utility.annotations.method.Impure;

/**
 * This annotation indicates that an object is modifiable.
 * {@link Impure} methods can only be called on modifiable objects.
 * 
 * @see Unmodifiable
 */
@Documented
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Modifiable {}
