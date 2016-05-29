package net.digitalid.utility.logging;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.validation.annotations.type.Utility;

/**
 * This class determines the caller of a method.
 */
@Utility
public class Caller {
    
    /* -------------------------------------------------- Configuration -------------------------------------------------- */
    
    /**
     * Stores the index of the caller of the logging method in the stack trace.
     */
    public static final @Nonnull Configuration<Integer> index = Configuration.with(5);
    
    /* -------------------------------------------------- Retrieval -------------------------------------------------- */
    
    /**
     * Returns the entry at the given index in the stack trace.
     */
    @Pure
    public static @Nonnull String get(int index) {
        final @Nonnull StackTraceElement element = Thread.currentThread().getStackTrace()[index];
        return element.getClassName() + "." + element.getMethodName() + ":" + element.getLineNumber();
    }
    
    /**
     * Returns the caller of the logging method.
     */
    @Pure
    public static @Nonnull String get() {
        return get(index.get());
    }
    
}
