package net.digitalid.utility.property.map;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Unspecifiable;
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
public abstract class ReadOnlyMapPropertyImplementation<@Unspecifiable KEY, @Unspecifiable VALUE, @Unspecifiable READONLY_MAP extends ReadOnlyMap<@Nonnull @Valid("key") KEY, @Nonnull @Valid VALUE>, @Unspecifiable EXCEPTION1 extends Exception, @Unspecifiable EXCEPTION2 extends Exception, @Unspecifiable OBSERVER extends MapObserver<KEY, VALUE, READONLY_MAP, EXCEPTION1, EXCEPTION2, OBSERVER, PROPERTY>, @Unspecifiable PROPERTY extends ReadOnlyMapProperty<KEY, VALUE, READONLY_MAP, EXCEPTION1, EXCEPTION2, OBSERVER, PROPERTY>> extends PropertyImplementation<OBSERVER, MapObserver<KEY, VALUE, READONLY_MAP, EXCEPTION1, EXCEPTION2, OBSERVER, PROPERTY>> implements ReadOnlyMapProperty<KEY, VALUE, READONLY_MAP, EXCEPTION1, EXCEPTION2, OBSERVER, PROPERTY> {
    
    /* -------------------------------------------------- Asynchronous Observer -------------------------------------------------- */
    
    /**
     * An asynchronous observer executes the notifications on a separate thread sequentially.
     */
    @Immutable
    public static class AsynchronousObserver<@Unspecifiable KEY, @Unspecifiable VALUE, @Unspecifiable READONLY_MAP extends ReadOnlyMap<@Nonnull @Valid("key") KEY, @Nonnull @Valid VALUE>, @Unspecifiable EXCEPTION1 extends Exception, @Unspecifiable EXCEPTION2 extends Exception, @Unspecifiable OBSERVER extends MapObserver<KEY, VALUE, READONLY_MAP, EXCEPTION1, EXCEPTION2, OBSERVER, PROPERTY>, @Unspecifiable PROPERTY extends ReadOnlyMapProperty<KEY, VALUE, READONLY_MAP, EXCEPTION1, EXCEPTION2, OBSERVER, PROPERTY>> extends PropertyImplementation.AsynchronousObserver<OBSERVER> implements MapObserver<KEY, VALUE, READONLY_MAP, EXCEPTION1, EXCEPTION2, OBSERVER, PROPERTY> {
        
        protected AsynchronousObserver(@Captured @Modified @Nonnull OBSERVER observer) {
            super(observer);
        }
        
        @Impure
        @Override
        public void notify(@Nonnull PROPERTY property, @NonCaptured @Unmodified @Nonnull @Valid("key") KEY key, @NonCaptured @Unmodified @Nonnull @Valid VALUE value, boolean added) {
            executorService.submit(() -> observer.notify(property, key, value, added));
        }
        
    }
    
    /* -------------------------------------------------- Observers -------------------------------------------------- */
    
    @Impure
    @Override
    public boolean registerOnGuiThread(@Captured @Nonnull OBSERVER observer) {
        return observers.put(observer, (property, key, value, added) -> Threading.runOnGuiThread(() -> observer.notify(property, key, value, added))) == null;
    }
    
    @Impure
    @Override
    public boolean registerOnNewThread(@Captured @Nonnull OBSERVER observer) {
        return observers.put(observer, new AsynchronousObserver<>(observer)) == null;
    }
    
}
