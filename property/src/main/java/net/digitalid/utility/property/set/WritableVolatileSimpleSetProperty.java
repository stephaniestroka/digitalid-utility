package net.digitalid.utility.property.set;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.type.ThreadSafe;
import net.digitalid.utility.collections.set.FreezableSet;
import net.digitalid.utility.collections.set.ReadOnlySet;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.validation.annotations.generation.Default;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * This class simplifies the creation and declaration of {@link WritableVolatileSetProperty}.
 */
@ThreadSafe
@GenerateBuilder
@GenerateSubclass
@Mutable(ReadOnlyVolatileSimpleSetProperty.class)
public abstract class WritableVolatileSimpleSetProperty<V> extends WritableVolatileSetProperty<V, ReadOnlySet<@Nonnull @Valid V>, FreezableSet<@Nonnull @Valid V>> implements ReadOnlyVolatileSimpleSetProperty<V> {
    
    @Pure
    @Override
    @Default("net.digitalid.utility.collections.set.FreezableLinkedHashSetBuilder.build()")
    protected abstract @Nonnull @NonFrozen FreezableSet<@Nonnull @Valid V> getSet();
    
}
