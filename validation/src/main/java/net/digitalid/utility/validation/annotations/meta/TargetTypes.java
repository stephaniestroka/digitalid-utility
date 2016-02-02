package net.digitalid.utility.validation.annotations.meta;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.elements.UniqueElements;

/**
 * This meta-annotation indicates to which types of values the annotated annotation can be applied.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface TargetTypes {
    
    /**
     * Returns the types of values to which the annotated annotation can be applied.
     */
    @Nonnull @NonNullableElements @UniqueElements Class<?>[] value();
    
}
