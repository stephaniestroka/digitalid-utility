package net.digitalid.utility.validation.contracts;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.exceptions.InvariantViolationException;

/**
 * This class makes it easier to validate class invariants.
 * Usage: {@code Validate.that(condition).orThrow(message)}.
 */
@Immutable
public final class Validate extends Contract {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates an invariant with the given condition.
     */
    private Validate(boolean condition) {
        super(condition);
    }
    
    /* -------------------------------------------------- Instances -------------------------------------------------- */
    
    /**
     * Stores a fulfilled invariant.
     */
    private static final @Nonnull Validate FULFILLED = new Validate(true);
    
    /**
     * Stores a violated invariant.
     */
    private static final @Nonnull Validate VIOLATED = new Validate(false);
    
    /* -------------------------------------------------- Evaluation -------------------------------------------------- */
    
    /**
     * Returns an invariant with the given condition that needs to be checked with {@link #orThrow(java.lang.String)}.
     */
    @Pure
    public static @Nonnull Validate that(boolean condition) {
        return condition ? FULFILLED : VIOLATED;
    }
    
    /**
     * Checks whether the invariant returned by {@link #that(boolean)} is fulfilled and throws an {@link InvariantViolationException} otherwise.
     */
    @Pure
    public void orThrow(String message) throws InvariantViolationException {
        if (isViolated()) { throw InvariantViolationException.with(message); }
    }
    
}
