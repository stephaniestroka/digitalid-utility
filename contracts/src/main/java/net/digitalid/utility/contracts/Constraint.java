package net.digitalid.utility.contracts;

/**
 * This class stores whether a constraint is fulfilled or violated based on a condition.
 */
public class Constraint {
    
    /* -------------------------------------------------- Condition -------------------------------------------------- */
    
    private final boolean condition;
    
    protected final boolean isFulfilled() {
        return condition;
    }
    
    protected final boolean isViolated() {
        return !condition;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected Constraint(boolean condition) {
        this.condition = condition;
    }
    
}
