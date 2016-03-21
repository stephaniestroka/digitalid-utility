package net.digitalid.utility.tuples;

import java.util.Objects;

/**
 * This class implements an immutable septet.
 * 
 * @see Octet
 */
@SuppressWarnings("EqualsAndHashcode")
public class Septet<E0, E1, E2, E3, E4, E5, E6> extends Sextet<E0, E1, E2, E3, E4, E5> {
    
    /* -------------------------------------------------- Element 0 -------------------------------------------------- */
    
    @Override
    public Septet<E0, E1, E2, E3, E4, E5, E6> set0(E0 element0) {
        return new Septet<>(element0, element1, element2, element3, element4, element5, element6);
    }
    
    /* -------------------------------------------------- Element 1 -------------------------------------------------- */
    
    @Override
    public Septet<E0, E1, E2, E3, E4, E5, E6> set1(E1 element1) {
        return new Septet<>(element0, element1, element2, element3, element4, element5, element6);
    }
    
    /* -------------------------------------------------- Element 2 -------------------------------------------------- */
    
    @Override
    public Septet<E0, E1, E2, E3, E4, E5, E6> set2(E2 element2) {
        return new Septet<>(element0, element1, element2, element3, element4, element5, element6);
    }
    
    /* -------------------------------------------------- Element 3 -------------------------------------------------- */
    
    @Override
    public Septet<E0, E1, E2, E3, E4, E5, E6> set3(E3 element3) {
        return new Septet<>(element0, element1, element2, element3, element4, element5, element6);
    }
    
    /* -------------------------------------------------- Element 4 -------------------------------------------------- */
    
    @Override
    public Septet<E0, E1, E2, E3, E4, E5, E6> set4(E4 element4) {
        return new Septet<>(element0, element1, element2, element3, element4, element5, element6);
    }
    
    /* -------------------------------------------------- Element 5 -------------------------------------------------- */
    
    @Override
    public Septet<E0, E1, E2, E3, E4, E5, E6> set5(E5 element5) {
        return new Septet<>(element0, element1, element2, element3, element4, element5, element6);
    }
    
    /* -------------------------------------------------- Element 6 -------------------------------------------------- */
    
    protected final E6 element6;
    
    /**
     * Returns the seventh element of this tuple.
     */
    public E6 get6() {
        return element6;
    }
    
    /**
     * Returns a new tuple with the seventh element set to the given object.
     */
    public Septet<E0, E1, E2, E3, E4, E5, E6> set6(E6 element6) {
        return new Septet<>(element0, element1, element2, element3, element4, element5, element6);
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected Septet(E0 element0, E1 element1, E2 element2, E3 element3, E4 element4, E5 element5, E6 element6) {
        super(element0, element1, element2, element3, element4, element5);
        
        this.element6 = element6;
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Override
    protected boolean elementEquals(Pair<?, ?> tuple) {
        return super.elementEquals(tuple) && Objects.equals(this.element6, ((Septet) tuple).element6);
    }
    
    @Override
    public int hashCode() {
        return 83 * super.hashCode() + Objects.hashCode(element6);
    }
    
    @Override
    public String toStringWithoutParentheses() {
        return super.toStringWithoutParentheses() + ", " + element6;
    }
    
}
