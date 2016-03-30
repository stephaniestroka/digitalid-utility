package net.digitalid.utility.validation.annotations.size;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.meta.ValueValidator;
import net.digitalid.utility.tuples.annotations.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.validators.SizeValidator;

/**
 * This annotation indicates that a {@link Collection collection}, array or string {@link Collection#isEmpty() is not empty}.
 * 
 * @see Empty
 * @see NonEmptyOrSingle
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@ValueValidator(NonEmpty.Validator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface NonEmpty {
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Validator extends SizeValidator {
        
        @Pure
        @Override
        public @Nonnull String getSizeComparison() {
            return "!= 0";
        }
        
        @Pure
        @Override
        public @Nonnull String getMessageCondition() {
            return "not zero";
        }
        
    }
    
}
