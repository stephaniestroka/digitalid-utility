package net.digitalid.utility.collections.readonly;

import java.util.Iterator;
import javax.annotation.Nonnull;
import net.digitalid.utility.annotations.reference.Capturable;
import net.digitalid.utility.annotations.state.Immutable;
import net.digitalid.utility.annotations.state.Pure;
import net.digitalid.utility.collections.annotations.freezable.NonFrozen;
import net.digitalid.utility.collections.freezable.Freezable;
import net.digitalid.utility.collections.freezable.FreezableIterator;

/**
 * This interface provides read-only access to {@link Iterator iterators} and should not be lost by assigning its objects to a supertype.
 * Never call {@link Iterator#remove()} on a read-only iterator! Unfortunately, this method cannot be undeclared again.
 * (Please note that only the underlying iterable and not the iterator itself is read-only (and possibly frozen).)
 * <p>
 * <em>Important:</em> Only use freezable or immutable types for the elements!
 * (The type is not restricted to {@link Freezable} or {@link Immutable} so that library types can also be used.)
 * 
 * @see FreezableIterator
 * 
 * @author Kaspar Etter (kaspar.etter@digitalid.net)
 * @version 1.0.0
 */
public interface ReadOnlyIterator<E> extends Iterator<E>, ReadOnly {
    
    /* –––––––––––––––––––––––––––––––––––––––––––––––––– Cloneable –––––––––––––––––––––––––––––––––––––––––––––––––– */
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableIterator<E> clone();
    
}
