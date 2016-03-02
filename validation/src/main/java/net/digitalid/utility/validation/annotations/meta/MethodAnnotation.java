package net.digitalid.utility.validation.annotations.meta;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

/**
 * This meta-annotation indicates the type to whose methods the annotated annotation can be applied.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface MethodAnnotation {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Returns the type to whose methods the annotated annotation can be applied.
     */
    @Nonnull Class<?> value();
    
}
