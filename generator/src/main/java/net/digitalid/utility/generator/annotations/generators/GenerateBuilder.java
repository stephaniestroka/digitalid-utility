package net.digitalid.utility.generator.annotations.generators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a class such that the {@link net.digitalid.utility.generator.BuilderGenerator builder generator} generates a builder for this class.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface GenerateBuilder {
}
