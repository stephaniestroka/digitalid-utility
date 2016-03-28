package net.digitalid.utility.processor.generator.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.digitalid.utility.processor.generator.FileGenerator;
import net.digitalid.utility.validation.annotations.meta.MethodValidator;

/**
 * This annotation indicates that a method may only be invoked on a {@link FileGenerator#isWritten() non-written} file.
 * 
 * @see FileGenerator
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
// TODO: How does a method validator look like for this annotation? We can't generate code here, so maybe the method validator annotation is misplaced?
// @MethodValidator(FileGenerator.class)
public @interface NonWrittenRecipient {}
