package net.digitalid.utility.validation.annotations.generation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.meta.ValueValidator;
import net.digitalid.utility.validation.annotations.string.JavaExpression;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.validators.GenerationValidator;

/**
 * This annotation indicates that the annotated value is derived with the given expression.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@ValueValidator(Derive.Validator.class)
@Target({ElementType.PARAMETER, ElementType.METHOD})
public @interface Derive {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Returns the expression with which the value is derived.
     */
    @Nonnull @JavaExpression String value();
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of the surrounding annotation.
     */
    @Stateless
    public static class Validator extends GenerationValidator {}
    
}
