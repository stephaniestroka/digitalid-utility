package net.digitalid.utility.validation.annotations.state;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.digitalid.utility.validation.annotations.method.Pure;

/**
 * This annotation indicates that an object is modifiable.
 * Non-{@link Pure pure} methods can only be called on modifiable objects.
 * 
 * @see Unmodifiable
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface Modifiable {}
