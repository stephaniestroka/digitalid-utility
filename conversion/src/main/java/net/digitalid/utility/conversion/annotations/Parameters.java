package net.digitalid.utility.conversion.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies the parameters of a convertible that must be converted to and from Java.
 * This annotation will be removed once we have a better mechanism to retrieve the fields.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Parameters {
    
    String[] value();
    
}
