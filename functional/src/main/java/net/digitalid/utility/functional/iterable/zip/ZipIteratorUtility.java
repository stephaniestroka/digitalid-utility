package net.digitalid.utility.functional.iterable.zip;

import java.util.Iterator;

import javax.annotation.Nonnull;

/**
 * Provides utility functions which can be used by the zip iterators.
 */
public class ZipIteratorUtility {
    
    /**
     * Returns the next element of the iterator, or null, if there is no next element.
     */
    public static <T> T getNextOrNull(@Nonnull Iterator<T> iterator) {
        return iterator.hasNext() ? iterator.next() : null;
    }
    
}
