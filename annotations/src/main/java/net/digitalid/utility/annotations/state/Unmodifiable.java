package net.digitalid.utility.annotations.state;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collections;

import net.digitalid.utility.annotations.method.Pure;

/**
 * This annotation indicates that an object is unmodifiable.
 * Only {@link Pure pure} methods can be called on unmodifiable objects.
 * (This annotation is intended for wrappers that still expose modifying methods but
 * throw, for example, an {@link UnsupportedOperationException} if they are called.)
 * 
 * @see Collections#unmodifiableCollection(java.util.Collection)
 * @see Collections#unmodifiableList(java.util.List)
 * @see Collections#unmodifiableMap(java.util.Map)
 * @see Collections#unmodifiableSet(java.util.Set)
 * 
 * @see Modifiable
 */
@Documented
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Unmodifiable {}
