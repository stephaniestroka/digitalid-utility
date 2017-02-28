package net.digitalid.utility.templates;

import net.digitalid.utility.exceptions.UncheckedException;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This exception allows to tunnel checked exceptions through the methods of {@link Iterable}.
 */
@Immutable
@GenerateBuilder
@GenerateSubclass
public abstract class IterationException extends UncheckedException {}
