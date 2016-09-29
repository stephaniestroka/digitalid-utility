package net.digitalid.utility.property.map;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.annotations.type.ThreadSafe;
import net.digitalid.utility.collections.map.ReadOnlyMap;
import net.digitalid.utility.property.PropertyImplementation;
import net.digitalid.utility.threading.Threading;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * This class implements the {@link ReadOnlyMapProperty}.
 * 
 * @see WritableMapPropertyImplementation
 */
@Mutable
@ThreadSafe
public abstract class ReadOnlyMapPropertyImplementation<K, V, R extends ReadOnlyMap<@Nonnull @Valid("key") K, @Nonnull @Valid V>, X extends Exception, O extends ReadOnlyMapProperty.Observer<K, V, R, X, O, P>, P extends ReadOnlyMapProperty<K, V, R, X, O, P>> extends PropertyImplementation<O, ReadOnlyMapProperty.Observer<K, V, R, X, O, P>> implements ReadOnlyMapProperty<K, V, R, X, O, P> {
    
    /* -------------------------------------------------- Asynchronous Observer -------------------------------------------------- */
    
    /**
     * An asynchronous observer executes the notifications on a separate thread sequentially.
     */
    @Immutable
    public static class AsynchronousObserver<K, V, R extends ReadOnlyMap<@Nonnull @Valid("key") K, @Nonnull @Valid V>, X extends Exception, O extends ReadOnlyMapProperty.Observer<K, V, R, X, O, P>, P extends ReadOnlyMapProperty<K, V, R, X, O, P>> extends PropertyImplementation.AsynchronousObserver<O> implements ReadOnlyMapProperty.Observer<K, V, R, X, O, P> {
        
        protected AsynchronousObserver(@Captured @Modified @Nonnull O observer) {
            super(observer);
        }
        
        @Impure
        @Override
        public void notify(@Nonnull P property, @NonCaptured @Unmodified @Nonnull @Valid("key") K key, @NonCaptured @Unmodified @Nonnull @Valid V value, boolean added) {
            executorService.submit(() -> observer.notify(property, key, value, added));
        }
        
    }
    
    /* -------------------------------------------------- Observers -------------------------------------------------- */
    
    @Impure
    @Override
    public boolean registerOnGuiThread(@Captured @Nonnull O observer) {
        return observers.put(observer, (property, key, value, added) -> Threading.runOnGuiThread(() -> observer.notify(property, key, value, added))) == null;
    }
    
    @Impure
    @Override
    public boolean registerOnNewThread(@Captured @Nonnull O observer) {
        return observers.put(observer, new AsynchronousObserver<>(observer)) == null;
    }
    
}
