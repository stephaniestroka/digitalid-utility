package net.digitalid.utility.tuples.quartet;

import javax.annotation.Nonnull;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class implements a quartet with non-nullable elements.
 */
@Immutable
@SuppressWarnings("null")
public class NonNullableQuartetImplementation<E0, E1, E2, E3> extends NullableQuartetImplementation<E0, E1, E2, E3> implements NonNullableQuartet<E0, E1, E2, E3> {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected NonNullableQuartetImplementation(@Nonnull E0 element0, @Nonnull E1 element1, @Nonnull E2 element2, @Nonnull E3 element3) {
        super(element0, element1, element2, element3);
        
        Require.that(element0 != null).orThrow("The first element may not be null.");
        Require.that(element1 != null).orThrow("The second element may not be null.");
        Require.that(element2 != null).orThrow("The third element may not be null.");
        Require.that(element3 != null).orThrow("The fourth element may not be null.");
    }
    
    /* -------------------------------------------------- Element 0 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull E0 get0() {
        return element0;
    }
    
    @Pure
    @Override
    public @Nonnull NonNullableQuartet<E0, E1, E2, E3> setNonNullable0(@Nonnull E0 element0) {
        return new NonNullableQuartetImplementation<>(element0, element1, element2, element3);
    }
    
    /* -------------------------------------------------- Element 1 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull E1 get1() {
        return element1;
    }
    
    @Pure
    @Override
    public @Nonnull NonNullableQuartet<E0, E1, E2, E3> setNonNullable1(@Nonnull E1 element1) {
        return new NonNullableQuartetImplementation<>(element0, element1, element2, element3);
    }
    
    /* -------------------------------------------------- Element 2 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull E2 get2() {
        return element2;
    }
    
    @Pure
    @Override
    public @Nonnull NonNullableQuartet<E0, E1, E2, E3> setNonNullable2(@Nonnull E2 element2) {
        return new NonNullableQuartetImplementation<>(element0, element1, element2, element3);
    }
    
    /* -------------------------------------------------- Element 3 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull E3 get3() {
        return element3;
    }
    
    @Pure
    @Override
    public @Nonnull NonNullableQuartet<E0, E1, E2, E3> setNonNullable3(@Nonnull E3 element3) {
        return new NonNullableQuartetImplementation<>(element0, element1, element2, element3);
    }
    
}
