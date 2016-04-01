package net.digitalid.utility.rootclass;

import net.digitalid.utility.casting.Castable;
import net.digitalid.utility.validation.validatable.Validatable;

/**
 * All custom interfaces in the Digital ID Library extend this root interface.
 * 
 * @see RootClass
 */
public interface RootInterface extends Castable, Validatable {
    
    @Override
    public boolean equals(Object e);
    
    @Override
    public int hashCode();
    
    @Override
    public String toString();
    
}
