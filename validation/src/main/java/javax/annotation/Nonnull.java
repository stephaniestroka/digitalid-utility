package javax.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.digitalid.utility.validation.validator.annotation.ValidateWith;
import net.digitalid.utility.validation.validator.nonnull.NonNullValidator;

/**
 * This annotation indicates that the annotated reference is not null.
 * 
 * @see Nullable
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@ValidateWith(NonNullValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD})
public @interface Nonnull {}
