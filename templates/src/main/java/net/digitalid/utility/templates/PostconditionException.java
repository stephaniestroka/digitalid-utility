package net.digitalid.utility.templates;

import net.digitalid.utility.contracts.exceptions.ContractException;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * A postcondition exception indicates that a postcondition has been violated.
 */
@Immutable
@GenerateBuilder
@GenerateSubclass
public abstract class PostconditionException extends ContractException {}
