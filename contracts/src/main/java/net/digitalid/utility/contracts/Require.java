package net.digitalid.utility.contracts;

import net.digitalid.utility.contracts.exceptions.PreconditionViolationException;

/**
 * This class makes it easier to validate preconditions.
 * Usage: {@code Require.that(condition).orThrow(message)}.
 */
public final class Require extends Contract {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    private Require(boolean condition) {
        super(condition);
    }
    
    /* -------------------------------------------------- Instances -------------------------------------------------- */
    
    private static final Require FULFILLED = new Require(true);
    
    private static final Require VIOLATED = new Require(false);
    
    /* -------------------------------------------------- Evaluation -------------------------------------------------- */
    
    /**
     * Returns a non-nullable precondition with the given condition that needs to be checked with {@link #orThrow(java.lang.String)}.
     */
    public static Require that(boolean condition) {
        return condition ? FULFILLED : VIOLATED;
    }
    
    /**
     * Checks whether the precondition returned by {@link #that(boolean)} is fulfilled and throws a {@link PreconditionViolationException} with the given message otherwise.
     */
    public void orThrow(String message) throws PreconditionViolationException {
        if (isViolated()) { throw PreconditionViolationException.with(message); }
    }
    
}
