package net.digitalid.utility.contracts.exceptions;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * A precondition exception indicates that a precondition has been violated.
 * 
 * @see Require
 */
@Immutable
public abstract class PreconditionException extends ContractException {}
