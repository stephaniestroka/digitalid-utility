package net.digitalid.utility.conversion.exceptions;

import net.digitalid.utility.exceptions.ExternalException;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * A conversion exception indicates a problem when converting or recovering an object.
 * 
 * @see RecoveryException
 * @see ConnectionException
 */
@Immutable
public abstract class ConversionException extends ExternalException {}
