package net.digitalid.utility.immutable.entry;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Referenced;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.functional.iterables.CollectionIterable;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.type.ReadOnly;

/**
 * This class implements a read-only entry set that returns an iterator which returns read-only entries.
 */
@ReadOnly
public class ReadOnlyEntrySet<K, V> implements Set<Map.@Nonnull Entry<K, V>>, CollectionIterable<Map.@Nonnull Entry<K, V>> {
    
    /* -------------------------------------------------- Fields -------------------------------------------------- */
    
    private final @Referenced @Nonnull Set<Map.@Nonnull Entry<K, V>> set;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ReadOnlyEntrySet(@Nonnull Set<Map.@Nonnull Entry<K, V>> set) {
        this.set = set;
    }
    
    /**
     * Returns a new read-only entry set that is backed by the given set.
     */
    @Pure
    public static <K, V> @Nonnull ReadOnlyEntrySet<K, V> with(@Referenced @Nonnull Set<Map.@Nonnull Entry<K, V>> set) {
        return new ReadOnlyEntrySet<>(set);
    }
    
    /* -------------------------------------------------- Modified Operations -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull ReadOnlyEntrySetIterator<K, V> iterator() {
        return new ReadOnlyEntrySetIterator<>(set.iterator());
    }
    
    /* -------------------------------------------------- Delegated Operations -------------------------------------------------- */
    
    @Pure
    @Override
    public @NonNegative int size() {
        return set.size();
    }
    
    @Pure
    @Override
    public boolean isEmpty() {
        return set.isEmpty();
    }
    
    @Pure
    @Override
    public boolean contains(@NonCaptured @Unmodified @Nullable Object object) {
        return set.contains(object);
    }
    
    @Pure
    @Override
    public boolean containsAll(@NonCaptured @Unmodified @Nonnull Collection<?> collection) {
        return set.containsAll(collection);
    }
    
    @Pure
    @Override
    public @Capturable @Nonnull Object[] toArray() {
        return set.toArray();
    }
    
    @Pure
    @Override
    @SuppressWarnings("SuspiciousToArrayCall")
    public <T> @Capturable @Nonnull T[] toArray(@NonCaptured @Modified @Nonnull T[] array) {
        return set.toArray(array);
    }
    
    /* -------------------------------------------------- Unsupported Operations -------------------------------------------------- */
    
    @Pure
    @Override
    public final boolean add(@Captured Map.@Nonnull Entry<K, V> element) {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    public final boolean addAll(@NonCaptured @Unmodified @Nonnull Collection<? extends Map.@Nonnull Entry<K, V>> collection) {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    public final boolean remove(@NonCaptured @Unmodified @Nullable Object object) {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    public final boolean removeAll(@NonCaptured @Unmodified @Nonnull Collection<?> collection) {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    public final boolean retainAll(@NonCaptured @Unmodified @Nonnull Collection<?> collection) {
        throw new UnsupportedOperationException();
    }
    
    @Pure
    @Override
    public final void clear() {
        throw new UnsupportedOperationException();
    }
    
}
