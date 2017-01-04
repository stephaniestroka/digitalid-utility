package net.digitalid.utility.contracts.exceptions;

import net.digitalid.utility.contracts.Validate;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * An invariant exception indicates that an invariant has been violated.
 * 
 * @see Validate
 */
@Immutable
public abstract class InvariantException extends ContractException {}
