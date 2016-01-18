package net.digitalid.utility.validation.validator.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.digitalid.utility.validation.validator.Validator;

/**
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ValidateWith {
    
    Class<? extends Validator<? extends Annotation>> value();
    
}
