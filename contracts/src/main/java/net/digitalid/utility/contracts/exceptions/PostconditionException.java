package net.digitalid.utility.contracts.exceptions;

import net.digitalid.utility.contracts.Ensure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * A postcondition exception indicates that a postcondition has been violated.
 * 
 * @see Ensure
 */
@Immutable
public abstract class PostconditionException extends ContractException {}
