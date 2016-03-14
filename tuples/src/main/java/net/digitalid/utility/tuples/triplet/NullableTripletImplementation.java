package net.digitalid.utility.tuples.triplet;

import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.tuples.pair.NullablePairImplementation;
import net.digitalid.utility.tuples.quartet.NullableQuartetImplementation;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class implements a triplet with nullable elements.
 * 
 * @see NonNullableTripletImplementation
 * @see NullableQuartetImplementation
 */
@Immutable
@SuppressWarnings("EqualsAndHashcode")
public class NullableTripletImplementation<E0, E1, E2> extends NullablePairImplementation<E0, E1> implements NullableTriplet<E0, E1, E2> {
    
    /* -------------------------------------------------- Element 0 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull NullableTriplet<E0, E1, E2> setNullable0(@Nullable E0 element0) {
        return new NullableTripletImplementation<>(element0, element1, element2);
    }
    
    /* -------------------------------------------------- Element 1 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull NullableTriplet<E0, E1, E2> setNullable1(@Nullable E1 element1) {
        return new NullableTripletImplementation<>(element0, element1, element2);
    }
    
    /* -------------------------------------------------- Element 2 -------------------------------------------------- */
    
    protected final @Nullable E2 element2;
    
    @Pure
    @Override
    public @Nullable E2 get2() {
        return element2;
    }
    
    @Pure
    @Override
    public @Nonnull NullableTriplet<E0, E1, E2> setNullable2(@Nullable E2 element2) {
        return new NullableTripletImplementation<>(element0, element1, element2);
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected NullableTripletImplementation(@Nullable E0 element0, @Nullable E1 element1, @Nullable E2 element2) {
        super(element0, element1);
        
        this.element2 = element2;
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    protected boolean elementEquals(@Nonnull NullablePairImplementation<?, ?> tuple) {
        return super.elementEquals(tuple) && Objects.equals(this.element2, ((NullableTripletImplementation) tuple).element2);
    }
    
    @Pure
    @Override
    public int hashCode() {
        return 83 * super.hashCode() + Objects.hashCode(element2);
    }
    
    @Pure
    @Override
    public @Nonnull String toStringWithoutParentheses() {
        return super.toStringWithoutParentheses() + ", " + element2;
    }
    
}
