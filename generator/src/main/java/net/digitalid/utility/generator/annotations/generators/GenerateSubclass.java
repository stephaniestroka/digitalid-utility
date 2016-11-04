package net.digitalid.utility.generator.annotations.generators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

import net.digitalid.utility.generator.generators.SubclassGenerator;
import net.digitalid.utility.validation.annotations.meta.TypeValidator;

/**
 * Marks a class such that the {@link SubclassGenerator subclass generator} generates a subclass for the annotated class.
 * 
 * @see GenerateBuilder
 * @see GenerateConverter
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@TypeValidator(GenerateAnnotationValidator.class)
public @interface GenerateSubclass {
    
    @Nonnull boolean makePublic() default false;
    
}
