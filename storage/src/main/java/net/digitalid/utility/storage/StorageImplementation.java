package net.digitalid.utility.storage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.CallSuper;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.rootclass.RootClass;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements the {@link Storage} interface.
 */
@Mutable
public abstract class StorageImplementation extends RootClass implements Storage {
    
    /* -------------------------------------------------- Initialization -------------------------------------------------- */
    
    @Pure
    @Override
    @CallSuper
    protected void initialize() {
        super.initialize();
        
        final @Nullable Module parentModule = getParentModule();
        if (parentModule != null) { parentModule.addChildStorage(this); }
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean equals(@Nullable Object object) {
        return object == this;
    }
    
    @Pure
    @Override
    public int hashCode() {
        return getHashCode();
    }
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return getFullNameWithUnderlines();
    }
    
}
