package net.digitalid.utility.processor.files.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

import net.digitalid.utility.processor.files.JavaSourceFile;
import net.digitalid.utility.validation.annotations.meta.MethodAnnotation;

/**
 * This annotation indicates that a method may only be invoked in one of the given code blocks.
 * 
 * @see JavaSourceFile
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@MethodAnnotation(JavaSourceFile.class)
public @interface OnlyPossibleIn {
    
    /**
     * Returns the code blocks in which the annotated method may be invoked.
     * An empty array of code blocks is used to indicate that any block that
     * {@link JavaSourceFile.CodeBlock#allowsStatements() allows statements}
     * is fine. For this reason, the value defaults to an empty array.
     */
    @Nonnull JavaSourceFile.CodeBlock[] value() default {};
    
}
