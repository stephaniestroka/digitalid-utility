/*
 * Copyright (C) 2017 Synacts GmbH, Switzerland (info@synacts.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.digitalid.utility.property;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Unspecifiable;
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
import net.digitalid.utility.property.map.ReadOnlyMapPropertyImplementation;
import net.digitalid.utility.property.set.ReadOnlySetPropertyImplementation;
import net.digitalid.utility.property.value.ReadOnlyValuePropertyImplementation;
import net.digitalid.utility.rootclass.RootClass;
import net.digitalid.utility.threading.NamedThreadFactory;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements the observer registration of {@link Property properties}.
 * 
 * @see ReadOnlyMapPropertyImplementation
 * @see ReadOnlySetPropertyImplementation
 * @see ReadOnlyValuePropertyImplementation
 */
@Mutable
@ThreadSafe
public abstract class PropertyImplementation<@Unspecifiable OBSERVER extends Observer, @Unspecifiable GENERIC_OBSERVER extends Observer> extends RootClass implements Property<OBSERVER> {
    
    /* -------------------------------------------------- Asynchronous Observer -------------------------------------------------- */
    
    /**
     * An asynchronous observer executes the notifications on a separate thread sequentially.
     */
    @Immutable
    public static class AsynchronousObserver<OBSERVER extends Observer> implements Observer {
        
        private final static @Nonnull ThreadFactory threadFactory = NamedThreadFactory.with("Observer");
        
        protected final @Nonnull ExecutorService executorService = Executors.newSingleThreadExecutor(threadFactory);
        
        protected final @Nonnull OBSERVER observer;
        
        protected AsynchronousObserver(@Captured @Modified @Nonnull OBSERVER observer) {
            this.observer = observer;
        }
        
    }
    
    /* -------------------------------------------------- Observers -------------------------------------------------- */
    
    /**
     * Stores the registered observers mapped to the respective observer that is executed instead.
     */
    protected final @Nonnull ConcurrentHashMap<@Nonnull OBSERVER, @Nonnull GENERIC_OBSERVER> observers = ConcurrentHashMapBuilder.buildWithInitialCapacity(1);
    
    @Impure
    @Override
    @SuppressWarnings("unchecked")
    public boolean register(@Captured @Nonnull OBSERVER observer) {
        return observers.put(observer, (GENERIC_OBSERVER) observer) == null;
    }
    
    @Impure
    @Override
    public boolean deregister(@NonCaptured @Nonnull OBSERVER observer) {
        return observers.remove(observer) != null;
    }
    
    @Pure
    @Override
    public boolean isRegistered(@NonCaptured @Nonnull OBSERVER observer) {
        return observers.containsKey(observer);
    }
    
    /* -------------------------------------------------- Lock -------------------------------------------------- */
    
    /**
     * Stores a non-reentrant lock to guarantee that this property reflects the notified change for each observer.
     * This can only be guaranteed if observers are prevented from changing this property during the notification.
     */
    protected final @Nonnull NonReentrantLock lock = NonReentrantLockBuilder.build();
    
    @Pure
    @Override
    public boolean isLockHeldByCurrentThread() {
        return lock.isLockHeldByCurrentThread();
    }
    
}
