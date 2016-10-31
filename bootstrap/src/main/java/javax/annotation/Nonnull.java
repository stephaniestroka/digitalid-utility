package javax.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation indicates that the annotated reference is not null.
 * 
 * Ideally, this annotation would be in {@code net.digitalid.utility.annotations.reference} and be
 * called NonNullable. However, we stick to this package and name because of better tool support.
 * 
 * @see Nullable
 */
@Documented
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.SOURCE)
public @interface Nonnull {}
