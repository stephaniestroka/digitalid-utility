package net.digitalid.utility.annotations.reference;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation indicates that a reference is non-nullable.
 * (This annotation is only valuable once the source code can
 * be transitioned to Java 1.8 with its type annotations).
 * 
 * @see Nullable
 * 
 * @author Kaspar Etter
 * @version 1.0.0
 */
@Deprecated
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD})
public @interface NonNullable {}
