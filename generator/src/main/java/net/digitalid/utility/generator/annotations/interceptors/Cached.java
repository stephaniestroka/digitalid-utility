package net.digitalid.utility.generator.annotations.interceptors;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.digitalid.utility.generator.generators.BuilderGenerator;

/**
 * This annotation indicates that the constructed instances of the annotated class should be cached.
 * 
 * TODO: Also allow this annotation on methods? Kaspar: Well, I would say only on methods!
 * 
 * @see BuilderGenerator
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cached {}
