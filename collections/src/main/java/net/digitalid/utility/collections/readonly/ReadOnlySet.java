package net.digitalid.utility.collections.readonly;

import java.util.Set;

import javax.annotation.Nonnull;

import net.digitalid.utility.collections.freezable.FreezableSet;
import net.digitalid.utility.freezable.Freezable;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.validation.reference.Capturable;
import net.digitalid.utility.validation.state.Immutable;
import net.digitalid.utility.validation.state.Pure;

/**
 * This interface provides read-only access to {@link Set sets} and should <em>never</em> be cast away (unless external code requires it).
 * <p>
 * <em>Important:</em> Only use freezable or immutable types for the elements!
 * (The type is not restricted to {@link Freezable} or {@link Immutable} so that library types can also be used.)
 * 
 * @see FreezableSet
 */
public interface ReadOnlySet<E> extends ReadOnlyCollection<E> {
    
    /* -------------------------------------------------- Operations -------------------------------------------------- */
    
    /**
     * Returns the union of this and the given set.
     * 
     * @param set the set whose elements are added.
     * 
     * @return the union of this and the given set.
     */
    @Pure
    public @Capturable @Nonnull @NonFrozen FreezableSet<E> add(@Nonnull ReadOnlySet<E> set);
    
    /**
     * Returns the relative complement of the given set in this set.
     * 
     * @param set the set whose elements are subtracted.
     * 
     * @return the relative complement of the given set in this set.
     */
    @Pure
    public @Capturable @Nonnull @NonFrozen FreezableSet<E> subtract(@Nonnull ReadOnlySet<E> set);
    
    /**
     * Returns the intersection of this and the given set.
     * 
     * @param set the set whose elements are intersected.
     * 
     * @return the intersection of this and the given set.
     */
    @Pure
    public @Capturable @Nonnull @NonFrozen FreezableSet<E> intersect(@Nonnull ReadOnlySet<E> set);
    
    /* -------------------------------------------------- Cloneable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableSet<E> clone();
    
}
