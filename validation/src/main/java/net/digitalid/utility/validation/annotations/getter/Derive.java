package net.digitalid.utility.validation.annotations.getter;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.string.JavaExpression;

/**
 * This annotation indicates the expression with which a value is derived.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER})
public @interface Derive {
    
    /**
     * Returns the expression with which the value is derived.
     */
    @Nonnull @JavaExpression String value();
    
}
