package net.digitalid.utility.tuples.pair;

import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.rootclass.RootClass;
import net.digitalid.utility.tuples.triplet.NullableTripletImplementation;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class implements a pair with nullable elements.
 * 
 * @see NonNullablePairImplementation
 * @see NullableTripletImplementation
 */
@Immutable
public class NullablePairImplementation<E0, E1> extends RootClass implements NullablePair<E0, E1> {
    
    /* -------------------------------------------------- Element 0 -------------------------------------------------- */
    
    protected final @Nullable E0 element0;
    
    @Pure
    @Override
    public @Nullable E0 get0() {
        return element0;
    }
    
    @Pure
    @Override
    public @Nonnull NullablePair<E0, E1> setNullable0(@Nullable E0 element0) {
        return new NullablePairImplementation<>(element0, element1);
    }
    
    /* -------------------------------------------------- Element 1 -------------------------------------------------- */
    
    protected final @Nullable E1 element1;
    
    @Pure
    @Override
    public @Nullable E1 get1() {
        return element1;
    }
    
    @Pure
    @Override
    public @Nonnull NullablePair<E0, E1> setNullable1(@Nullable E1 element1) {
        return new NullablePairImplementation<>(element0, element1);
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected NullablePairImplementation(@Nullable E0 element0, @Nullable E1 element1) {
        this.element0 = element0;
        this.element1 = element1;
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    /**
     * Returns whether the elements of the given tuple and this tuple are equal.
     * 
     * @require getClass().isInstance(tuple) : "The object has to be assignable to the class of this object.";
     */
    @Pure
    protected boolean elementEquals(@Nonnull NullablePairImplementation<?, ?> tuple) {
        Require.that(getClass().isInstance(tuple)).orThrow("The tuple has to be assignable to the class of this object.");
        
        return Objects.equals(this.element0, tuple.element0) && Objects.equals(this.element1, tuple.element1);
    }
    
    @Pure
    @Override
    public boolean equals(@Nullable Object object) {
        if (object == this) { return true; }
        if (object == null) { return false; }
        
        if (getClass().isInstance(object)) { return elementEquals((NullablePairImplementation<?, ?>) object); }
        else if (object instanceof NullablePairImplementation) { return ((NullablePairImplementation<?, ?>) object).elementEquals(this); }
        else { return false; }
    }
    
    @Pure
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(element0);
        hash = 83 * hash + Objects.hashCode(element1);
        return hash;
    }
    
    @Pure
    @Override
    public @Nonnull String toStringWithoutParentheses() {
        return element0 + ", " + element1;
    }
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return "(" + toStringWithoutParentheses() + ")";
    }
    
}
