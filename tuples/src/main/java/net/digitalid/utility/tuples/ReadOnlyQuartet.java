package net.digitalid.utility.tuples;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.readonly.ReadOnly;
import net.digitalid.utility.validation.reference.Capturable;
import net.digitalid.utility.validation.state.Pure;

/**
 * This interface models a {@link ReadOnly read-only} quartet.
 * 
 * @see FreezableQuartet
 */
public interface ReadOnlyQuartet<E0, E1, E2, E3> extends ReadOnlyTriplet<E0, E1, E2> {
    
    /* -------------------------------------------------- Getter -------------------------------------------------- */
    
    /**
     * Returns the third element of this tuple.
     * 
     * @return the third element of this tuple.
     */
    @Pure
    public @Nullable E3 getNullableElement3();
    
    /**
     * Returns the third element of this tuple.
     * 
     * @return the third element of this tuple.
     * 
     * @require getNullableElement3() != null : "The element is not null.";
     */
    @Pure
    public @Nonnull E3 getNonNullableElement3();
    
    /* -------------------------------------------------- Cloneable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableQuartet<E0, E1, E2, E3> clone();
    
}