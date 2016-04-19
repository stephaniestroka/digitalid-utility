package net.digitalid.utility.generator.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

import net.digitalid.utility.generator.information.field.FieldInformation;
import net.digitalid.utility.validation.annotations.string.JavaExpression;

/**
 * This annotation indicates that a generated field or parameter has to fulfill the given condition.
 * In case of the generated field, this annotation is used on its getter method.
 * 
 * @see FieldInformation
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER})
public @interface Invariant {
    
    /**
     * Returns the condition which the generated field or parameter has to fulfill.
     */
    @Nonnull @JavaExpression String condition();
    
    /**
     * Returns the message which is thrown if the given condition is not fulfilled.
     */
    @Nonnull String message();
    
}
