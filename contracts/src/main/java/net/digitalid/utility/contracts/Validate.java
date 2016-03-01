package net.digitalid.utility.contracts;

import net.digitalid.utility.contracts.exceptions.InvariantViolationException;

/**
 * This class makes it easier to validate class invariants.
 * Usage: {@code Validate.that(condition).orThrow(message)}.
 */
public final class Validate extends Constraint {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    private Validate(boolean condition) {
        super(condition);
    }
    
    /* -------------------------------------------------- Instances -------------------------------------------------- */
    
    private static final Validate FULFILLED = new Validate(true);
    
    private static final Validate VIOLATED = new Validate(false);
    
    /* -------------------------------------------------- Evaluation -------------------------------------------------- */
    
    /**
     * Returns a non-nullable invariant with the given condition that needs to be checked with {@link #orThrow(java.lang.String)}.
     */
    public static Validate that(boolean condition) {
        return condition ? FULFILLED : VIOLATED;
    }
    
    /**
     * Checks whether the invariant returned by {@link #that(boolean)} is fulfilled and throws an {@link InvariantViolationException} with the given message otherwise.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    public void orThrow(String message, Object... arguments) throws InvariantViolationException {
        if (isViolated()) { throw InvariantViolationException.with(message, arguments); }
    }
    
}
