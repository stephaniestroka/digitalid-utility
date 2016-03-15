package net.digitalid.utility.functional.iterable.zip;

/**
 * Defines whether calling zip on two or more iterables should return an iterable of
 * tuples containing the elements until the shortest iterable reached its end, or until the
 * longest iterable reached its end (in which case, the shorter iterables will continue to return <i>null</i> values on the call to <i>next()</i>).
 */
public enum ZipStrategy {
    
    /**
     * Indicates, that the zip iterator should return tuples on the call to <i>next()</i> until the longest zipped iterator reached its end. 
     * In this case, shorter iterators add <i>null</i> to the returned tuple.
     */
    LONGEST_SEQUENCE,
    
    /**
     * Indicates, that the zip iterator should return tuples on the call to <i>next()</i> until the shortest zipped iterator reached its end. 
     * In this case, elements of longer iterators are discarded.
     */
    SHORTEST_SEQUENCE
    
}
