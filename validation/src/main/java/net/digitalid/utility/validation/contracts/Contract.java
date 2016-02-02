package net.digitalid.utility.validation.contracts;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class stores whether a contract is fulfilled or violated based on a condition.
 */
@Immutable
class Contract {
    
    /* -------------------------------------------------- Condition -------------------------------------------------- */
    
    /**
     * Stores the condition of this contract.
     */
    private final boolean condition;
    
    /**
     * Returns whether this contract is fulfilled.
     */
    @Pure
    protected final boolean isFulfilled() {
        return condition;
    }
    
    /**
     * Returns whether this contract is violated.
     */
    @Pure
    protected final boolean isViolated() {
        return !condition;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates a contract with the given condition.
     */
    protected Contract(boolean condition) {
        this.condition = condition;
    }
    
}
