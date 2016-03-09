package net.digitalid.utility.validation.annotations.type.nesting;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;
import javax.lang.model.element.NestingKind;

import net.digitalid.utility.validation.annotations.meta.ValueValidator;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.validators.NestingKindValidator;

/**
 * This annotation indicates that a class or element represents an anonymous type.
 * 
 * @see NestingKind
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@ValueValidator(AnonymousType.Validator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD})
public @interface AnonymousType {
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Validator extends NestingKindValidator {
        
        @Pure
        @Override
        public @Nonnull NestingKind getKind() {
            return NestingKind.ANONYMOUS;
        }
        
    }
    
}
