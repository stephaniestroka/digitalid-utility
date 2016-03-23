package net.digitalid.utility.tuples;

import java.util.Objects;

import net.digitalid.utility.tuples.annotations.Pure;

/**
 * This class implements an immutable sextet.
 * 
 * @see Septet
 */
@SuppressWarnings("EqualsAndHashcode")
public class Sextet<E0, E1, E2, E3, E4, E5> extends Quintet<E0, E1, E2, E3, E4> {
    
    /* -------------------------------------------------- Element 0 -------------------------------------------------- */
    
    @Pure
    @Override
    public Sextet<E0, E1, E2, E3, E4, E5> set0(E0 element0) {
        return new Sextet<>(element0, element1, element2, element3, element4, element5);
    }
    
    /* -------------------------------------------------- Element 1 -------------------------------------------------- */
    
    @Pure
    @Override
    public Sextet<E0, E1, E2, E3, E4, E5> set1(E1 element1) {
        return new Sextet<>(element0, element1, element2, element3, element4, element5);
    }
    
    /* -------------------------------------------------- Element 2 -------------------------------------------------- */
    
    @Pure
    @Override
    public Sextet<E0, E1, E2, E3, E4, E5> set2(E2 element2) {
        return new Sextet<>(element0, element1, element2, element3, element4, element5);
    }
    
    /* -------------------------------------------------- Element 3 -------------------------------------------------- */
    
    @Pure
    @Override
    public Sextet<E0, E1, E2, E3, E4, E5> set3(E3 element3) {
        return new Sextet<>(element0, element1, element2, element3, element4, element5);
    }
    
    /* -------------------------------------------------- Element 4 -------------------------------------------------- */
    
    @Pure
    @Override
    public Sextet<E0, E1, E2, E3, E4, E5> set4(E4 element4) {
        return new Sextet<>(element0, element1, element2, element3, element4, element5);
    }
    
    /* -------------------------------------------------- Element 5 -------------------------------------------------- */
    
    protected final E5 element5;
    
    /**
     * Returns the sixth element of this tuple.
     */
    @Pure
    public E5 get5() {
        return element5;
    }
    
    /**
     * Returns a new tuple with the sixth element set to the given object.
     */
    @Pure
    public Sextet<E0, E1, E2, E3, E4, E5> set5(E5 element5) {
        return new Sextet<>(element0, element1, element2, element3, element4, element5);
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected Sextet(E0 element0, E1 element1, E2 element2, E3 element3, E4 element4, E5 element5) {
        super(element0, element1, element2, element3, element4);
        
        this.element5 = element5;
    }
    
    /**
     * Returns a new sextet with the given elements.
     */
    @Pure
    public static <E0, E1, E2, E3, E4, E5> Sextet<E0, E1, E2, E3, E4, E5> of(E0 element0, E1 element1, E2 element2, E3 element3, E4 element4, E5 element5) {
        return new Sextet<>(element0, element1, element2, element3, element4, element5);
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    protected boolean elementEquals(Pair<?, ?> tuple) {
        return super.elementEquals(tuple) && Objects.equals(this.element5, ((Sextet) tuple).element5);
    }
    
    @Pure
    @Override
    public int hashCode() {
        return 83 * super.hashCode() + Objects.hashCode(element5);
    }
    
    @Pure
    @Override
    public String toStringWithoutParentheses() {
        return super.toStringWithoutParentheses() + ", " + element5;
    }
    
}
