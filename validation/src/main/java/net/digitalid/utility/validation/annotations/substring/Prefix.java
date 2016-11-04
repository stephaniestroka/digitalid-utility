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
 * This annotation indicates that the annotated string or file starts with the given string.
 */
@Documented
@Target({ElementType.TYPE_USE, ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
@ValueValidator(Prefix.Validator.class)
public @interface Prefix {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Returns the string with which the annotated string starts.
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
            return "startsWith";
        }
        
        @Pure
        @Override
        public @Nonnull String getMessageCondition() {
            return "start with";
        }
        
    }
    
}
