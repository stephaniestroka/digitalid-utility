package net.digitalid.utility.validation.annotations.generation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.string.JavaExpression;

/**
 * Annotates fields that are propagated as parameters to the recover method of all other object converters referenced in the converter of the containing type.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface Provide {
    
    /**
     * Returns the default value as a Java expression.
     */
    @Nonnull @JavaExpression String value();
    
}
