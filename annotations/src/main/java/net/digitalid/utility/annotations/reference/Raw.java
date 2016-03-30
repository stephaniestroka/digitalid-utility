package net.digitalid.utility.annotations.reference;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation indicates that a parameter or local variable might not yet be fully initialized.<br>
 * Therefore, you cannot rely on the {@code @Nonnull} property of a field or method called on a raw variable.<br>
 * Please make sure that you only pass {@code this} in a constructor to methods that are aware of this situation.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
public @interface Raw {}
