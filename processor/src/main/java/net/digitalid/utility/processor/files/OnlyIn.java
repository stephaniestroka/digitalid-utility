package net.digitalid.utility.processor.files;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.meta.TargetTypes;

// TODO: Adjust all the Javadoc!

/**
 * This annotation indicates that a method should be run during the initialization of the library.
 * The annotated method has to be static and may neither have parameters nor a return value.
 * If the annotated method throws an exception, the initialization is aborted with an {@link InitializationException}.
 * 
 * @see InitializationProcessor
 */
@Documented
@TargetTypes({Void.class})
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnlyIn {
    
    /**
     * Returns the target that is initialized by the generated {@link Initializer}.
     * The target must have exactly one static field of the type {@link Configuration}.
     */
    @Nonnull JavaSourceFile.CodeBlock[] value();
    
}
