package net.digitalid.utility.validation.annotations.elements;
  
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nullable;

/**
 * This annotation indicates that the elements of an {@link Iterable iterable} or array are {@link Nullable nullable}.
 * Even though Java 1.8 now supports type annotations, this annotation is still useful to avoid ambiguity in case of arrays.
 * 
 * @see NonNullableElements
 */
@Documented
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.SOURCE)
public @interface NullableElements {}
