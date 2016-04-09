package net.digitalid.utility.immutable.entry;

import java.util.Iterator;
import java.util.Map;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.type.Mutable;
import net.digitalid.utility.functional.iterators.ReadOnlyIterableIterator;

/**
 * This class implements an iterator that returns only read-only entries.
 */
@Mutable
public class ReadOnlyEntrySetIterator<K, V> extends ReadOnlyIterableIterator<Map.@Nonnull Entry<K, V>> {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ReadOnlyEntrySetIterator(@Captured @Nonnull Iterator<? extends Map.@Nonnull Entry<K, V>> iterator) {
        super(iterator);
    }
    
    /* -------------------------------------------------- Operations -------------------------------------------------- */
    
    @Impure
    @Override
    public @Nonnull ReadOnlyEntry<K, V> next() {
        return new ReadOnlyEntry<>(super.next());
    }
    
}
