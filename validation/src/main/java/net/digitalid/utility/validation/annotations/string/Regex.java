package net.digitalid.utility.validation.annotations.string;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.meta.TargetTypes;
import net.digitalid.utility.validation.annotations.meta.Generator;
import net.digitalid.utility.validation.generator.ContractGenerator;

/**
 * This annotation indicates that a string matches the given regular expression.
 */
@Documented
@TargetTypes(CharSequence.class)
@Retention(RetentionPolicy.RUNTIME)
@Generator(Regex.Validator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD})
public @interface Regex {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Returns the regular expression which the annotated string matches.
     */
    @Nonnull String value();
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    public static class Validator extends ContractGenerator {
        // TODO: Generate the contract.
    }
    
}
