package net.digitalid.utility.functional.iterators;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.functional.exceptions.FailedIterationException;
import net.digitalid.utility.functional.failable.FailableUnaryFunction;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements a mapping iterator that iterates over the elements of the given iterator mapped by the given function.
 */
@Mutable
public class MappingIterator<O, I> extends SingleIteratorBasedIterator<O, I> {
    
    /* -------------------------------------------------- Predicate -------------------------------------------------- */
    
    protected final @Nonnull FailableUnaryFunction<? super I, ? extends O, ?> function;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected MappingIterator(@Captured @Nonnull Iterator<I> primaryIterator, @Nonnull FailableUnaryFunction<? super I, ? extends O, ?> function) {
        super(primaryIterator);
        
        this.function = function;
    }
    
    /**
     * Returns a new mapping iterator that iterates over the elements of the given iterator mapped by the given function.
     */
    @Pure
    public static <O, I> @Capturable @Nonnull MappingIterator<O, I> with(@Captured @Nonnull Iterator<I> iterator, @Nonnull FailableUnaryFunction<? super I, ? extends O, ?> function) {
        return new MappingIterator<>(iterator, function);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean hasNext() {
        return primaryIterator.hasNext();
    }
    
    @Impure
    @Override
    public O next() {
        try {
            return function.evaluate(primaryIterator.next());
        } catch (@Nonnull Exception exception) {
            throw FailedIterationException.with(exception);
        }
    }
    
}
