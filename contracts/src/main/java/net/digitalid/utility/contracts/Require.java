package net.digitalid.utility.contracts;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.contracts.exceptions.PreconditionViolationException;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class makes it easier to validate preconditions.
 * Usage: {@code Require.that(condition).orThrow(message)}.
 */
@Immutable
public final class Require extends Constraint {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    private Require(boolean condition) {
        super(condition);
    }
    
    /* -------------------------------------------------- Instances -------------------------------------------------- */
    
    private static final @Nonnull Require FULFILLED = new Require(true);
    
    private static final @Nonnull Require VIOLATED = new Require(false);
    
    /* -------------------------------------------------- Evaluation -------------------------------------------------- */
    
    /**
     * Returns a non-nullable precondition with the given condition that needs to be checked with {@link #orThrow(java.lang.String, java.lang.Object...)}.
     */
    @Pure
    public static @Nonnull Require that(boolean condition) {
        return condition ? FULFILLED : VIOLATED;
    }
    
    /**
     * Checks whether the precondition returned by {@link #that(boolean)} is fulfilled and throws a {@link PreconditionViolationException} with the given message otherwise.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Pure
    public void orThrow(@Nullable String message, @Nullable Object... arguments) throws PreconditionViolationException {
        if (isViolated()) { throw PreconditionViolationException.with(message, arguments); }
    }
    
}
