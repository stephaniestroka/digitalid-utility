package net.digitalid.utility.interfaces;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * Classes with a non-reentrant lock should implement this interface.
 */
@Mutable
public interface Locking {
    
    /**
     * Returns whether the lock is held by the current thread.
     */
    @Pure
    public boolean isLockHeldByCurrentThread();
    
}
