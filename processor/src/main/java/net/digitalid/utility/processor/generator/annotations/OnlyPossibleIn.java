package net.digitalid.utility.processor.generator.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

import net.digitalid.utility.processor.generator.JavaFileGenerator;

/**
 * This annotation indicates that a method may only be invoked in one of the given code blocks.
 * 
 * @see JavaFileGenerator
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnlyPossibleIn {
    
    /**
     * Returns the code blocks in which the annotated method may be invoked.
     * An empty array of code blocks is used to indicate that any block that
     * {@link JavaFileGenerator.CodeBlock#allowsStatements() allows statements}
     * is fine. For this reason, the value defaults to an empty array.
     */
    JavaFileGenerator.@Nonnull CodeBlock[] value() default {};
    
}
