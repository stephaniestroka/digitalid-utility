package net.digitalid.utility.validation.annotations.type.nesting;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;
import javax.lang.model.element.NestingKind;
import javax.lang.model.element.TypeElement;

import net.digitalid.utility.validation.annotations.meta.Generator;
import net.digitalid.utility.validation.annotations.meta.TargetTypes;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.generators.NestingKindContractGenerator;

/**
 * This annotation indicates that a class or element represents a local type.
 * 
 * @see NestingKind
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Generator(LocalType.Generator.class)
@TargetTypes({Class.class, TypeElement.class})
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD})
public @interface LocalType {
    
    /* -------------------------------------------------- Generator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Generator extends NestingKindContractGenerator {
        
        @Pure
        @Override
        public @Nonnull NestingKind getKind() {
            return NestingKind.LOCAL;
        }
        
    }
    
}
