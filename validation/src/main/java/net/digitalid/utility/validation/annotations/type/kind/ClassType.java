package net.digitalid.utility.validation.annotations.type.kind;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.lang.model.element.Element;

import net.digitalid.utility.validation.annotations.meta.TargetTypes;
import net.digitalid.utility.validation.annotations.meta.Generator;
import net.digitalid.utility.validation.generator.ContractGenerator;

/**
 * This annotation indicates that a class or element represents a class.
 * 
 * @see Class
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Generator(ClassType.Validator.class)
@TargetTypes({Class.class, Element.class})
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD})
public @interface ClassType {
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    public static class Validator extends ContractGenerator {
        // TODO: Generate the contract.
    }
    
}
