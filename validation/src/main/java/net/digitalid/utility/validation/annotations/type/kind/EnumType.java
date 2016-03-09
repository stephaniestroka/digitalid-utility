package net.digitalid.utility.validation.annotations.type.kind;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;
import javax.lang.model.element.ElementKind;

import net.digitalid.utility.validation.annotations.meta.ValueValidator;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.validators.ElementKindValidator;

/**
 * This annotation indicates that a class or element represents an enumeration.
 * 
 * @see Class#isEnum()
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@ValueValidator(EnumType.Validator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD})
public @interface EnumType {
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Validator extends ElementKindValidator {
        
        @Pure
        @Override
        public @Nonnull ElementKind getKind() {
            return ElementKind.ENUM;
        }
        
    }
    
}
