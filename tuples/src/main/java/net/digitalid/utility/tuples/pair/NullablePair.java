package net.digitalid.utility.tuples.pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.rootclass.RootInterface;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This interface models a pair with nullable elements.
 * 
 * @see NullablePairImplementation
 * @see NonNullablePair
 * @see NullableTriplet
 */
@Immutable
public interface NullablePair<E0, E1> extends RootInterface {
    
    /* -------------------------------------------------- Element 0 -------------------------------------------------- */
    
    /**
     * Returns the first element of this tuple.
     */
    @Pure
    public @Nullable E0 get0();
    
    /**
     * Returns a new tuple with the first element set to the given object.
     */
    @Pure
    public @Nonnull NullablePair<E0, E1> setNullable0(@Nullable E0 element0);
    
    /* -------------------------------------------------- Element 1 -------------------------------------------------- */
    
    /**
     * Returns the second element of this tuple.
     */
    @Pure
    public @Nullable E1 get1();
    
    /**
     * Returns a new tuple with the second element set to the given object.
     */
    @Pure
    public @Nonnull NullablePair<E0, E1> setNullable1(@Nullable E1 element1);
    
    /* -------------------------------------------------- String -------------------------------------------------- */
    
    /**
     * Returns this tuple as a string without parentheses.
     */
    @Pure
    public @Nonnull String toStringWithoutParentheses();
    
}
