package net.digitalid.utility.validation.annotations.equality;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

/**
 * This annotation indicates that the string version of the annotated object does not equal the given string.
 * 
 * @see Equal
 */
@Documented
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Unequal {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Returns the string which the string version of the annotated object does not equal.
     */
    @Nonnull String value();
    
}
