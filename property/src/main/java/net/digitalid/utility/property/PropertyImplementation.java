package net.digitalid.utility.property;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.type.ThreadSafe;
import net.digitalid.utility.concurrency.lock.NonReentrantLock;
import net.digitalid.utility.concurrency.lock.NonReentrantLockBuilder;
import net.digitalid.utility.concurrency.map.ConcurrentHashMap;
import net.digitalid.utility.concurrency.map.ConcurrentHashMapBuilder;
import net.digitalid.utility.property.value.ReadOnlyValuePropertyImplementation;
import net.digitalid.utility.rootclass.RootClass;
import net.digitalid.utility.threading.NamedThreadFactory;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements the observer registration of {@link Property properties}.
 * 
 * @see ReadOnlyValuePropertyImplementation
 */
@Mutable
@ThreadSafe
public abstract class PropertyImplementation<O extends Property.Observer, Q extends Property.Observer> extends RootClass implements Property<O> {
    
    /* -------------------------------------------------- Asynchronous Observer -------------------------------------------------- */
    
    /**
     * An asynchronous observer executes the notifications on a separate thread sequentially.
     */
    @Immutable
    public static class AsynchronousObserver<O extends Property.Observer> implements Property.Observer {
        
        private final static @Nonnull ThreadFactory threadFactory = NamedThreadFactory.with("Observer");
        
        protected final @Nonnull ExecutorService executorService = Executors.newSingleThreadExecutor(threadFactory);
        
        protected final @Nonnull O observer;
        
        protected AsynchronousObserver(@Captured @Modified @Nonnull O observer) {
            this.observer = observer;
        }
        
    }
    
    /* -------------------------------------------------- Observers -------------------------------------------------- */
    
    /**
     * Stores the registered observers mapped to the respective observer that is executed instead.
     */
    protected final @Nonnull ConcurrentHashMap<@Nonnull O, @Nonnull Q> observers = ConcurrentHashMapBuilder.buildWithInitialCapacity(1);
    
    @Impure
    @Override
    @SuppressWarnings("unchecked")
    public boolean register(@Captured @Nonnull O observer) {
        return observers.put(observer, (Q) observer) == null;
    }
    
    @Impure
    @Override
    public boolean deregister(@NonCaptured @Nonnull O observer) {
        return observers.remove(observer) != null;
    }
    
    @Pure
    @Override
    public boolean isRegistered(@NonCaptured @Nonnull O observer) {
        return observers.containsKey(observer);
    }
    
    /* -------------------------------------------------- Lock -------------------------------------------------- */
    
    /**
     * Stores a non-reentrant lock to guarantee that this property reflects the notified change for each observer.
     * This can only be guaranteed if observers are prevented from changing this property during the notification.
     */
    protected final @Nonnull NonReentrantLock lock = NonReentrantLockBuilder.build();
    
}
