package net.digitalid.utility.immutable.entry;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.validation.annotations.type.ReadOnly;

/**
 * This class implements a read-only entry, whose value cannot be set.
 */
@ReadOnly(Map.Entry.class)
public class ReadOnlyEntry<K, V> implements Map.Entry<K, V> {
    
    /* -------------------------------------------------- Fields -------------------------------------------------- */
    
    protected final Map.@Nonnull Entry<K, V> entry;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ReadOnlyEntry(@Captured Map.@Nonnull Entry<K, V> entry) {
        this.entry = entry;
    }
    
    /* -------------------------------------------------- Delegated Operations -------------------------------------------------- */
    
    @Pure
    @Override
    public @NonCapturable K getKey() {
        return entry.getKey();
    }
    
    @Pure
    @Override
    public @NonCapturable V getValue() {
        return entry.getValue();
    }
    
    @Pure
    @Override
    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    public boolean equals(@NonCaptured @Unmodified @Nullable Object object) {
        return entry.equals(object);
    }
    
    @Pure
    @Override
    public int hashCode() {
        return entry.hashCode();
    }
    
    /* -------------------------------------------------- Unsupported Operations -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable V setValue(@NonCaptured @Unmodified V value) {
        throw new UnsupportedOperationException();
    }
    
}
