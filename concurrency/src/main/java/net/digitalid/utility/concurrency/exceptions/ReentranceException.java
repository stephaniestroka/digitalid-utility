package net.digitalid.utility.concurrency.exceptions;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.concurrency.lock.NonReentrantLock;
import net.digitalid.utility.exceptions.InternalException;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This exception indicates that a thread tried to acquire a {@link NonReentrantLock non-reentrant lock} which it already holds.
 */
@Immutable
public class ReentranceException extends InternalException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ReentranceException() {
        super("The thread $ tried to acquire a non-reentrant lock which it already holds.", Thread.currentThread().getName());
    }
    
    /**
     * Returns a new reentrance exception.
     */
    @Pure
    public static @Nonnull ReentranceException withNoArguments() {
        return new ReentranceException();
    }
    
}
