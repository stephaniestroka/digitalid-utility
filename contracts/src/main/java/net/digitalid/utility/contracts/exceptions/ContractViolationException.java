package net.digitalid.utility.contracts.exceptions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.exceptions.InternalException;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This exception indicates a violated contract.
 * 
 * @see InvariantViolationException
 * @see PreconditionViolationException
 * @see PostconditionViolationException
 */
@Immutable
public class ContractViolationException extends InternalException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ContractViolationException(@Nullable String message, @Captured @Nonnull @NullableElements Object... arguments) {
        super(message, arguments);
    }
    
}
