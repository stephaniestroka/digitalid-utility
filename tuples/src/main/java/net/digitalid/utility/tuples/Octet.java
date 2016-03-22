package net.digitalid.utility.tuples;

import java.util.Objects;

import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This class implements an immutable octet.
 */
@SuppressWarnings("EqualsAndHashcode")
public class Octet<E0, E1, E2, E3, E4, E5, E6, E7> extends Septet<E0, E1, E2, E3, E4, E5, E6> {
    
    /* -------------------------------------------------- Element 0 -------------------------------------------------- */
    
    @Pure
    @Override
    public Octet<E0, E1, E2, E3, E4, E5, E6, E7> set0(E0 element0) {
        return new Octet<>(element0, element1, element2, element3, element4, element5, element6, element7);
    }
    
    /* -------------------------------------------------- Element 1 -------------------------------------------------- */
    
    @Pure
    @Override
    public Octet<E0, E1, E2, E3, E4, E5, E6, E7> set1(E1 element1) {
        return new Octet<>(element0, element1, element2, element3, element4, element5, element6, element7);
    }
    
    /* -------------------------------------------------- Element 2 -------------------------------------------------- */
    
    @Pure
    @Override
    public Octet<E0, E1, E2, E3, E4, E5, E6, E7> set2(E2 element2) {
        return new Octet<>(element0, element1, element2, element3, element4, element5, element6, element7);
    }
    
    /* -------------------------------------------------- Element 3 -------------------------------------------------- */
    
    @Pure
    @Override
    public Octet<E0, E1, E2, E3, E4, E5, E6, E7> set3(E3 element3) {
        return new Octet<>(element0, element1, element2, element3, element4, element5, element6, element7);
    }
    
    /* -------------------------------------------------- Element 4 -------------------------------------------------- */
    
    @Pure
    @Override
    public Octet<E0, E1, E2, E3, E4, E5, E6, E7> set4(E4 element4) {
        return new Octet<>(element0, element1, element2, element3, element4, element5, element6, element7);
    }
    
    /* -------------------------------------------------- Element 5 -------------------------------------------------- */
    
    @Pure
    @Override
    public Octet<E0, E1, E2, E3, E4, E5, E6, E7> set5(E5 element5) {
        return new Octet<>(element0, element1, element2, element3, element4, element5, element6, element7);
    }
    
    /* -------------------------------------------------- Element 6 -------------------------------------------------- */
    
    @Pure
    @Override
    public Octet<E0, E1, E2, E3, E4, E5, E6, E7> set6(E6 element6) {
        return new Octet<>(element0, element1, element2, element3, element4, element5, element6, element7);
    }
    
    /* -------------------------------------------------- Element 7 -------------------------------------------------- */
    
    protected final E7 element7;
    
    /**
     * Returns the eighth element of this tuple.
     */
    @Pure
    public E7 get7() {
        return element7;
    }
    
    /**
     * Returns a new tuple with the eighth element set to the given object.
     */
    @Pure
    public Octet<E0, E1, E2, E3, E4, E5, E6, E7> set7(E7 element7) {
        return new Octet<>(element0, element1, element2, element3, element4, element5, element6, element7);
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected Octet(E0 element0, E1 element1, E2 element2, E3 element3, E4 element4, E5 element5, E6 element6, E7 element7) {
        super(element0, element1, element2, element3, element4, element5, element6);
        
        this.element7 = element7;
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    protected boolean elementEquals(Pair<?, ?> tuple) {
        return super.elementEquals(tuple) && Objects.equals(this.element7, ((Octet) tuple).element7);
    }
    
    @Pure
    @Override
    public int hashCode() {
        return 83 * super.hashCode() + Objects.hashCode(element7);
    }
    
    @Pure
    @Override
    public String toStringWithoutParentheses() {
        return super.toStringWithoutParentheses() + ", " + element7;
    }
    
}
