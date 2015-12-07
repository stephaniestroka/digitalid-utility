package net.digitalid.utility.collections.annotations.size;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;
import net.digitalid.utility.annotations.meta.TargetType;
import net.digitalid.utility.collections.freezable.FreezableCollection;

/**
 * This annotation indicates that a collection or array contains at most the given number of elements.
 * (In case of strings, we are talking about the number of contained characters, of course.)
 * 
 * @see Size
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@TargetType({Collection.class, FreezableCollection.class, Object[].class, String.class})
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface SizeAtMost {
    int value();
}
