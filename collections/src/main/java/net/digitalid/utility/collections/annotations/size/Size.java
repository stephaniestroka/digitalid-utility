package net.digitalid.utility.collections.annotations.size;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;

import net.digitalid.utility.collections.readonly.ReadOnlyCollection;
import net.digitalid.utility.validation.meta.TargetType;

/**
 * This annotation indicates that a collection or array contains the given number of elements.
 * (In case of strings, we are talking about the number of contained characters, of course.)
 * 
 * @see MinSize
 * @see MaxSize
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@TargetType({Collection.class, ReadOnlyCollection.class, Object[].class, String.class})
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface Size {
    
    /**
     * Returns the number of elements that the annotated collection or array contains.
     * 
     * @return the number of elements that the annotated collection or array contains.
     */
    int value();
    
}
