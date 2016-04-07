package net.digitalid.utility.collections.set;

import java.util.Set;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.collections.collection.ReadOnlyCollection;
import net.digitalid.utility.freezable.annotations.Freezable;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.annotations.type.ReadOnly;

/**
 * This interface provides read-only access to {@link Set sets} and should <em>never</em> be cast away (unless external code requires it).
 * It is recommended to use only {@link Freezable} or {@link Immutable} types for the elements.
 */
@ReadOnly(FreezableSet.class)
public interface ReadOnlySet<E> extends ReadOnlyCollection<E> {
    
    /* -------------------------------------------------- Cloneable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableSet<E> clone();
    
}
