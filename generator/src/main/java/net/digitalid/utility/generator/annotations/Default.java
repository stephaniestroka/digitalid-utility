package net.digitalid.utility.generator.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

import net.digitalid.utility.generator.BuilderGenerator;
import net.digitalid.utility.generator.information.field.FieldInformation;
import net.digitalid.utility.validation.annotations.string.JavaExpression;

/**
 * This annotation indicates the default value of a generated field or parameter.
 * In case of the generated field, this annotation is used on its getter method.
 * 
 * @see FieldInformation
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER})
public @interface Default {
    
    /**
     * Returns the name that describes the default value.
     * The name is used by the {@link BuilderGenerator}.
     */
    @Nonnull String name();
    
    /**
     * Returns the default value as a Java expression.
     */
    @Nonnull @JavaExpression String value();
    
}
