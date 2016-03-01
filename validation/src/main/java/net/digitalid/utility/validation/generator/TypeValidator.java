package net.digitalid.utility.validation.generator;

import net.digitalid.utility.validation.annotations.meta.Validator;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * A type validator validates the annotations on the members of a type.
 * It is the only code generator that does not generate code.
 * 
 * @see Validator
 */
@Stateless
public abstract class TypeValidator extends CodeGenerator {}
