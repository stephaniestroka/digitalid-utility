package net.digitalid.utility.validation.annotations.meta;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.generator.ContractGenerator;

/**
 * This meta-annotation indicates the contract generator that generates contracts to validate the values annotated with the annotated annotation.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Generator {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Returns the contract generator that generates contracts to validate the values annotated with the annotated annotation.
     */
    @Nonnull Class<? extends ContractGenerator> value();
    
}
