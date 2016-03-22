package net.digitalid.utility.tuples;

import java.util.Objects;

import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This class implements an immutable triplet.
 * 
 * @see Quartet
 */
@SuppressWarnings("EqualsAndHashcode")
public class Triplet<E0, E1, E2> extends Pair<E0, E1> {
    
    /* -------------------------------------------------- Element 0 -------------------------------------------------- */
    
    @Pure
    @Override
    public Triplet<E0, E1, E2> set0(E0 element0) {
        return new Triplet<>(element0, element1, element2);
    }
    
    /* -------------------------------------------------- Element 1 -------------------------------------------------- */
    
    @Pure
    @Override
    public Triplet<E0, E1, E2> set1(E1 element1) {
        return new Triplet<>(element0, element1, element2);
    }
    
    /* -------------------------------------------------- Element 2 -------------------------------------------------- */
    
    protected final E2 element2;
    
    /**
     * Returns the third element of this tuple.
     */
    @Pure
    public E2 get2() {
        return element2;
    }
    
    /**
     * Returns a new tuple with the third element set to the given object.
     */
    @Pure
    public Triplet<E0, E1, E2> set2(E2 element2) {
        return new Triplet<>(element0, element1, element2);
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected Triplet(E0 element0, E1 element1, E2 element2) {
        super(element0, element1);
        
        this.element2 = element2;
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    protected boolean elementEquals(Pair<?, ?> tuple) {
        return super.elementEquals(tuple) && Objects.equals(this.element2, ((Triplet) tuple).element2);
    }
    
    @Pure
    @Override
    public int hashCode() {
        return 83 * super.hashCode() + Objects.hashCode(element2);
    }
    
    @Pure
    @Override
    public String toStringWithoutParentheses() {
        return super.toStringWithoutParentheses() + ", " + element2;
    }
    
}
