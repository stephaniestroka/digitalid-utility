package net.digitalid.utility.concurrency.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.type.ThreadSafe;
import net.digitalid.utility.concurrency.exceptions.ReentranceException;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.rootclass.RootClass;
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
    
    /**
     * This class delegates the implementation to a fair reentrant lock.
     */
    private final @Nonnull ReentrantLock reentrantLock = new ReentrantLock(true);
    
    @Impure
    @Override
    public void lock() throws ReentranceException {
        if (reentrantLock.isHeldByCurrentThread()) {
            throw ReentranceException.withNoArguments();
        } else {
            reentrantLock.lock();
        }
    }
    
    @Impure
    @Override
    public void lockInterruptibly() throws ReentranceException, InterruptedException {
        if (reentrantLock.isHeldByCurrentThread()) {
            throw ReentranceException.withNoArguments();
        } else {
            reentrantLock.lockInterruptibly();
        }
    }
    
    @Impure
    @Override
    public boolean tryLock() throws ReentranceException {
        if (reentrantLock.isHeldByCurrentThread()) {
            throw ReentranceException.withNoArguments();
        } else {
            return reentrantLock.tryLock();
        }
    }
    
    @Impure
    @Override
    public boolean tryLock(long time, @Nonnull TimeUnit unit) throws ReentranceException, InterruptedException {
        if (reentrantLock.isHeldByCurrentThread()) {
            throw ReentranceException.withNoArguments();
        } else {
            return reentrantLock.tryLock(time, unit);
        }
    }
    
    @Impure
    @Override
    public void unlock() {
        reentrantLock.unlock();
    }
    
    @Impure
    @Override
    public @Nonnull Condition newCondition() {
        return reentrantLock.newCondition();
    }
    
}
