package net.digitalid.utility.tuples;

import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class models a triplet with nullable elements.
 * 
 * @see NullableQuartet
 */
@Immutable
@SuppressWarnings("EqualsAndHashcode")
public class NullableTriplet<E0, E1, E2> extends NullablePair<E0, E1> {
    
    /* -------------------------------------------------- Element 0 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull NullableTriplet<E0, E1, E2> setNullable0(@Nullable E0 element0) {
        return with(element0, element1, element2);
    }
    
    /* -------------------------------------------------- Element 1 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull NullableTriplet<E0, E1, E2> setNullable1(@Nullable E1 element1) {
        return with(element0, element1, element2);
    }
    
    /* -------------------------------------------------- Element 2 -------------------------------------------------- */
    
    protected final @Nullable E2 element2;
    
    /**
     * Returns the third element of this tuple.
     */
    @Pure
    public @Nullable E2 get2() {
        return element2;
    }
    
    /**
     * Returns a new tuple with the third element set to the given object.
     */
    @Pure
    public @Nonnull NullableTriplet<E0, E1, E2> setNullable2(@Nullable E2 element2) {
        return with(element0, element1, element2);
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected NullableTriplet(@Nullable E0 element0, @Nullable E1 element1, @Nullable E2 element2) {
        super(element0, element1);
        
        this.element2 = element2;
    }
    
    /**
     * Returns a triplet with the given nullable elements.
     */
    @Pure
    public static @Nonnull <E0, E1, E2> NullableTriplet<E0, E1, E2> with(@Nullable E0 element0, @Nullable E1 element1, @Nullable E2 element2) {
        return new NullableTriplet<>(element0, element1, element2);
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    protected boolean elementEquals(@Nonnull NullablePair<?, ?> tuple) {
        return super.elementEquals(tuple) && Objects.equals(this.element2, ((NullableTriplet) tuple).element2);
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
