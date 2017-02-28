package net.digitalid.utility.functional.iterables;

import java.util.Collection;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Shared;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.functional.iterators.ReadOnlyIterableIterator;
import net.digitalid.utility.functional.iterators.ReadOnlyIterator;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.type.ReadOnly;

/**
 * This class implements the collection iterable interface based on a collection.
 */
@ReadOnly
public class CollectionBasedIterable<@Specifiable ELEMENT> implements CollectionIterable<ELEMENT> {
    
    /* -------------------------------------------------- Collection -------------------------------------------------- */
    
    private final @Shared @Nonnull Collection<? extends ELEMENT> collection;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected CollectionBasedIterable(@Shared @Unmodified @Nonnull Collection<? extends ELEMENT> collection) {
        this.collection = collection;
    }
    
    /* -------------------------------------------------- Iterator -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull ReadOnlyIterator<ELEMENT> iterator() {
        return ReadOnlyIterableIterator.with(collection.iterator());
    }
    
    /* -------------------------------------------------- Size -------------------------------------------------- */
    
    @Pure
    @Override
    public @NonNegative int size() {
        return collection.size();
    }
    
}
