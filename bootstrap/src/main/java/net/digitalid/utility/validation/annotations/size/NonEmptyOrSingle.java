package net.digitalid.utility.validation.annotations.size;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;

/**
 * This annotation indicates that a {@link Collection collection}, array or string is {@link NonEmpty non-empty} and {@link NonSingle non-single}.
 * 
 * @see EmptyOrSingle
 */
@Documented
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NonEmptyOrSingle {}
