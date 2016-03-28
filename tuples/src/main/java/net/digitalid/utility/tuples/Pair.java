package net.digitalid.utility.tuples;

import java.util.Objects;

import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This class implements an immutable pair.
 * 
 * @see Triplet
 */
public class Pair<E0, E1> extends Tuple {
    
    /* -------------------------------------------------- Element 0 -------------------------------------------------- */
    
    protected final E0 element0;
    
    /**
     * Returns the first element of this tuple.
     */
    @Pure
    public E0 get0() {
        return element0;
    }
    
    /**
     * Returns a new tuple with the first element set to the given object.
     */
    @Pure
    public Pair<E0, E1> set0(E0 element0) {
        return new Pair<>(element0, element1);
    }
    
    /* -------------------------------------------------- Element 1 -------------------------------------------------- */
    
    protected final E1 element1;
    
    /**
     * Returns the second element of this tuple.
     */
    @Pure
    public E1 get1() {
        return element1;
    }
    
    /**
     * Returns a new tuple with the second element set to the given object.
     */
    @Pure
    public Pair<E0, E1> set1(E1 element1) {
        return new Pair<>(element0, element1);
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected Pair(E0 element0, E1 element1) {
        this.element0 = element0;
        this.element1 = element1;
    }
    
    /**
     * Returns a new pair with the given elements.
     */
    @Pure
    public static <E0, E1> Pair<E0, E1> of(E0 element0, E1 element1) {
        return new Pair<>(element0, element1);
    }
    
    /* -------------------------------------------------- Tuple -------------------------------------------------- */
    
    @Pure
    @Override
    public int size() {
        return 2;
    }
    
    @Pure
    @Override
    public Object get(int index) {
        if (index == 0) { return element0; }
        if (index == 1) { return element1; }
        throw new IndexOutOfBoundsException("The size of the tuple is " + size() + " but the index was " + index + ".");
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    /**
     * Returns whether the elements of the given non-nullable tuple and this tuple are equal.
     * 
     * @require getClass().isInstance(tuple) : "The object has to be assignable to the class of this object.";
     */
    @Pure
    protected boolean elementEquals(Pair<?, ?> tuple) {
        assert getClass().isInstance(tuple) : "The tuple has to be assignable to the class of this object.";
        
        return Objects.equals(this.element0, tuple.element0) && Objects.equals(this.element1, tuple.element1);
    }
    
    @Pure
    @Override
    public boolean equals(Object object) {
        if (object == this) { return true; }
        if (object == null) { return false; }
        
        if (getClass().isInstance(object)) { return elementEquals((Pair<?, ?>) object); }
        else if (object instanceof Pair) { return ((Pair<?, ?>) object).elementEquals(this); }
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
    public String toStringWithoutParentheses() {
        return element0 + ", " + element1;
    }
    
    @Pure
    @Override
    public String toString() {
        return "(" + toStringWithoutParentheses() + ")";
    }
    
}
