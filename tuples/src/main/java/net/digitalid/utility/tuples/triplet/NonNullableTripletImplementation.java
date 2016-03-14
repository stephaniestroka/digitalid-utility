package net.digitalid.utility.tuples.triplet;

import javax.annotation.Nonnull;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class implements a triplet with non-nullable elements.
 */
@Immutable
@SuppressWarnings("null")
public class NonNullableTripletImplementation<E0, E1, E2> extends NullableTripletImplementation<E0, E1, E2> implements NonNullableTriplet<E0, E1, E2> {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected NonNullableTripletImplementation(@Nonnull E0 element0, @Nonnull E1 element1, @Nonnull E2 element2) {
        super(element0, element1, element2);
        
        Require.that(element0 != null).orThrow("The first element may not be null.");
        Require.that(element1 != null).orThrow("The second element may not be null.");
        Require.that(element2 != null).orThrow("The third element may not be null.");
    }
    
    /* -------------------------------------------------- Element 0 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull E0 get0() {
        return element0;
    }
    
    @Pure
    @Override
    public @Nonnull NonNullableTriplet<E0, E1, E2> setNonNullable0(@Nonnull E0 element0) {
        return new NonNullableTripletImplementation<>(element0, element1, element2);
    }
    
    /* -------------------------------------------------- Element 1 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull E1 get1() {
        return element1;
    }
    
    @Pure
    @Override
    public @Nonnull NonNullableTriplet<E0, E1, E2> setNonNullable1(@Nonnull E1 element1) {
        return new NonNullableTripletImplementation<>(element0, element1, element2);
    }
    
    /* -------------------------------------------------- Element 2 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull E2 get2() {
        return element2;
    }
    
    @Pure
    @Override
    public @Nonnull NonNullableTriplet<E0, E1, E2> setNonNullable2(@Nonnull E2 element2) {
        return new NonNullableTripletImplementation<>(element0, element1, element2);
    }
    
}
