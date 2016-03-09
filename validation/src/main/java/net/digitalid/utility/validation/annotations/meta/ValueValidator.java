package net.digitalid.utility.validation.annotations.meta;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.validator.ValueAnnotationValidator;

/**
 * This meta-annotation indicates the value validator that validates the (return) value of the annotated variable (or method).
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface ValueValidator {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Returns the value validator that validates the (return) value of the annotated variable (or method).
     */
    @Nonnull Class<? extends ValueAnnotationValidator> value();
    
}
