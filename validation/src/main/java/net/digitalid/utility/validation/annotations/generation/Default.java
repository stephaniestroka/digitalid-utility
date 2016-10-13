package net.digitalid.utility.validation.annotations.generation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.meta.ValueValidator;
import net.digitalid.utility.validation.annotations.string.JavaExpression;
import net.digitalid.utility.validation.validators.GenerationValidator;

/**
 * This annotation indicates that the annotated value is initialized with the given default value.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@ValueValidator(GenerationValidator.class)
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
public @interface Default {
    
    /**
     * Returns the name that describes the default value.
     */
    @Nonnull String name() default "";
    
    /**
     * Returns the default value as a Java expression.
     */
    @Nonnull @JavaExpression String value();
    
}
