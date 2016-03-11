package net.digitalid.utility.tuples;

import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.rootclass.RootClass;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class models a pair with nullable elements.
 * 
 * @see NullableTriplet
 */
@Immutable
public class NullablePair<E0, E1> extends RootClass {
    
    /* -------------------------------------------------- Element 0 -------------------------------------------------- */
    
    protected final @Nullable E0 element0;
    
    /**
     * Returns the first element of this tuple.
     */
    @Pure
    public @Nullable E0 get0() {
        return element0;
    }
    
    /**
     * Returns a new tuple with the first element set to the given object.
     */
    @Pure
    public @Nonnull NullablePair<E0, E1> setNullable0(@Nullable E0 element0) {
        return with(element0, element1);
    }
    
    /* -------------------------------------------------- Element 1 -------------------------------------------------- */
    
    protected final @Nullable E1 element1;
    
    /**
     * Returns the second element of this tuple.
     */
    @Pure
    public @Nullable E1 get1() {
        return element1;
    }
    
    /**
     * Returns a new tuple with the second element set to the given object.
     */
    @Pure
    public @Nonnull NullablePair<E0, E1> setNullable1(@Nullable E1 element1) {
        return with(element0, element1);
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected NullablePair(@Nullable E0 element0, @Nullable E1 element1) {
        this.element0 = element0;
        this.element1 = element1;
    }
    
    /**
     * Returns a pair with the given nullable elements.
     */
    @Pure
    public static @Nonnull <E0, E1> NullablePair<E0, E1> with(@Nullable E0 element0, @Nullable E1 element1) {
        return new NullablePair<>(element0, element1);
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    /**
     * Returns whether the elements of the given tuple and this tuple are equal.
     * 
     * @require getClass().isInstance(tuple) : "The object has to be assignable to the class of this object.";
     */
    @Pure
    protected boolean elementEquals(@Nonnull NullablePair<?, ?> tuple) {
        Require.that(getClass().isInstance(tuple)).orThrow("The tuple has to be assignable to the class of this object.");
        
        return Objects.equals(this.element0, tuple.element0) && Objects.equals(this.element1, tuple.element1);
    }
    
    @Pure
    @Override
    public boolean equals(@Nullable Object object) {
        if (object == this) { return true; }
        if (object == null) { return false; }
        
        if (getClass().isInstance(object)) { return elementEquals((NullablePair<?, ?>) object); }
        else if (object instanceof NullablePair) { return ((NullablePair<?, ?>) object).elementEquals(this); }
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
    
    /**
     * Returns this tuple as a string without parentheses.
     */
    @Pure
    public @Nonnull String toStringWithoutParentheses() {
        return element0 + ", " + element1;
    }
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return "(" + toStringWithoutParentheses() + ")";
    }
    
}
