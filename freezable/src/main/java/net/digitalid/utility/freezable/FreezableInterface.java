package net.digitalid.utility.freezable;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.freezable.annotations.Freezable;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.freezable.annotations.NonFrozenRecipient;
import net.digitalid.utility.validation.annotations.method.Chainable;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * Classes that implement this interface allow their objects to transition from a {@link Mutable mutable} into an {@link Immutable immutable} state.
 */
@Freezable(ReadOnlyInterface.class)
public interface FreezableInterface extends ReadOnlyInterface {
    
    /**
     * Freezes this object and thus makes it immutable.
     */
    @Impure
    @NonFrozenRecipient
    public @Chainable @Nonnull @Frozen ReadOnlyInterface freeze();
    
}
