package net.digitalid.utility.contracts;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.contracts.exceptions.PreconditionException;
import net.digitalid.utility.contracts.exceptions.PreconditionExceptionBuilder;
import net.digitalid.utility.string.Strings;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
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
     * Returns a precondition with the given condition that needs to be checked with {@link #orThrow(java.lang.String, java.lang.Object...)}.
     */
    @Pure
    public static @Nonnull Require that(boolean condition) {
        return condition ? FULFILLED : VIOLATED;
    }
    
    /**
     * Checks whether the precondition returned by {@link #that(boolean)} is fulfilled and throws a {@link PreconditionException} with the given message otherwise.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Pure
    public void orThrow(@Nonnull String message, @Captured @Nonnull @NullableElements Object... arguments) throws PreconditionException {
        if (isViolated()) { throw PreconditionExceptionBuilder.withMessage(Strings.format(message, arguments)).build(); }
    }
    
}
