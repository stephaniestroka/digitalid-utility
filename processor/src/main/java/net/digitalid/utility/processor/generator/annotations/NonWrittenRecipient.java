package net.digitalid.utility.processor.generator.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.digitalid.utility.processor.generator.FileGenerator;
import net.digitalid.utility.validation.annotations.meta.MethodAnnotation;

/**
 * This annotation indicates that a method may only be invoked on a {@link GeneratedFile#isWritten() non-written} file.
 * 
 * @see FileGenerator
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@MethodAnnotation(FileGenerator.class)
public @interface NonWrittenRecipient {}
