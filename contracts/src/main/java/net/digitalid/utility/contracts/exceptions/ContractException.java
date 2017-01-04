package net.digitalid.utility.contracts.exceptions;

import net.digitalid.utility.exceptions.InternalException;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * A contract exception indicates that a contract has been violated.
 * 
 * @see InvariantException
 * @see PreconditionException
 * @see PostconditionException
 */
@Immutable
public abstract class ContractException extends InternalException {}
