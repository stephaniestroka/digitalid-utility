package net.digitalid.utility.tuples;

import javax.annotation.Nonnull;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class models a quartet with non-nullable elements.
 */
@Immutable
@SuppressWarnings("null")
public class NonNullableQuartet<E0, E1, E2, E3> extends NullableQuartet<E0, E1, E2, E3> {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected NonNullableQuartet(@Nonnull E0 element0, @Nonnull E1 element1, @Nonnull E2 element2, @Nonnull E3 element3) {
        super(element0, element1, element2, element3);
        
        Require.that(element0 != null).orThrow("The first element may not be null.");
        Require.that(element1 != null).orThrow("The second element may not be null.");
        Require.that(element2 != null).orThrow("The third element may not be null.");
        Require.that(element3 != null).orThrow("The fourth element may not be null.");
    }
    
    /**
     * Returns a quartet with the given non-nullable elements.
     */
    @Pure
    public static @Nonnull <E0, E1, E2, E3> NonNullableQuartet<E0, E1, E2, E3> with(@Nonnull E0 element0, @Nonnull E1 element1, @Nonnull E2 element2, @Nonnull E3 element3) {
        return new NonNullableQuartet<>(element0, element1, element2, element3);
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
    public @Nonnull NonNullableQuartet<E0, E1, E2, E3> setNonNullable0(@Nonnull E0 element0) {
        return with(element0, element1, element2, element3);
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
    public @Nonnull NonNullableQuartet<E0, E1, E2, E3> setNonNullable1(@Nonnull E1 element1) {
        return with(element0, element1, element2, element3);
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
    public @Nonnull NonNullableQuartet<E0, E1, E2, E3> setNonNullable2(@Nonnull E2 element2) {
        return with(element0, element1, element2, element3);
    }
    
    /* -------------------------------------------------- Element 3 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull E3 get3() {
        return element3;
    }
    
    /**
     * Returns a new tuple with the fourth element set to the given object.
     */
    @Pure
    public @Nonnull NonNullableQuartet<E0, E1, E2, E3> setNonNullable3(@Nonnull E3 element3) {
        return with(element0, element1, element2, element3);
    }
    
}
