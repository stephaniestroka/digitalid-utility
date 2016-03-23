package net.digitalid.utility.functional.iterables;

import java.util.function.Supplier;

import net.digitalid.utility.functional.interfaces.UnaryOperator;
import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This interface extends the functional iterable interface to model infinite iterables.
 */
public interface InfiniteIterable<T> extends FunctionalIterable<T> {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    @Pure
    public static <T> InfiniteIterable<T> repeat(T object) {
        return () -> new ObjectIterator(object);
    }
    
    @Pure
    public static <T> InfiniteIterable<T> generate(Supplier<T> supplier) {
        return () -> new SupplierIterator(supplier);
    }
    
    @Pure
    public static <T> InfiniteIterable<T> iterate(UnaryOperator<T> operator, T seed) {
        return () -> new OperatorIterator(operator, seed);
    }
    
    /* -------------------------------------------------- Size -------------------------------------------------- */
    
    @Pure
    @Override
    public default boolean isEmpty() {
        return false;
    }
    
    @Pure
    @Override
    public default int size(int limit) {
        if (limit < 0) { throw new IndexOutOfBoundsException("The limit has to be non-negative but was " + limit + "."); }
        
        return limit;
    }
    
}
