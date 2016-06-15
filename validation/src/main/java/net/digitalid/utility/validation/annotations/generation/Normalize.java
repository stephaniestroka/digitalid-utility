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
 * This annotation indicates that the annotated value is normalized with the given expression.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@ValueValidator(GenerationValidator.class)
@Target({ElementType.PARAMETER, ElementType.METHOD})
public @interface Normalize {
    
    /**
     * Returns the expression with which the value is normalized.
     */
    @Nonnull @JavaExpression String value();
    
}
