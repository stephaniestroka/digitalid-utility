package net.digitalid.utility.collections.readonly;

import java.util.ListIterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.collections.freezable.FreezableListIterator;
import net.digitalid.utility.freezable.Freezable;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.annotations.reference.Capturable;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.annotations.method.Pure;

/**
 * This interface provides read-only access to {@link ListIterator list iterators} and should not be lost by assigning its objects to a supertype.
 * Never call a method that modifies the underlying list on a read-only list iterator! Unfortunately, these methods cannot be undeclared again.
 * (Please note that only the underlying list and not the iterator itself is read-only (and possibly frozen).)
 * <p>
 * <em>Important:</em> Only use freezable or immutable types for the elements!
 * (The type is not restricted to {@link Freezable} or {@link Immutable} so that library types can also be used.)
 * 
 * @see FreezableListIterator
 */
public interface ReadOnlyListIterator<E> extends ReadOnlyIterator<E>, ListIterator<E> {
    
    /* -------------------------------------------------- Cloneable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableListIterator<E> clone();
    
}
