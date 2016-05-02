package net.digitalid.utility.rootclass;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.casting.Castable;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.validatable.Validatable;

/**
 * All custom interfaces in the Digital ID Library extend this root interface.
 * 
 * @see RootClass
 */
@Mutable
public interface RootInterface extends Castable, Validatable {
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    // The following methods will always be implemented by classes but are important when generating an implementation directly from an interface.
    
    @Pure
    @Override
    public boolean equals(@Nullable Object object);
    
    @Pure
    @Override
    public int hashCode();
    
    @Pure
    @Override
    public @Nonnull String toString();
    
}
