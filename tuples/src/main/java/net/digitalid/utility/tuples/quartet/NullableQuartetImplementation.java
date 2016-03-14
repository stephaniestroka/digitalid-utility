package net.digitalid.utility.tuples.quartet;

import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.tuples.pair.NullablePairImplementation;
import net.digitalid.utility.tuples.triplet.NullableTripletImplementation;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class implements a quartet with nullable elements.
 * 
 * @see NonNullableQuartetImplementation
 */
@Immutable
@SuppressWarnings("EqualsAndHashcode")
public class NullableQuartetImplementation<E0, E1, E2, E3> extends NullableTripletImplementation<E0, E1, E2> implements NullableQuartet<E0, E1, E2, E3> {
    
    /* -------------------------------------------------- Element 0 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull NullableQuartet<E0, E1, E2, E3> setNullable0(@Nullable E0 element0) {
        return new NullableQuartetImplementation<>(element0, element1, element2, element3);
    }
    
    /* -------------------------------------------------- Element 1 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull NullableQuartet<E0, E1, E2, E3> setNullable1(@Nullable E1 element1) {
        return new NullableQuartetImplementation<>(element0, element1, element2, element3);
    }
    
    /* -------------------------------------------------- Element 2 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull NullableQuartet<E0, E1, E2, E3> setNullable2(@Nullable E2 element2) {
        return new NullableQuartetImplementation<>(element0, element1, element2, element3);
    }
    
    /* -------------------------------------------------- Element 3 -------------------------------------------------- */
    
    protected final @Nullable E3 element3;
    
    @Pure
    @Override
    public @Nullable E3 get3() {
        return element3;
    }
    
    @Pure
    @Override
    public @Nonnull NullableQuartet<E0, E1, E2, E3> setNullable3(@Nullable E3 element3) {
        return new NullableQuartetImplementation<>(element0, element1, element2, element3);
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected NullableQuartetImplementation(@Nullable E0 element0, @Nullable E1 element1, @Nullable E2 element2, @Nullable E3 element3) {
        super(element0, element1, element2);
        
        this.element3 = element3;
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    protected boolean elementEquals(@Nonnull NullablePairImplementation<?, ?> tuple) {
        return super.elementEquals(tuple) && Objects.equals(this.element3, ((NullableQuartetImplementation) tuple).element3);
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
