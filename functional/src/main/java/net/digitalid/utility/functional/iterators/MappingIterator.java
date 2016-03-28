package net.digitalid.utility.functional.iterators;

import java.util.Iterator;

import net.digitalid.utility.functional.interfaces.UnaryFunction;
import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This class implements a mapping iterator that iterates over the elements of the given iterator mapped by the given function.
 */
public class MappingIterator<O, I> extends SingleIteratorBasedIterator<O, I> {
    
    /* -------------------------------------------------- Predicate -------------------------------------------------- */
    
    protected final UnaryFunction<? super I, ? extends O> function;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected MappingIterator(Iterator<I> primaryIterator, UnaryFunction<? super I, ? extends O> function) {
        super(primaryIterator);
        
        this.function = function;
    }
    
    /**
     * Returns a new mapping iterator that iterates over the elements of the given iterator mapped by the given function.
     */
    @Pure
    public static <O, I> MappingIterator<O, I> with(Iterator<I> iterator, UnaryFunction<? super I, ? extends O> function) {
        return new MappingIterator<>(iterator, function);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    @Override
    public O next() {
        return function.evaluate(primaryIterator.next());
    }
    
}
