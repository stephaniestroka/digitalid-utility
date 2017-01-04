package net.digitalid.utility.contracts;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.contracts.exceptions.InvariantException;
import net.digitalid.utility.contracts.exceptions.InvariantExceptionBuilder;
import net.digitalid.utility.string.Strings;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class makes it easier to validate class invariants.
 * Usage: {@code Validate.that(condition).orThrow(message)}.
 */
@Immutable
public final class Validate extends Constraint {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    private Validate(boolean condition) {
        super(condition);
    }
    
    /* -------------------------------------------------- Instances -------------------------------------------------- */
    
    private static final @Nonnull Validate FULFILLED = new Validate(true);
    
    private static final @Nonnull Validate VIOLATED = new Validate(false);
    
    /* -------------------------------------------------- Evaluation -------------------------------------------------- */
    
    /**
     * Returns a invariant with the given condition that needs to be checked with {@link #orThrow(java.lang.String, java.lang.Object...)}.
     */
    @Pure
    public static @Nonnull Validate that(boolean condition) {
        return condition ? FULFILLED : VIOLATED;
    }
    
    /**
     * Checks whether the invariant returned by {@link #that(boolean)} is fulfilled and throws an {@link InvariantException} with the given message otherwise.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Pure
    public void orThrow(@Nonnull String message, @Captured @Nonnull @NullableElements Object... arguments) throws InvariantException {
        if (isViolated()) { throw InvariantExceptionBuilder.withMessage(Strings.format(message, arguments)).build(); }
    }
    
}
