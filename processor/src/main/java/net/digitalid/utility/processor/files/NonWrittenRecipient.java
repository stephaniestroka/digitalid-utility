package net.digitalid.utility.processor.files;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.digitalid.utility.validation.annotations.meta.TargetTypes;

/**
 * This annotation indicates that a method should only be invoked on {@link NonFrozen non-frozen} objects.
 * 
 * @see Freezable
 */
@Documented
@Target(ElementType.METHOD)
@TargetTypes(GeneratedFile.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface NonWrittenRecipient {}
