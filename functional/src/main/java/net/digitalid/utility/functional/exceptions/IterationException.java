package net.digitalid.utility.functional.exceptions;

import net.digitalid.utility.exceptions.UncheckedException;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This exception allows to tunnel checked exceptions through the methods of {@link Iterable}.
 */
@Immutable
public abstract class IterationException extends UncheckedException {}
