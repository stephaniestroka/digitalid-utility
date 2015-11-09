package net.digitalid.utility.collections.tuples;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.digitalid.utility.annotations.reference.Capturable;
import net.digitalid.utility.annotations.state.Pure;
import net.digitalid.utility.collections.annotations.freezable.NonFrozen;
import net.digitalid.utility.collections.readonly.ReadOnly;

/**
 * This interface models a {@link ReadOnly read-only} triplet.
 * 
 * @see FreezableTriplet
 * @see ReadOnlyQuartet
 */
public interface ReadOnlyTriplet<E0, E1, E2> extends ReadOnlyPair<E0, E1> {
    
    /* -------------------------------------------------- Getter -------------------------------------------------- */
    
    /**
     * Returns the third element of this tuple.
     * 
     * @return the third element of this tuple.
     */
    @Pure
    public @Nullable E2 getNullableElement2();
    
    /**
     * Returns the third element of this tuple.
     * 
     * @return the third element of this tuple.
     * 
     * @require getNullableElement2() != null : "The element is not null.";
     */
    @Pure
    public @Nonnull E2 getNonNullableElement2();
    
    /* -------------------------------------------------- Cloneable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableTriplet<E0, E1, E2> clone();
    
}
