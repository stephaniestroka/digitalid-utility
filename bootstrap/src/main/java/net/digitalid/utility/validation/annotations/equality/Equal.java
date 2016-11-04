package net.digitalid.utility.validation.annotations.equality;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

/**
 * This annotation indicates that the string version of the annotated object equals the given string.
 * 
 * @see Unequal
 */
@Documented
@Target({ElementType.TYPE_USE, ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Equal {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Returns the string which the string version of the annotated object equals.
     */
    @Nonnull String value();
    
}
