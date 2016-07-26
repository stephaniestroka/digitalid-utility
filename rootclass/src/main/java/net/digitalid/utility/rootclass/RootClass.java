package net.digitalid.utility.rootclass;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.CallSuper;
import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * All custom classes in the Digital ID Library extend this root class.
 */
@Mutable
public abstract class RootClass implements RootInterface {
    
    /* -------------------------------------------------- Initialization -------------------------------------------------- */
    
    /**
     * Initializes this object after the constructors of the subclasses have been executed.
     */
    @Impure
    @CallSuper
    protected void initialize() {}
    
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
