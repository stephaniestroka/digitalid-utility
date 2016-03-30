package net.digitalid.utility.math;

import java.math.BigInteger;

import javax.annotation.Nonnull;

import net.digitalid.utility.generator.conversion.Convertible;
import net.digitalid.utility.validation.annotations.math.Positive;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class models a multiplicative group with unknown order.
 */
@Immutable
public final class GroupWithUnknownOrder extends Group<GroupWithUnknownOrder> implements Convertible {
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new multiplicative group with the given modulus.
     * 
     * @param modulus the modulus of the new group.
     */
    private GroupWithUnknownOrder(@Nonnull @Positive BigInteger modulus) {
        super(modulus);
    }
    
    /**
     * Creates a new multiplicative group with the given modulus.
     * 
     * @param modulus the modulus of the new group.
     * 
     * @return a new multiplicative group with the given modulus.
     */
    @Pure
    public static @Nonnull GroupWithUnknownOrder get(@Nonnull @Positive BigInteger modulus) {
        return new GroupWithUnknownOrder(modulus);
    }
    
}
