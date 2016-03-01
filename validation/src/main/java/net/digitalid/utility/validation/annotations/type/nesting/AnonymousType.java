package net.digitalid.utility.validation.annotations.type.nesting;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.lang.model.element.Element;
import javax.lang.model.element.NestingKind;

import net.digitalid.utility.validation.annotations.meta.TargetTypes;
import net.digitalid.utility.validation.annotations.meta.Generator;
import net.digitalid.utility.validation.generator.ContractGenerator;

/**
 * This annotation indicates that a class or element represents an anonymous type.
 * 
 * @see NestingKind
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Generator(AnonymousType.Validator.class)
@TargetTypes({Class.class, Element.class})
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD})
public @interface AnonymousType {
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    public static class Validator extends ContractGenerator {
        // TODO: Generate the contract.
    }
    
}
