package net.digitalid.utility.generator.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

import net.digitalid.utility.generator.information.field.FieldInformation;

/**
 * This annotation indicates the default value of a generated field or parameter.
 * In case of the generated field, this annotation is used on its getter method.
 * 
 * @see FieldInformation
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER})
public @interface DefaultValue {
    
    @Nonnull String value();
    
}
