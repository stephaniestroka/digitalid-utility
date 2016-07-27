package net.digitalid.utility.validation.annotations.generation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.digitalid.utility.validation.annotations.meta.ValueValidator;

/**
 * Marks a field as non-representative.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@ValueValidator(Recover.Validator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
public @interface NonRepresentative {
}
