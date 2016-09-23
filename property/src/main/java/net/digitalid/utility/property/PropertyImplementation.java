package net.digitalid.utility.property;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.collections.set.FreezableLinkedHashSetBuilder;
import net.digitalid.utility.collections.set.FreezableSet;
import net.digitalid.utility.collections.set.ReadOnlySet;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.property.value.ReadOnlyValuePropertyImplementation;
import net.digitalid.utility.rootclass.RootClass;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements the observer registration of {@link Property properties}.
 * 
 * @see ReadOnlyValuePropertyImplementation
 */
@Mutable
public abstract class PropertyImplementation<O extends Property.Observer> extends RootClass implements Property<O> {
    
    /* -------------------------------------------------- Observers -------------------------------------------------- */
    
    /**
     * Stores null until the first observer registers and then the registered observers.
     */
    private @Nullable @NonFrozen FreezableSet<@Nonnull O> observers;
    
    @Impure
    @Override
    public boolean register(@Captured @Nonnull O observer) {
        if (observers == null) { observers = FreezableLinkedHashSetBuilder.buildWithInitialCapacity(1); }
        return observers.add(observer);
    }
    
    @Impure
    @Override
    public boolean deregister(@NonCaptured @Nonnull O observer) {
        if (observers != null) { return observers.remove(observer); } else { return false; }
    }
    
    @Pure
    @Override
    public boolean isRegistered(@NonCaptured @Nonnull O observer) {
        return observers != null && observers.contains(observer);
    }
    
    /**
     * Returns whether this property has observers.
     */
    @Pure
    protected boolean hasObservers() {
        return observers != null && !observers.isEmpty();
    }
    
    /**
     * Returns the observers of this property.
     */
    @Pure
    protected @Nonnull @NonFrozen ReadOnlySet<@Nonnull O> getObservers() {
        if (observers == null) { observers = FreezableLinkedHashSetBuilder.buildWithInitialCapacity(0); }
        return observers;
    }
    
}
