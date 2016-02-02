package net.digitalid.utility.validation.contracts;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.exceptions.PostconditionViolationException;

/**
 * This class makes it easier to validate postconditions.
 * Usage: {@code Ensure.that(condition).orThrow(message)}.
 */
@Immutable
public final class Ensure extends Contract {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates a postcondition with the given condition.
     */
    private Ensure(boolean condition) {
        super(condition);
    }
    
    /* -------------------------------------------------- Instances -------------------------------------------------- */
    
    /**
     * Stores a fulfilled postcondition.
     */
    private static final @Nonnull Ensure FULFILLED = new Ensure(true);
    
    /**
     * Stores a violated postcondition.
     */
    private static final @Nonnull Ensure VIOLATED = new Ensure(false);
    
    /* -------------------------------------------------- Evaluation -------------------------------------------------- */
    
    /**
     * Returns a postcondition with the given condition that needs to be checked with {@link #orThrow(java.lang.String)}.
     */
    @Pure
    public static @Nonnull Ensure that(boolean condition) {
        return condition ? FULFILLED : VIOLATED;
    }
    
    /**
     * Checks whether the postcondition returned by {@link #that(boolean)} is fulfilled and throws a {@link PostconditionViolationException} otherwise.
     */
    @Pure
    public void orThrow(String message) throws PostconditionViolationException {
        if (isViolated()) { throw PostconditionViolationException.with(message); }
    }
    
}
