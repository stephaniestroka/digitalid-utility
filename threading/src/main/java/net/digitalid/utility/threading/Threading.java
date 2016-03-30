package net.digitalid.utility.threading;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Utility;

/**
 * This class facilitates the handling of threads.
 */
@Utility
public final class Threading {
    
    /* -------------------------------------------------- Main Thread -------------------------------------------------- */
    
    /**
     * Stores the main thread.
     */
    private static final @Nonnull Thread main = Thread.currentThread();
    
    /**
     * Returns whether the current thread is the main thread.
     */
    @Pure
    public static final boolean isMainThread() {
        return Thread.currentThread() == main;
    }
    
}
