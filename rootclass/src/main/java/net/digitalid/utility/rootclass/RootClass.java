package net.digitalid.utility.rootclass;

import net.digitalid.utility.casting.CastableClass;
import net.digitalid.utility.annotations.method.CallSuper;
import net.digitalid.utility.annotations.method.Pure;

/**
 * All custom classes in the Digital ID Library extend this root class.
 */
public abstract class RootClass extends CastableClass implements RootInterface {
    
    @Pure
    @Override
    public abstract boolean equals(Object e);
    
    @Pure
    @Override
    public abstract int hashCode();
    
    @Pure
    @Override
    public abstract String toString();
    
    @Pure
    @Override
    @CallSuper
    public void validate() {}
    
}
