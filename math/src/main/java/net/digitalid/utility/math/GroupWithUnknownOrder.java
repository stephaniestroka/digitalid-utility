package net.digitalid.utility.math;

import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class models a multiplicative group with unknown order.
 */
@Immutable
@GenerateBuilder
@GenerateSubclass
public abstract class GroupWithUnknownOrder extends Group {}
