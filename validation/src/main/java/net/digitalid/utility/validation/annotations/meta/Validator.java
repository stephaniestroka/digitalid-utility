package net.digitalid.utility.validation.annotations.meta;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.generator.TypeValidator;

/**
 * This meta-annotation indicates the type validator that validates the annotations on the members of the type annotated with the annotated annotation.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Validator {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Returns the type validator that validates the annotations on the members of the type annotated with the annotated annotation.
     */
    @Nonnull Class<? extends TypeValidator> value();
    
}
