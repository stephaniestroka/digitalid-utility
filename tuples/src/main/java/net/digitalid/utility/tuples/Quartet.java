package net.digitalid.utility.tuples;

import java.util.Objects;

/**
 * This class implements an immutable quartet.
 * 
 * @see Quintet
 */
@SuppressWarnings("EqualsAndHashcode")
public class Quartet<E0, E1, E2, E3> extends Triplet<E0, E1, E2> {
    
    /* -------------------------------------------------- Element 0 -------------------------------------------------- */
    
    @Override
    public Quartet<E0, E1, E2, E3> set0(E0 element0) {
        return new Quartet<>(element0, element1, element2, element3);
    }
    
    /* -------------------------------------------------- Element 1 -------------------------------------------------- */
    
    @Override
    public Quartet<E0, E1, E2, E3> set1(E1 element1) {
        return new Quartet<>(element0, element1, element2, element3);
    }
    
    /* -------------------------------------------------- Element 2 -------------------------------------------------- */
    
    @Override
    public Quartet<E0, E1, E2, E3> set2(E2 element2) {
        return new Quartet<>(element0, element1, element2, element3);
    }
    
    /* -------------------------------------------------- Element 3 -------------------------------------------------- */
    
    protected final E3 element3;
    
    /**
     * Returns the fourth element of this tuple.
     */
    public E3 get3() {
        return element3;
    }
    
    /**
     * Returns a new tuple with the fourth element set to the given object.
     */
    public Quartet<E0, E1, E2, E3> set3(E3 element3) {
        return new Quartet<>(element0, element1, element2, element3);
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected Quartet(E0 element0, E1 element1, E2 element2, E3 element3) {
        super(element0, element1, element2);
        
        this.element3 = element3;
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Override
    protected boolean elementEquals(Pair<?, ?> tuple) {
        return super.elementEquals(tuple) && Objects.equals(this.element3, ((Quartet) tuple).element3);
    }
    
    @Override
    public int hashCode() {
        return 83 * super.hashCode() + Objects.hashCode(element3);
    }
    
    @Override
    public String toStringWithoutParentheses() {
        return super.toStringWithoutParentheses() + ", " + element3;
    }
    
}
