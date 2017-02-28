package net.digitalid.utility.functional.iterators;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements a combining iterator that iterates first over the elements of the first iterator and then over the elements of the second iterator.
 */
@Mutable
public class CombiningIterator<@Specifiable ELEMENT> extends DoubleIteratorBasedIterator<ELEMENT, ELEMENT, ELEMENT> {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected CombiningIterator(@Captured @Nonnull Iterator<? extends ELEMENT> primaryIterator, @Captured @Nonnull Iterator<? extends ELEMENT> secondaryIterator) {
        super(primaryIterator, secondaryIterator);
    }
    
    /**
     * Returns a new combining iterator that iterates first over the elements of the first iterator and then over the elements of the second iterator.
     */
    @Pure
    public static @Capturable <@Specifiable ELEMENT> @Nonnull CombiningIterator<ELEMENT> with(@Captured @Nonnull Iterator<? extends ELEMENT> primaryIterator, @Captured @Nonnull Iterator<? extends ELEMENT> secondaryIterator) {
        return new CombiningIterator<>(primaryIterator, secondaryIterator);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean hasNext() {
        return primaryIterator.hasNext() || secondaryIterator.hasNext();
    }
    
    @Impure
    @Override
    public @NonCapturable ELEMENT next() {
        if (primaryIterator.hasNext()) { return primaryIterator.next(); }
        else { return secondaryIterator.next(); }
    }
    
}
