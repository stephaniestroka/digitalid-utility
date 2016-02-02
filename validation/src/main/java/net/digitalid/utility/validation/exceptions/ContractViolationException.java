package net.digitalid.utility.validation.exceptions;

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
    
    /**
     * Creates a contract violation exception with the given message.
     */
    protected ContractViolationException(String message) {
        super(message);
    }
    
}
