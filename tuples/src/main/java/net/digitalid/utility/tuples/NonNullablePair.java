package net.digitalid.utility.tuples;

import javax.annotation.Nonnull;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;


/**
 * This class models a pair with non-nullable elements.
 * 
 * @see NonNullableTriplet
 */
@Immutable
@SuppressWarnings("null")
public class NonNullablePair<E0, E1> extends NullablePair<E0, E1> {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected NonNullablePair(@Nonnull E0 element0, @Nonnull E1 element1) {
        super(element0, element1);
        
        Require.that(element0 != null).orThrow("The first element may not be null.");
        Require.that(element1 != null).orThrow("The second element may not be null.");
    }
    
    /**
     * Returns a pair with the given non-nullable elements.
     */
    @Pure
    public static @Nonnull <E0, E1> NonNullablePair<E0, E1> with(@Nonnull E0 element0, @Nonnull E1 element1) {
        return new NonNullablePair<>(element0, element1);
    }
    
    /* -------------------------------------------------- Element 0 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull E0 get0() {
        return element0;
    }
    
    /**
     * Returns a new tuple with the first element set to the given object.
     */
    @Pure
    public @Nonnull NonNullablePair<E0, E1> setNonNullable0(@Nonnull E0 element0) {
        return with(element0, element1);
    }
    
    /* -------------------------------------------------- Element 1 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull E1 get1() {
        return element1;
    }
    
    /**
     * Returns a new tuple with the second element set to the given object.
     */
    @Pure
    public @Nonnull NonNullablePair<E0, E1> setNonNullable1(@Nonnull E1 element1) {
        return with(element0, element1);
    }
    
}
