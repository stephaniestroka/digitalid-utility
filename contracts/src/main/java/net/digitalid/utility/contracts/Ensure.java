package net.digitalid.utility.contracts;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.contracts.exceptions.PostconditionViolationException;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class makes it easier to validate postconditions.
 * Usage: {@code Ensure.that(condition).orThrow(message)}.
 */
@Immutable
public final class Ensure extends Constraint {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    private Ensure(boolean condition) {
        super(condition);
    }
    
    /* -------------------------------------------------- Instances -------------------------------------------------- */
    
    private static final @Nonnull Ensure FULFILLED = new Ensure(true);
    
    private static final @Nonnull Ensure VIOLATED = new Ensure(false);
    
    /* -------------------------------------------------- Evaluation -------------------------------------------------- */
    
    /**
     * Returns a postcondition with the given condition that needs to be checked with {@link #orThrow(java.lang.String, java.lang.Object...)}.
     */
    @Pure
    public static @Nonnull Ensure that(boolean condition) {
        return condition ? FULFILLED : VIOLATED;
    }
    
    /**
     * Checks whether the postcondition returned by {@link #that(boolean)} is fulfilled and throws a {@link PostconditionViolationException} with the given message otherwise.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Pure
    public void orThrow(@Nullable String message, @Nullable Object... arguments) throws PostconditionViolationException {
        if (isViolated()) { throw PostconditionViolationException.with(message, arguments); }
    }
    
}
