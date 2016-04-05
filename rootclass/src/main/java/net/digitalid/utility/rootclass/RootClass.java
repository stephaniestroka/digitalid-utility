package net.digitalid.utility.rootclass;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.CallSuper;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.type.Mutable;
import net.digitalid.utility.casting.CastableClass;

/**
 * All custom classes in the Digital ID Library extend this root class.
 */
@Mutable
public abstract class RootClass extends CastableClass implements RootInterface {
    
    /* -------------------------------------------------- Validatable -------------------------------------------------- */
    
    @Pure
    @Override
    @CallSuper
    public void validate() {}
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    public abstract boolean equals(@Nullable Object object);
    
    @Pure
    @Override
    public abstract int hashCode();
    
    @Pure
    @Override
    public abstract @Nonnull String toString();
    
}
