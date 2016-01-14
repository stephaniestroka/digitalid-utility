package net.digitalid.utility.collections.annotations.order;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import net.digitalid.utility.validation.meta.TargetType;
import net.digitalid.utility.collections.readonly.ReadOnlyList;

/**
 * This annotation indicates that the elements of a {@link List list} are {@link ReadOnlyList#isAscending() ascending}.
 * 
 * @see Descending
 * @see StrictlyAscending
 * @see StrictlyDescending
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@TargetType({List.class, ReadOnlyList.class, Object[].class})
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface Ascending {}
