package net.digitalid.utility.functional.iterators;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.functional.exceptions.IterationExceptionBuilder;
import net.digitalid.utility.functional.failable.FailableUnaryFunction;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class implements a mapping iterator that iterates over the elements of the given iterator mapped by the given function.
 */
@Mutable
public class MappingIterator<@Specifiable OUTPUT, @Specifiable INPUT> extends SingleIteratorBasedIterator<OUTPUT, INPUT> {
    
    /* -------------------------------------------------- Predicate -------------------------------------------------- */
    
    protected final @Nonnull FailableUnaryFunction<? super INPUT, ? extends OUTPUT, ?> function;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected MappingIterator(@Captured @Nonnull Iterator<INPUT> primaryIterator, @Nonnull FailableUnaryFunction<? super INPUT, ? extends OUTPUT, ?> function) {
        super(primaryIterator);
        
        this.function = function;
    }
    
    /**
     * Returns a new mapping iterator that iterates over the elements of the given iterator mapped by the given function.
     */
    @Pure
    public static @Capturable <@Specifiable OUTPUT, @Specifiable INPUT> @Nonnull MappingIterator<OUTPUT, INPUT> with(@Captured @Nonnull Iterator<INPUT> iterator, @Nonnull FailableUnaryFunction<? super INPUT, ? extends OUTPUT, ?> function) {
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
    public OUTPUT next() {
        try {
            return function.evaluate(primaryIterator.next());
        } catch (@Nonnull Exception exception) {
            throw IterationExceptionBuilder.withCause(exception).build();
        }
    }
    
}
