package net.digitalid.utility.functional.iterable.filter.predicate.implementation;

import javax.annotation.Nullable;

import net.digitalid.utility.functional.iterable.filter.predicate.NullablePredicate;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * This predicate is used in combination with a filter that intends to filter out all non-nullable objects.
 */
@Stateless
public class FilterNonNullPredicate<T, A> extends NullablePredicate<T, A> {
    
    /**
     * Returns false if an object is null.
     */
    @Pure
    @Override
    public boolean apply(@Nullable T object, @Nullable A additionalInformation) {
        return object != null;
    }
    
}
