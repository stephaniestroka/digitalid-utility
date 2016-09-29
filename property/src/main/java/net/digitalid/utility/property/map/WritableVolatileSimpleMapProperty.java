package net.digitalid.utility.property.map;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.type.ThreadSafe;
import net.digitalid.utility.collections.map.FreezableMap;
import net.digitalid.utility.collections.map.ReadOnlyMap;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.validation.annotations.generation.Default;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * This class simplifies the creation and declaration of {@link WritableVolatileMapProperty}.
 */
@ThreadSafe
@GenerateBuilder
@GenerateSubclass
@Mutable(ReadOnlyVolatileSimpleMapProperty.class)
public abstract class WritableVolatileSimpleMapProperty<K, V> extends WritableVolatileMapProperty<K, V, ReadOnlyMap<@Nonnull @Valid("key") K, @Nonnull @Valid V>, FreezableMap<@Nonnull @Valid("key") K, @Nonnull @Valid V>> implements ReadOnlyVolatileSimpleMapProperty<K, V> {
    
    @Pure
    @Override
    @Default("net.digitalid.utility.collections.map.FreezableLinkedHashMapBuilder.build()")
    protected abstract @Nonnull @NonFrozen FreezableMap<@Nonnull @Valid("key") K, @Nonnull @Valid V> getMap();
    
}
