package net.digitalid.utility.math;

import java.math.BigInteger;

import javax.annotation.Nonnull;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.generator.conversion.Convertible;
import net.digitalid.utility.validation.annotations.math.Positive;
import net.digitalid.utility.tuples.annotations.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class models a multiplicative group with known order.
 */
@Immutable
public final class GroupWithKnownOrder extends Group<GroupWithKnownOrder> implements Convertible {
    
    /* -------------------------------------------------- Order -------------------------------------------------- */
    
    /**
     * Stores the order of this group.
     * 
     * @invariant order.compareTo(getModulus()) == -1 : "The order is smaller than the modulus.";
     */
    private final @Nonnull @Positive BigInteger order;
    
    /**
     * Returns the order of this group.
     * 
     * @return the order of this group.
     * 
     * @ensure order.compareTo(getModulus()) == -1 : "The order is smaller than the modulus.";
     */
    @Pure
    public @Nonnull @Positive BigInteger getOrder() {
        return order;
    }
    
    /**
     * Returns a new group with the same modulus but without the order.
     * 
     * @return a new group with the same modulus but without the order.
     */
    @Pure
    public @Nonnull GroupWithUnknownOrder dropOrder() {
        return GroupWithUnknownOrder.get(getModulus());
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new multiplicative group with the given modulus and order.
     * 
     * @param modulus the modulus of the new group.
     * @param order the order of the new group.
     * 
     * @require order.compareTo(modulus) == -1 : "The order is smaller than the modulus.";
     */
    private GroupWithKnownOrder(@Nonnull @Positive BigInteger modulus, @Nonnull @Positive BigInteger order) {
        super(modulus);
        
        Require.that(order.compareTo(BigInteger.ZERO) == 1 && order.compareTo(modulus) == -1).orThrow("The order is positive and smaller than the modulus.");
        
        this.order = order;
    }
    
    /**
     * Creates a new multiplicative group with the given modulus and order.
     * 
     * @param modulus the modulus of the new group.
     * @param order the order of the new group.
     * 
     * @return a new multiplicative group with the given modulus and order.
     * 
     * @require order.compareTo(modulus) == -1 : "The order is smaller than the modulus.";
     */
    @Pure
    public static @Nonnull GroupWithKnownOrder get(@Nonnull @Positive BigInteger modulus, @Nonnull @Positive BigInteger order) {
        return new GroupWithKnownOrder(modulus, order);
    }

}
