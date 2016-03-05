package net.digitalid.utility.functional.iterable.filter.predicate;

import javax.annotation.Nullable;

/**
 * This predicate is used in combination with a filter that intends to filter out all non-nullable objects.
 */
public class FilterNonNullPredicate<T> extends NullablePredicate<T> {
    
    /**
     * Returns false if an object is null.
     */
    @Override
    public boolean apply(@Nullable T object) {
        return object != null;
    }
    
}
