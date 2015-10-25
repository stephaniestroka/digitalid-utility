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
 * This annotation indicates that a {@link Collection collection} {@link Collection#isEmpty() is empty}.
 * 
 * @see NonEmpty
 * @see EmptyOrSingle
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@TargetType({Collection.class, FreezableCollection.class})
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface Empty {}
