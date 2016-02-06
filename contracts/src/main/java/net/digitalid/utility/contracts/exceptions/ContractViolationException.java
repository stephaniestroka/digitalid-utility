package net.digitalid.utility.contracts.exceptions;

import net.digitalid.utility.exceptions.InternalException;

/**
 * This exception indicates a violated contract.
 * 
 * @see InvariantViolationException
 * @see PreconditionViolationException
 * @see PostconditionViolationException
 */
public class ContractViolationException extends InternalException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ContractViolationException(String message) {
        super(message);
    }
    
}
