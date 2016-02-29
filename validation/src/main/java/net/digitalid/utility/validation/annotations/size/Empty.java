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
 * This annotation indicates that a {@link Collection collection}, array or string {@link Collection#isEmpty() is empty}.
 * 
 * @see NonEmpty
 * @see EmptyOrSingle
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Validator(Empty.Validator.class)
@TargetTypes({Collection.class, Countable.class, Object[].class, CharSequence.class})
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface Empty {
    
    @Stateless
    public static class Validator extends SizeValidator {
        
        @Pure
        @Override
        public @Nonnull String getSizeComparison() {
            return "== 0";
        }
        
        @Pure
        @Override
        public @Nonnull String getMessageCondition() {
            return "zero";
        }
        
    }
    
}
