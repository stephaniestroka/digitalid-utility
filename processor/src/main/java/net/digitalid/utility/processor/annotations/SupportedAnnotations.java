package net.digitalid.utility.processor.annotations;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;
import javax.annotation.processing.SupportedAnnotationTypes;

import net.digitalid.utility.processor.CustomProcessor;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;

/**
 * This annotation indicates what annotation types an annotation processor supports.
 * It is an alternative to {@link SupportedAnnotationTypes} which prevents typos and
 * supports auto-completion and renaming by integrated development environments.
 */
@Documented
@Target(ElementType.TYPE)
// TODO: How does a method validator look like for this annotation? We can't generate code here, so maybe the method validator annotation is misplaced?
// @MethodValidator(Processor.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface SupportedAnnotations {
    
    /**
     * Returns the annotation types that the annotated annotation processor supports.
     */
    @Nonnull @NonNullableElements Class<? extends Annotation>[] value() default {};
    
    /**
     * Returns the prefixes of annotation types that the annotated annotation processor supports.
     * The wildcard character '*' will be appended to each prefix by the {@link CustomProcessor}.
     */
    @Nonnull @NonNullableElements String[] prefix() default {};
    
}
