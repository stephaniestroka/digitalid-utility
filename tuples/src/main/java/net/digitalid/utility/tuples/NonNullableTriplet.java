package net.digitalid.utility.tuples;

import javax.annotation.Nonnull;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class models a triplet with non-nullable elements.
 * 
 * @see NonNullableQuartet
 */
@Immutable
@SuppressWarnings("null")
public class NonNullableTriplet<E0, E1, E2> extends NullableTriplet<E0, E1, E2> {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected NonNullableTriplet(@Nonnull E0 element0, @Nonnull E1 element1, @Nonnull E2 element2) {
        super(element0, element1, element2);
        
        Require.that(element0 != null).orThrow("The first element may not be null.");
        Require.that(element1 != null).orThrow("The second element may not be null.");
        Require.that(element2 != null).orThrow("The third element may not be null.");
    }
    
    /**
     * Returns a triplet with the given non-nullable elements.
     */
    @Pure
    public static @Nonnull <E0, E1, E2> NonNullableTriplet<E0, E1, E2> with(@Nonnull E0 element0, @Nonnull E1 element1, @Nonnull E2 element2) {
        return new NonNullableTriplet<>(element0, element1, element2);
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
    public @Nonnull NonNullableTriplet<E0, E1, E2> setNonNullable0(@Nonnull E0 element0) {
        return with(element0, element1, element2);
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
    public @Nonnull NonNullableTriplet<E0, E1, E2> setNonNullable1(@Nonnull E1 element1) {
        return with(element0, element1, element2);
    }
    
    /* -------------------------------------------------- Element 2 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull E2 get2() {
        return element2;
    }
    
    /**
     * Returns a new tuple with the third element set to the given object.
     */
    @Pure
    public @Nonnull NonNullableTriplet<E0, E1, E2> setNonNullable2(@Nonnull E2 element2) {
        return with(element0, element1, element2);
    }
    
}
