package net.digitalid.utility.generator.annotations.generators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

import net.digitalid.utility.collaboration.annotations.TODO;
import net.digitalid.utility.collaboration.enumerations.Author;
import net.digitalid.utility.generator.generators.ConverterGenerator;
import net.digitalid.utility.validation.annotations.meta.TypeValidator;

/**
 * Marks a class such that the {@link ConverterGenerator converter generator} generates a table for the annotated class.
 * 
 * @see GenerateBuilder
 * @see GenerateSubclass
 * @see GenerateConverter
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@TypeValidator(GenerateAnnotationValidator.class)
@TODO(task = "Move this class to the database layer again once generators can be declared in higher artifacts.", date = "2017-04-16", author = Author.KASPAR_ETTER)
public @interface GenerateTableConverter {
    
    /* -------------------------------------------------- Name -------------------------------------------------- */
    
    /**
     * Returns the name of the generated table.
     */
    @Nonnull String name() default "";
    
    /* -------------------------------------------------- Module -------------------------------------------------- */
    
    /**
     * Returns the module to which the generated table belongs.
     */
    @Nonnull String module() default "";
    
    /* -------------------------------------------------- Reference -------------------------------------------------- */
    
    /**
     * Returns ...
     */
    @Nonnull String reference() default "";
    
    // or
    
    /**
     * Returns ...
     */
    @Nonnull String schema() default "";
    
    /**
     * Returns ...
     */
    @Nonnull String table() default "";
    
    /**
     * Returns ...
     */
    @Nonnull String columns() default "";
    
    /* -------------------------------------------------- Actions -------------------------------------------------- */
    
    /**
     * Returns ...
     */
    @Nonnull String /* TODO: ForeignKeyAction */ onDelete() default "";
    
    /**
     * Returns ...
     */
    @Nonnull String /* TODO: ForeignKeyAction */ onUpdate() default "";
    
}
