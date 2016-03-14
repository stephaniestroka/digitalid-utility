package net.digitalid.utility.rootclass;

import net.digitalid.utility.casting.CastableClass;
import net.digitalid.utility.validation.annotations.method.CallSuper;
import net.digitalid.utility.validation.annotations.method.Pure;

/**
 * All custom classes in the Digital ID Library extend this root class.
 */
public abstract class RootClass extends CastableClass implements RootInterface {
    
    @Pure
    @Override
    @CallSuper
    public void validate() {}
    
}
