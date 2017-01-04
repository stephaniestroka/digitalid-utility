package net.digitalid.utility.contracts;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.contracts.exceptions.PostconditionException;
import net.digitalid.utility.contracts.exceptions.PostconditionExceptionBuilder;
import net.digitalid.utility.string.Strings;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
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
     * Checks whether the postcondition returned by {@link #that(boolean)} is fulfilled and throws a {@link PostconditionException} with the given message otherwise.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Pure
    public void orThrow(@Nonnull String message, @Captured @Nonnull @NullableElements Object... arguments) throws PostconditionException {
        if (isViolated()) { throw PostconditionExceptionBuilder.withMessage(Strings.format(message, arguments)).build(); }
    }
    
}
