package net.digitalid.utility.validation.annotations.order;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.digitalid.utility.validation.annotations.meta.Generator;
import net.digitalid.utility.validation.annotations.meta.TargetTypes;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.generators.OrderingContractGenerator;

/**
 * This annotation indicates that the elements of an {@link Iterable iterable} are ascending.
 * 
 * @see Descending
 * @see StrictlyAscending
 * @see StrictlyDescending
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Generator(Ascending.Generator.class)
@TargetTypes({Iterable.class, Object[].class})
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface Ascending {
    
    /* -------------------------------------------------- Generator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Generator extends OrderingContractGenerator {
        
        @Pure
        @Override
        protected boolean isStrictly() {
            return false;
        }
        
        @Pure
        @Override
        protected boolean isAscending() {
            return true;
        }
        
    }
    
}
