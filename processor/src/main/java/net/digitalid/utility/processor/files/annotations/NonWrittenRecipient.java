package net.digitalid.utility.processor.files.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.digitalid.utility.processor.files.GeneratedFile;
import net.digitalid.utility.validation.annotations.meta.TargetTypes;

/**
 * This annotation indicates that a method may only be invoked on a {@link GeneratedFile#isWritten() non-written} file.
 */
@Documented
@Target(ElementType.METHOD)
@TargetTypes(GeneratedFile.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface NonWrittenRecipient {}
