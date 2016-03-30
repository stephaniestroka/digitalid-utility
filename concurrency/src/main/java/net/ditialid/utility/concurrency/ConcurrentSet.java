package net.ditialid.utility.concurrency;

import java.util.Set;

import net.digitalid.utility.annotations.type.Mutable;

/**
 * Extends Java's {@link java.util.Set} interface.
 * 
 * @param <E> the type of the elements of this set.
 * 
 * @see ConcurrentHashSet
 */
@Mutable
public interface ConcurrentSet<E> extends Set<E> {}
