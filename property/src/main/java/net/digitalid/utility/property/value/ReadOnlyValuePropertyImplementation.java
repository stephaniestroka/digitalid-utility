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
public abstract class ReadOnlyValuePropertyImplementation<VALUE, EXCEPTION extends Exception, OBSERVER extends ValueObserver<VALUE, EXCEPTION, OBSERVER, PROPERTY>, PROPERTY extends ReadOnlyValueProperty<VALUE, EXCEPTION, OBSERVER, PROPERTY>> extends PropertyImplementation<OBSERVER, ValueObserver<VALUE, EXCEPTION, OBSERVER, PROPERTY>> implements ReadOnlyValueProperty<VALUE, EXCEPTION, OBSERVER, PROPERTY> {
    
    /* -------------------------------------------------- Asynchronous Observer -------------------------------------------------- */
    
    /**
     * An asynchronous observer executes the notifications on a separate thread sequentially.
     */
    @Immutable
    public static class AsynchronousObserver<VALUE, EXCEPTION extends Exception, OBSERVER extends ValueObserver<VALUE, EXCEPTION, OBSERVER, PROPERTY>, PROPERTY extends ReadOnlyValueProperty<VALUE, EXCEPTION, OBSERVER, PROPERTY>> extends PropertyImplementation.AsynchronousObserver<OBSERVER> implements ValueObserver<VALUE, EXCEPTION, OBSERVER, PROPERTY> {
        
        protected AsynchronousObserver(@Captured @Modified @Nonnull OBSERVER observer) {
            super(observer);
        }
        
        @Impure
        @Override
        public void notify(@Nonnull PROPERTY property, @NonCaptured @Unmodified @Valid VALUE oldValue, @NonCaptured @Unmodified @Valid VALUE newValue) {
            executorService.submit(() -> observer.notify(property, oldValue, newValue));
        }
        
    }
    
    /* -------------------------------------------------- Observers -------------------------------------------------- */
    
    @Impure
    @Override
    public boolean registerOnGuiThread(@Captured @Nonnull OBSERVER observer) {
        return observers.put(observer, (property, oldValue, newValue) -> Threading.runOnGuiThread(() -> observer.notify(property, oldValue, newValue))) == null;
    }
    
    @Impure
    @Override
    public boolean registerOnNewThread(@Captured @Nonnull OBSERVER observer) {
        return observers.put(observer, new AsynchronousObserver<>(observer)) == null;
    }
    
}
