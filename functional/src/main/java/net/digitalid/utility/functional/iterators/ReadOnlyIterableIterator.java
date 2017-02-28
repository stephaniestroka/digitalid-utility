package net.digitalid.utility.functional.iterators;

import java.util.Iterator;
import java.util.Objects;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements a read-only iterable iterator.
 */
@Mutable
public class ReadOnlyIterableIterator<@Specifiable ELEMENT> extends ReadOnlyIterator<ELEMENT> {
    
    /* -------------------------------------------------- Iterator -------------------------------------------------- */
    
    protected final @Nonnull Iterator<? extends ELEMENT> iterator;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ReadOnlyIterableIterator(@Captured @Nonnull Iterator<? extends ELEMENT> iterator) {
        this.iterator = Objects.requireNonNull(iterator);
    }
    
    /**
     * Returns a read-only iterable iterator that captures the given iterator.
     */
    @Pure
    public static @Capturable <@Specifiable ELEMENT> @Nonnull ReadOnlyIterableIterator<ELEMENT> with(@Captured @Nonnull Iterator<? extends ELEMENT> iterator) {
        return new ReadOnlyIterableIterator<>(iterator);
    }
    
    /* -------------------------------------------------- Delegated Operations -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }
    
    @Impure
    @Override
    public ELEMENT next() {
        return iterator.next();
    }
    
}
