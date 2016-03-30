package net.digitalid.utility.functional.iterators;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.reference.Capturable;
import net.digitalid.utility.annotations.type.Mutable;
import net.digitalid.utility.functional.interfaces.UnaryFunction;

/**
 * This class implements a mapping iterator that iterates over the elements of the given iterator mapped by the given function.
 */
@Mutable
public class MappingIterator<O, I> extends SingleIteratorBasedIterator<O, I> {
    
    /* -------------------------------------------------- Predicate -------------------------------------------------- */
    
    protected final @Nonnull UnaryFunction<? super I, ? extends O> function;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected MappingIterator(@Nonnull Iterator<I> primaryIterator, @Nonnull UnaryFunction<? super I, ? extends O> function) {
        super(primaryIterator);
        
        this.function = function;
    }
    
    /**
     * Returns a new mapping iterator that iterates over the elements of the given iterator mapped by the given function.
     */
    @Pure
    public static <O, I> @Capturable @Nonnull MappingIterator<O, I> with(@Nonnull Iterator<I> iterator, @Nonnull UnaryFunction<? super I, ? extends O> function) {
        return new MappingIterator<>(iterator, function);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean hasNext() {
        return primaryIterator.hasNext();
    }
    
    @Pure
    @Override
    public O next() {
        return function.evaluate(primaryIterator.next());
    }
    
}
