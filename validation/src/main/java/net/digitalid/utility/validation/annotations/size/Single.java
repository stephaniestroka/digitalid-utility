package net.digitalid.utility.validation.annotations.size;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.meta.Generator;
import net.digitalid.utility.validation.annotations.meta.TargetTypes;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.generators.SizeContractGenerator;
import net.digitalid.utility.validation.interfaces.Countable;

/**
 * This annotation indicates that a {@link Collection collection}, array or string contains a single element.
 * 
 * @see NonSingle
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Generator(Single.Generator.class)
@TargetTypes({Collection.class, Countable.class, Object[].class, CharSequence.class})
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface Single {
    
    /* -------------------------------------------------- Generator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Generator extends SizeContractGenerator {
        
        @Pure
        @Override
        public @Nonnull String getSizeComparison() {
            return "== 1";
        }
        
        @Pure
        @Override
        public @Nonnull String getMessageCondition() {
            return "one";
        }
        
    }
    
}
