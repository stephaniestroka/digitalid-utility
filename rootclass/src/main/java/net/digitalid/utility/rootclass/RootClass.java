package net.digitalid.utility.rootclass;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.type.Mutable;

/**
 * All custom classes in the Digital ID Library extend this root class.
 */
@Mutable
public abstract class RootClass implements RootInterface {
    
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
