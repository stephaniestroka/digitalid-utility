package net.digitalid.utility.validation.annotations.type.kind;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

import net.digitalid.utility.validation.annotations.meta.Generator;
import net.digitalid.utility.validation.annotations.meta.TargetTypes;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.generators.ElementKindContractGenerator;

/**
 * This annotation indicates that a class or element represents a class.
 * 
 * @see Class
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Generator(ClassType.Generator.class)
@TargetTypes({Class.class, Element.class})
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD})
public @interface ClassType {
    
    /* -------------------------------------------------- Generator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Generator extends ElementKindContractGenerator {
        
        @Pure
        @Override
        public @Nonnull ElementKind getKind() {
            return ElementKind.CLASS;
        }
        
    }
    
}
