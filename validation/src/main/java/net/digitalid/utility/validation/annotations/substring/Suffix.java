package net.digitalid.utility.validation.annotations.substring;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.meta.ValueValidator;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.validators.SubstringValidator;

/**
 * This annotation indicates that the annotated string or file ends with the given string.
 */
@Documented
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
@ValueValidator(Suffix.Validator.class)
public @interface Suffix {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Returns the string with which the annotated string ends.
     */
    @Nonnull String value();
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Validator extends SubstringValidator {
        
        @Pure
        @Override
        public @Nonnull String getMethodName() {
            return "endsWith";
        }
        
        @Pure
        @Override
        public @Nonnull String getMessageCondition() {
            return "end with";
        }
        
    }
    
}
