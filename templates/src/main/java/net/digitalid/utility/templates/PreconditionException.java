package net.digitalid.utility.templates;

import net.digitalid.utility.contracts.exceptions.ContractException;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * A precondition exception indicates that a precondition has been violated.
 */
@Immutable
@GenerateBuilder
@GenerateSubclass
public abstract class PreconditionException extends ContractException {}
