package net.digitalid.utility.validation.annotations.method;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.string.JavaExpression;

/**
 * This annotation indicates that a method ensures that the given condition holds after its execution.
 * 
 * @see Requires
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Ensures {
    
    /* -------------------------------------------------- Values -------------------------------------------------- */
    
    /**
     * Returns the condition that holds after the execution of the annotated method.
     */
    @Nonnull @JavaExpression String condition();
    
    /**
     * Returns the message which is thrown if the given condition is not fulfilled.
     */
    @Nonnull String message();
    
}
