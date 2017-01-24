package net.digitalid.utility.concurrency.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.type.ThreadSafe;
import net.digitalid.utility.contracts.exceptions.PreconditionException;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.interfaces.Locking;
import net.digitalid.utility.rootclass.RootClass;
import net.digitalid.utility.validation.annotations.generation.Derive;
import net.digitalid.utility.validation.annotations.lock.LockNotHeldByCurrentThread;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements a non-reentrant lock which throws a {@link PreconditionException} if a thread tries to acquire a non-reentrant lock that it already holds.
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
public abstract class NonReentrantLock extends RootClass implements Lock, Locking {
    
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
    @LockNotHeldByCurrentThread
    public void lock() {
        getReentrantLock().lock();
    }
    
    @Impure
    @Override
    @LockNotHeldByCurrentThread
    public void lockInterruptibly() throws InterruptedException {
        getReentrantLock().lockInterruptibly();
    }
    
    @Impure
    @Override
    @LockNotHeldByCurrentThread
    public boolean tryLock() {
        return getReentrantLock().tryLock();
    }
    
    @Impure
    @Override
    @LockNotHeldByCurrentThread
    public boolean tryLock(long time, @Nonnull TimeUnit unit) throws InterruptedException {
        return getReentrantLock().tryLock(time, unit);
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
    
    /* -------------------------------------------------- Locking -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean isLockHeldByCurrentThread() {
        return getReentrantLock().isHeldByCurrentThread();
    }
    
}
