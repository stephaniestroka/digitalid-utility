package net.digitalid.utility.validation.contracts;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.exceptions.PreconditionViolationException;

/**
 * This class makes it easier to validate preconditions.
 * Usage: {@code Require.that(condition).orThrow(message)}.
 */
@Immutable
public final class Require extends Contract {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates a precondition with the given condition.
     */
    private Require(boolean condition) {
        super(condition);
    }
    
    /* -------------------------------------------------- Instances -------------------------------------------------- */
    
    /**
     * Stores a fulfilled precondition.
     */
    private static final @Nonnull Require FULFILLED = new Require(true);
    
    /**
     * Stores a violated precondition.
     */
    private static final @Nonnull Require VIOLATED = new Require(false);
    
    /* -------------------------------------------------- Evaluation -------------------------------------------------- */
    
    /**
     * Returns a precondition with the given condition that needs to be checked with {@link #orThrow(java.lang.String)}.
     */
    @Pure
    public static @Nonnull Require that(boolean condition) {
        return condition ? FULFILLED : VIOLATED;
    }
    
    /**
     * Checks whether the precondition returned by {@link #that(boolean)} is fulfilled and throws a {@link PreconditionViolationException} otherwise.
     */
    @Pure
    public void orThrow(String message) throws PreconditionViolationException {
        if (isViolated()) { throw PreconditionViolationException.with(message); }
    }
    
}
