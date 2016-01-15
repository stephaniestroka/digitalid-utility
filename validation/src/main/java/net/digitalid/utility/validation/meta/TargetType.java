package net.digitalid.utility.validation.meta;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

/**
 * This meta-annotation indicates to which types of values the annotated annotation can be applied.
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.ANNOTATION_TYPE)
public @interface TargetType {
    
    /**
     * Returns the types of values to which the annotated annotation can be applied.
     * 
     * @return the types of values to which the annotated annotation can be applied.
     */
    @Nonnull Class<?>[] value();
    
}
