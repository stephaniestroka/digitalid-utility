package net.digitalid.utility.console.exceptions;

import net.digitalid.utility.console.Option;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This exception is thrown when an {@link Option option} is escaped.
 */
@Immutable
public class EscapeOptionException extends Exception {}
