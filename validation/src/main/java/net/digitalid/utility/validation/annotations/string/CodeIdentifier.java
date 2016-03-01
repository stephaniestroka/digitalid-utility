package net.digitalid.utility.validation.annotations.string;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.digitalid.utility.validation.annotations.meta.TargetTypes;
import net.digitalid.utility.validation.annotations.meta.Generator;
import net.digitalid.utility.validation.generator.ContractGenerator;

/**
 * This annotation indicates that a string is a valid identifier in most languages like Java or SQL.
 */
@Documented
@TargetTypes(CharSequence.class)
@Retention(RetentionPolicy.RUNTIME)
@Generator(CodeIdentifier.Validator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD})
public @interface CodeIdentifier {
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    public static class Validator extends ContractGenerator {
        // TODO: Generate the contract.
    }
    
}
