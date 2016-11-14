package net.digitalid.utility.property.value;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.annotations.type.ThreadSafe;
import net.digitalid.utility.property.PropertyImplementation;
import net.digitalid.utility.threading.Threading;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * This class implements the {@link ReadOnlyValueProperty}.
 * 
 * @see WritableValuePropertyImplementation
 */
@Mutable
@ThreadSafe
public abstract class ReadOnlyValuePropertyImplementation<V, X extends Exception, O extends ReadOnlyValueProperty.Observer<V, X, O, P>, P extends ReadOnlyValueProperty<V, X, O, P>> extends PropertyImplementation<O, ReadOnlyValueProperty.Observer<V, X, O, P>> implements ReadOnlyValueProperty<V, X, O, P> {
    
    /* -------------------------------------------------- Asynchronous Observer -------------------------------------------------- */
    
    /**
     * An asynchronous observer executes the notifications on a separate thread sequentially.
     */
    @Immutable
    public static class AsynchronousObserver<V, X extends Exception, O extends ReadOnlyValueProperty.Observer<V, X, O, P>, P extends ReadOnlyValueProperty<V, X, O, P>> extends PropertyImplementation.AsynchronousObserver<O> implements ReadOnlyValueProperty.Observer<V, X, O, P> {
        
        protected AsynchronousObserver(@Captured @Modified @Nonnull O observer) {
            super(observer);
        }
        
        @Impure
        @Override
        public void notify(@Nonnull P property, @NonCaptured @Unmodified @Valid V oldValue, @NonCaptured @Unmodified @Valid V newValue) {
            executorService.submit(() -> observer.notify(property, oldValue, newValue));
        }
        
    }
    
    /* -------------------------------------------------- Observers -------------------------------------------------- */
    
    @Impure
    @Override
    public boolean registerOnGuiThread(@Captured @Nonnull O observer) {
        return observers.put(observer, (property, oldValue, newValue) -> Threading.runOnGuiThread(() -> observer.notify(property, oldValue, newValue))) == null;
    }
    
    @Impure
    @Override
    public boolean registerOnNewThread(@Captured @Nonnull O observer) {
        return observers.put(observer, new AsynchronousObserver<>(observer)) == null;
    }
    
}
