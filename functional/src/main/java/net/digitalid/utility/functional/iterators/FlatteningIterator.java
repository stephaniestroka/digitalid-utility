package net.digitalid.utility.functional.iterators;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.annotations.type.Mutable;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.validation.annotations.math.NonNegative;

/**
 * This class implements a flattening iterator that iterates over the elements of the given iterator with all collections up to the given level flattened.
 */
@Mutable
public class FlatteningIterator<F, E> extends SingleIteratorBasedIterator<F, E> {
    
    /* -------------------------------------------------- Level -------------------------------------------------- */
    
    protected final @NonNegative long level;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected FlatteningIterator(@Captured @Nonnull Iterator<? extends E> primaryIterator, @NonNegative long level) {
        super(primaryIterator);
        
        this.level = level;
    }
    
    /**
     * Returns a new flattening iterator that iterates over the elements of the given iterator with all collections up to the given level flattened.
     */
    @Pure
    public static <F, E> @Capturable @Nonnull FlatteningIterator<F, E> with(@Captured @Nonnull Iterator<? extends E> iterator, @NonNegative long level) {
        return new FlatteningIterator<>(iterator, level);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    private @Nullable E nextElement = null;
    
    private boolean found = false;
    
    private @Nullable Iterator<F> subiterator = null;
    
    @Pure
    @Override
    public boolean hasNext() {
        if (subiterator != null) {
            if (subiterator.hasNext()) {
                return true;
            } else {
                subiterator = null;
            }
        }
        
        assert subiterator == null;
        
        if (found) {
            return true;
        } else {
            while (primaryIterator.hasNext()) {
                final E element = primaryIterator.next();
                if (level > 0) {
                    final FiniteIterable<?> iterable;
                    if (element instanceof Collection<?>) { iterable = FiniteIterable.of((Collection<?>) element); }
                    else if (element instanceof Object[]) { iterable = FiniteIterable.of((Object[]) element); }
                    else { iterable = null; }
                    if (iterable != null) {
                        subiterator = new FlatteningIterator<>(iterable.iterator(), level - 1);
                        if (subiterator.hasNext()) {
                            return true;
                        } else {
                            subiterator = null;
                            continue;
                        }
                    }
                }
                nextElement = element;
                found = true;
                return true;
            }
            return false;
        }
    }
    
    @Impure
    @Override
    @SuppressWarnings("unchecked")
    public @NonCapturable F next() {
        if (hasNext()) {
            if (subiterator != null) {
                return subiterator.next();
            } else {
                found = false;
                return (F) nextElement;
            }
        } else {
            throw new NoSuchElementException();
        }
    }
    
}
