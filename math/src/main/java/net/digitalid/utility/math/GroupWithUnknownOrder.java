package net.digitalid.utility.math;

import net.digitalid.utility.generator.annotations.GenerateBuilder;
import net.digitalid.utility.generator.annotations.GenerateSubclass;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class models a multiplicative group with unknown order.
 */
@Immutable
@GenerateBuilder
@GenerateSubclass
public abstract class GroupWithUnknownOrder extends Group {}
