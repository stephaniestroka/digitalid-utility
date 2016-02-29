package net.digitalid.utility.validation.annotations.size;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.meta.TargetTypes;
import net.digitalid.utility.validation.annotations.meta.Validator;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.interfaces.Countable;
import net.digitalid.utility.validation.validators.SizeValidator;

/**
 * This annotation indicates that a collection, array or string contains the given number of elements.
 * (In case of strings, we are talking about the number of contained characters, of course.)
 * 
 * @see MinSize
 * @see MaxSize
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Validator(Size.Validator.class)
@TargetTypes({Collection.class, Countable.class, Object[].class, CharSequence.class})
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface Size {
    
    /**
     * Returns the number of elements that the annotated collection, array or string contains.
     */
    int value();
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Validator extends SizeValidator {
        
        @Pure
        @Override
        public @Nonnull String getSizeComparison() {
            return "== @";
        }
        
        @Pure
        @Override
        public @Nonnull String getMessageCondition() {
            return "@";
        }
        
    }
    
}
