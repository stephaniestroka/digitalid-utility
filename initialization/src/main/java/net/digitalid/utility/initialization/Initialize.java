package net.digitalid.utility.initialization;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.configuration.Initializer;
import net.digitalid.utility.configuration.exceptions.InitializationException;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;

/**
 * This annotation indicates that a method should be run during the initialization of the library.
 * The annotated method has to be static and may neither have parameters nor a return value.
 * If the annotated method throws an exception, the initialization is aborted with an {@link InitializationException}.
 * 
 * @see InitializationProcessor
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Initialize {
    
    /**
     * Returns the target that is initialized by the generated {@link Initializer}.
     * The target must have exactly one static field of the type {@link Configuration}.
     */
    @Nonnull Class<?> target();
    
    /**
     * Returns the dependencies that must be initialized before the annotated method is run.
     * Each dependency must have exactly one static field of the type {@link Configuration}.
     */
    @Nonnull @NonNullableElements Class<?>[] dependencies() default {};
    
}
