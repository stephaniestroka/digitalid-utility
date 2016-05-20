package net.digitalid.utility.validation.annotations.value;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.string.JavaExpression;

/**
 * This annotation indicates the condition that a value has to fulfill.
 * You can use this annotation if you don't want to write a dedicated annotation for some rare case.
 */
@Documented
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Invariant {
    
    /* -------------------------------------------------- Values -------------------------------------------------- */
    
    /**
     * Returns the condition which the generated field or parameter has to fulfill.
     */
    @Nonnull @JavaExpression String condition();
    
    /**
     * Returns the message which is thrown if the given condition is not fulfilled.
     */
    @Nonnull String message();
    
}
