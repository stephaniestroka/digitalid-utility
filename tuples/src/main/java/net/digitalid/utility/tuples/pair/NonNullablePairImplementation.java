package net.digitalid.utility.tuples.pair;

import javax.annotation.Nonnull;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class implements a pair with non-nullable elements.
 */
@Immutable
@SuppressWarnings("null")
public class NonNullablePairImplementation<E0, E1> extends NullablePairImplementation<E0, E1> implements NonNullablePair<E0, E1> {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected NonNullablePairImplementation(@Nonnull E0 element0, @Nonnull E1 element1) {
        super(element0, element1);
        
        Require.that(element0 != null).orThrow("The first element may not be null.");
        Require.that(element1 != null).orThrow("The second element may not be null.");
    }
    
    /* -------------------------------------------------- Element 0 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull E0 get0() {
        return element0;
    }
    
    @Pure
    @Override
    public @Nonnull NonNullablePair<E0, E1> setNonNullable0(@Nonnull E0 element0) {
        return new NonNullablePairImplementation<>(element0, element1);
    }
    
    /* -------------------------------------------------- Element 1 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull E1 get1() {
        return element1;
    }
    
    @Pure
    @Override
    public @Nonnull NonNullablePair<E0, E1> setNonNullable1(@Nonnull E1 element1) {
        return new NonNullablePairImplementation<>(element0, element1);
    }
    
}
