package net.digitalid.utility.property.set;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.annotations.type.ThreadSafe;
import net.digitalid.utility.collections.set.ReadOnlySet;
import net.digitalid.utility.property.PropertyImplementation;
import net.digitalid.utility.threading.Threading;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * This class implements the {@link ReadOnlySetProperty}.
 * 
 * @see WritableSetPropertyImplementation
 */
@Mutable
@ThreadSafe
public abstract class ReadOnlySetPropertyImplementation<V, R extends ReadOnlySet<@Nonnull @Valid V>, X extends Exception, O extends ReadOnlySetProperty.Observer<V, R, X, O, P>, P extends ReadOnlySetProperty<V, R, X, O, P>> extends PropertyImplementation<O, ReadOnlySetProperty.Observer<V, R, X, O, P>> implements ReadOnlySetProperty<V, R, X, O, P> {
    
    /* -------------------------------------------------- Asynchronous Observer -------------------------------------------------- */
    
    /**
     * An asynchronous observer executes the notifications on a separate thread sequentially.
     */
    @Immutable
    public static class AsynchronousObserver<V, R extends ReadOnlySet<@Nonnull @Valid V>, X extends Exception, O extends ReadOnlySetProperty.Observer<V, R, X, O, P>, P extends ReadOnlySetProperty<V, R, X, O, P>> extends PropertyImplementation.AsynchronousObserver<O> implements ReadOnlySetProperty.Observer<V, R, X, O, P> {
        
        protected AsynchronousObserver(@Captured @Modified @Nonnull O observer) {
            super(observer);
        }
        
        @Impure
        @Override
        public void notify(@Nonnull P property, @NonCaptured @Unmodified @Nonnull @Valid V value, boolean added){
            executorService.submit(() -> observer.notify(property, value, added));
        }
        
    }
    
    /* -------------------------------------------------- Observers -------------------------------------------------- */
    
    @Impure
    @Override
    public boolean registerOnGuiThread(@Captured @Nonnull O observer) {
        return observers.put(observer, (property, value, added) -> Threading.runOnGuiThread(() -> observer.notify(property, value, added))) == null;
    }
    
    @Impure
    @Override
    public boolean registerOnNewThread(@Captured @Nonnull O observer) {
        return observers.put(observer, new AsynchronousObserver<>(observer)) == null;
    }
    
}
