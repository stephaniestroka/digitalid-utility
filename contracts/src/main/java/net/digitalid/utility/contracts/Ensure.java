package net.digitalid.utility.contracts;

import net.digitalid.utility.contracts.exceptions.PostconditionViolationException;

/**
 * This class makes it easier to validate postconditions.
 * Usage: {@code Ensure.that(condition).orThrow(message)}.
 */
public final class Ensure extends Contract {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    private Ensure(boolean condition) {
        super(condition);
    }
    
    /* -------------------------------------------------- Instances -------------------------------------------------- */
    
    private static final Ensure FULFILLED = new Ensure(true);
    
    private static final Ensure VIOLATED = new Ensure(false);
    
    /* -------------------------------------------------- Evaluation -------------------------------------------------- */
    
    /**
     * Returns a non-nullable postcondition with the given condition that needs to be checked with {@link #orThrow(java.lang.String)}.
     */
    public static Ensure that(boolean condition) {
        return condition ? FULFILLED : VIOLATED;
    }
    
    /**
     * Checks whether the postcondition returned by {@link #that(boolean)} is fulfilled and throws a {@link PostconditionViolationException} with the given message otherwise.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    public void orThrow(String message, Object... arguments) throws PostconditionViolationException {
        if (isViolated()) { throw PostconditionViolationException.with(message, arguments); }
    }
    
}
