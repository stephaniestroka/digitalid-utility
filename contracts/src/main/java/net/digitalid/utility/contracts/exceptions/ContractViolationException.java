package net.digitalid.utility.contracts.exceptions;

import javax.annotation.Nullable;

import net.digitalid.utility.exceptions.InternalException;
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
    
    protected ContractViolationException(@Nullable String message, @Nullable Object... arguments) {
        super(message, arguments);
    }
    
}
