package net.digitalid.utility.functional.iterators;

import net.digitalid.utility.functional.interfaces.Producer;
import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This class implements a generating iterator that generates an infinite number of elements with the given producer.
 */
public class GeneratingIterator<E> implements InfiniteIterator<E> {
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    protected final Producer<? extends E> producer;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected GeneratingIterator(Producer<? extends E> producer) {
        this.producer = producer;
    }
    
    /**
     * Returns a new generating iterator that generates an infinite number of elements with the given producer.
     */
    @Pure
    public static <E> GeneratingIterator<E> with(Producer<? extends E> producer) {
        return new GeneratingIterator<>(producer);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    @Override
    public E next() {
        return producer.produce();
    }
    
}
