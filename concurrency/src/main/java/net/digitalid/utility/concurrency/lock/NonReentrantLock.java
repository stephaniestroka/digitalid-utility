package net.digitalid.utility.concurrency.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.type.ThreadSafe;
import net.digitalid.utility.concurrency.exceptions.ReentranceException;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.rootclass.RootClass;
import net.digitalid.utility.validation.annotations.generation.Derive;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements a non-reentrant lock which throws a {@link ReentranceException} if a thread tries to acquire a non-reentrant lock that it already holds.
 * <p>
 * It is highly recommended that you always follow a (successful) call to one of the lock methods immediately with a try-finally block like
 * <pre>{@code
 * lock.lock();
 * try {
 *     // Do the synchronized operations here.
 * } finally {
 *     lock.unlock();
 * }
 * }</pre>
 */
@Mutable
@ThreadSafe
@GenerateBuilder
@GenerateSubclass
public abstract class NonReentrantLock extends RootClass implements Lock {
    
    /* -------------------------------------------------- Lock -------------------------------------------------- */
    
    /**
     * Returns the fair reentrant lock which is used to implement this non-reentrant lock.
     * This method intentionally allows to acquire the lock reentrantly for special cases.
     */
    @Pure
    @Derive("new ReentrantLock(true)")
    public abstract @Nonnull ReentrantLock getReentrantLock();
    
    @Impure
    @Override
    public void lock() throws ReentranceException {
        if (getReentrantLock().isHeldByCurrentThread()) {
            throw ReentranceException.withNoArguments();
        } else {
            getReentrantLock().lock();
        }
    }
    
    @Impure
    @Override
    public void lockInterruptibly() throws ReentranceException, InterruptedException {
        if (getReentrantLock().isHeldByCurrentThread()) {
            throw ReentranceException.withNoArguments();
        } else {
            getReentrantLock().lockInterruptibly();
        }
    }
    
    @Impure
    @Override
    public boolean tryLock() throws ReentranceException {
        if (getReentrantLock().isHeldByCurrentThread()) {
            throw ReentranceException.withNoArguments();
        } else {
            return getReentrantLock().tryLock();
        }
    }
    
    @Impure
    @Override
    public boolean tryLock(long time, @Nonnull TimeUnit unit) throws ReentranceException, InterruptedException {
        if (getReentrantLock().isHeldByCurrentThread()) {
            throw ReentranceException.withNoArguments();
        } else {
            return getReentrantLock().tryLock(time, unit);
        }
    }
    
    @Impure
    @Override
    public void unlock() {
        getReentrantLock().unlock();
    }
    
    @Impure
    @Override
    public @Nonnull Condition newCondition() {
        return getReentrantLock().newCondition();
    }
    
}
