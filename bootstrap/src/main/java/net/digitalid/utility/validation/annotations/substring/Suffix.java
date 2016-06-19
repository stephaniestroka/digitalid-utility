package net.digitalid.utility.validation.annotations.substring;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

/**
 * This annotation indicates that the annotated string or file ends with the given string.
 */
@Documented
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Suffix {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Returns the string with which the annotated string ends.
     */
    @Nonnull String value();
    
}
