package net.digitalid.utility.validation.annotations.meta;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.validator.MethodAnnotationValidator;

/**
 * This meta-annotation indicates the method validator that validates the state in which the annotated method is called.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface MethodValidator {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Returns the method validator that validates the state in which the annotated method is called.
     */
    @Nonnull Class<? extends MethodAnnotationValidator> value();
    
}
