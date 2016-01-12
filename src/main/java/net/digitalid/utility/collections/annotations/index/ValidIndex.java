package net.digitalid.utility.collections.annotations.index;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;
import net.digitalid.utility.annotations.meta.TargetType;

/**
 * This annotation indicates that an index is valid for retrieving or removing an element of a {@link Collection collection}.
 * Such an index is valid if it is greater or equal to zero and less than the number of elements (usually given by {@link Collection#size()}).
 */
@Documented
@TargetType(int.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD})
public @interface ValidIndex {}
