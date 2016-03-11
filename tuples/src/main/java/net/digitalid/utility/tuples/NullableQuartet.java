package net.digitalid.utility.tuples;

import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class models a quartet with nullable elements.
 */
@Immutable
@SuppressWarnings("EqualsAndHashcode")
public class NullableQuartet<E0, E1, E2, E3> extends NullableTriplet<E0, E1, E2> {
    
    /* -------------------------------------------------- Element 0 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull NullableQuartet<E0, E1, E2, E3> setNullable0(@Nullable E0 element0) {
        return with(element0, element1, element2, element3);
    }
    
    /* -------------------------------------------------- Element 1 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull NullableQuartet<E0, E1, E2, E3> setNullable1(@Nullable E1 element1) {
        return with(element0, element1, element2, element3);
    }
    
    /* -------------------------------------------------- Element 2 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull NullableQuartet<E0, E1, E2, E3> setNullable2(@Nullable E2 element2) {
        return with(element0, element1, element2, element3);
    }
    
    /* -------------------------------------------------- Element 2 -------------------------------------------------- */
    
    protected final @Nullable E3 element3;
    
    /**
     * Returns the fourth element of this tuple.
     */
    @Pure
    public @Nullable E3 get3() {
        return element3;
    }
    
    /**
     * Returns a new tuple with the fourth element set to the given object.
     */
    @Pure
    public @Nonnull NullableQuartet<E0, E1, E2, E3> setNullable3(@Nullable E3 element3) {
        return with(element0, element1, element2, element3);
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected NullableQuartet(@Nullable E0 element0, @Nullable E1 element1, @Nullable E2 element2, @Nullable E3 element3) {
        super(element0, element1, element2);
        
        this.element3 = element3;
    }
    
    /**
     * Returns a quartet with the given nullable elements.
     */
    @Pure
    public static @Nonnull <E0, E1, E2, E3> NullableQuartet<E0, E1, E2, E3> with(@Nullable E0 element0, @Nullable E1 element1, @Nullable E2 element2, @Nullable E3 element3) {
        return new NullableQuartet<>(element0, element1, element2, element3);
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    protected boolean elementEquals(@Nonnull NullablePair<?, ?> tuple) {
        return super.elementEquals(tuple) && Objects.equals(this.element3, ((NullableQuartet) tuple).element3);
    }
    
    @Pure
    @Override
    public int hashCode() {
        return 83 * super.hashCode() + Objects.hashCode(element3);
    }
    
    @Pure
    @Override
    public @Nonnull String toStringWithoutParentheses() {
        return super.toStringWithoutParentheses() + ", " + element3;
    }
    
}
