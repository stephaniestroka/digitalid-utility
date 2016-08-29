package net.digitalid.utility.rootclass;

import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * The root class for all custom classes whose initialization does not throw a checked exception.
 */
@Mutable
public abstract class RootClass extends RootClassWithException<RuntimeException> {}
