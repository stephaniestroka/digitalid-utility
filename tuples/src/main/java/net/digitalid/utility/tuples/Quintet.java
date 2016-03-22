package net.digitalid.utility.tuples;

import java.util.Objects;

import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This class implements an immutable quintet.
 * 
 * @see Sextet
 */
@SuppressWarnings("EqualsAndHashcode")
public class Quintet<E0, E1, E2, E3, E4> extends Quartet<E0, E1, E2, E3> {
    
    /* -------------------------------------------------- Element 0 -------------------------------------------------- */
    
    @Pure
    @Override
    public Quintet<E0, E1, E2, E3, E4> set0(E0 element0) {
        return new Quintet<>(element0, element1, element2, element3, element4);
    }
    
    /* -------------------------------------------------- Element 1 -------------------------------------------------- */
    
    @Pure
    @Override
    public Quintet<E0, E1, E2, E3, E4> set1(E1 element1) {
        return new Quintet<>(element0, element1, element2, element3, element4);
    }
    
    /* -------------------------------------------------- Element 2 -------------------------------------------------- */
    
    @Pure
    @Override
    public Quintet<E0, E1, E2, E3, E4> set2(E2 element2) {
        return new Quintet<>(element0, element1, element2, element3, element4);
    }
    
    /* -------------------------------------------------- Element 3 -------------------------------------------------- */
    
    @Pure
    @Override
    public Quintet<E0, E1, E2, E3, E4> set3(E3 element3) {
        return new Quintet<>(element0, element1, element2, element3, element4);
    }
    
    /* -------------------------------------------------- Element 4 -------------------------------------------------- */
    
    protected final E4 element4;
    
    /**
     * Returns the fifth element of this tuple.
     */
    @Pure
    public E4 get4() {
        return element4;
    }
    
    /**
     * Returns a new tuple with the fifth element set to the given object.
     */
    @Pure
    public Quintet<E0, E1, E2, E3, E4> set4(E4 element4) {
        return new Quintet<>(element0, element1, element2, element3, element4);
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected Quintet(E0 element0, E1 element1, E2 element2, E3 element3, E4 element4) {
        super(element0, element1, element2, element3);
        
        this.element4 = element4;
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    protected boolean elementEquals(Pair<?, ?> tuple) {
        return super.elementEquals(tuple) && Objects.equals(this.element4, ((Quintet) tuple).element4);
    }
    
    @Pure
    @Override
    public int hashCode() {
        return 83 * super.hashCode() + Objects.hashCode(element4);
    }
    
    @Pure
    @Override
    public String toStringWithoutParentheses() {
        return super.toStringWithoutParentheses() + ", " + element4;
    }
    
}
