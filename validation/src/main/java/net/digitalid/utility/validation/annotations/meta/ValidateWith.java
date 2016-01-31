package net.digitalid.utility.validation.annotations.meta;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.validator.AnnotationValidator;

/**
 * This meta-annotation indicates the validator with which the annotated annotation is validated.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface ValidateWith {
    
    /**
     * Returns the validator with which the annotated annotation is validated.
     */
    @Nonnull Class<? extends AnnotationValidator> value();
    
}
