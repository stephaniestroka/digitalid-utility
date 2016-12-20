package net.digitalid.utility.annotations.generics;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation indicates that the annotated type parameter can be specified more precisely when the type is used.
 * In almost all cases, it indicates that the type parameter can be instantiated with a nullable or non-nullable type.
 * 
 * @see Unspecifiable
 */
@Documented
@Target(ElementType.TYPE_PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Specifiable {}
