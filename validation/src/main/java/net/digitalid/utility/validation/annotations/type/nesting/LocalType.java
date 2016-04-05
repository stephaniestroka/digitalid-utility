package net.digitalid.utility.validation.annotations.type.nesting;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;
import javax.lang.model.element.NestingKind;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.meta.ValueValidator;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.validators.NestingKindValidator;

/**
 * This annotation indicates that a class or element represents a local type.
 * 
 * @see NestingKind
 */
@Documented
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
@ValueValidator(LocalType.Validator.class)
public @interface LocalType {
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Validator extends NestingKindValidator {
        
        @Pure
        @Override
        public @Nonnull NestingKind getKind() {
            return NestingKind.LOCAL;
        }
        
    }
    
}
