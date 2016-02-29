package net.digitalid.utility.contracts;

/**
 * This class stores whether a contract is fulfilled or violated based on a condition.
 */
public class Contract {
    
    /* -------------------------------------------------- Condition -------------------------------------------------- */
    
    private final boolean condition;
    
    protected final boolean isFulfilled() {
        return condition;
    }
    
    protected final boolean isViolated() {
        return !condition;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected Contract(boolean condition) {
        this.condition = condition;
    }
    
}
