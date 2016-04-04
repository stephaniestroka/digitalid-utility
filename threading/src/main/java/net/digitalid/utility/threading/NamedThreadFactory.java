package net.digitalid.utility.threading;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class allows the created threads to be named.
 */
@Immutable
public class NamedThreadFactory implements ThreadFactory {
    
    /* -------------------------------------------------- Fields -------------------------------------------------- */
    
    /**
     * Stores the prefix of the threads.
     */
    private final @Nonnull String prefix;
    
    /**
     * Stores the number of the next thread.
     */
    private final @Nonnull AtomicInteger number = new AtomicInteger(1);
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected NamedThreadFactory(@Nonnull String prefix) {
        this.prefix = prefix + "-";
    }
    
    /**
     * Returns a new thread factory with the given name prefix.
     * 
     * @param prefix the prefix of the threads created by this factory.
     */
    @Pure
    public static @Nonnull NamedThreadFactory with(@Nonnull String prefix) {
        return new NamedThreadFactory(prefix);
    }
    
    /* -------------------------------------------------- Method -------------------------------------------------- */
    
    @Impure
    @Override
    public @Nonnull Thread newThread(@Nonnull Runnable runnable) {
        return new Thread(runnable, prefix + number.getAndIncrement());
    }
    
}
