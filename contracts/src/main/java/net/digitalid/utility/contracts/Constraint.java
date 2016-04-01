package net.digitalid.utility.contracts;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class stores whether a constraint is fulfilled or violated based on a condition.
 */
@Immutable
public class Constraint {
    
    /* -------------------------------------------------- Condition -------------------------------------------------- */
    
    private final boolean condition;
    
    @Pure
    protected final boolean isFulfilled() {
        return condition;
    }
    
    @Pure
    protected final boolean isViolated() {
        return !condition;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected Constraint(boolean condition) {
        this.condition = condition;
    }
    
}
