package net.digitalid.utility.tuples;

import java.util.Objects;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.index.Index;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class implements an immutable sextet.
 * 
 * @see Septet
 */
@Immutable
@SuppressWarnings("EqualsAndHashcode")
public class Sextet<E0, E1, E2, E3, E4, E5> extends Quintet<E0, E1, E2, E3, E4> {
    
    /* -------------------------------------------------- Element 0 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull Sextet<E0, E1, E2, E3, E4, E5> set0(E0 element0) {
        return new Sextet<>(element0, element1, element2, element3, element4, element5);
    }
    
    /* -------------------------------------------------- Element 1 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull Sextet<E0, E1, E2, E3, E4, E5> set1(E1 element1) {
        return new Sextet<>(element0, element1, element2, element3, element4, element5);
    }
    
    /* -------------------------------------------------- Element 2 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull Sextet<E0, E1, E2, E3, E4, E5> set2(E2 element2) {
        return new Sextet<>(element0, element1, element2, element3, element4, element5);
    }
    
    /* -------------------------------------------------- Element 3 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull Sextet<E0, E1, E2, E3, E4, E5> set3(E3 element3) {
        return new Sextet<>(element0, element1, element2, element3, element4, element5);
    }
    
    /* -------------------------------------------------- Element 4 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull Sextet<E0, E1, E2, E3, E4, E5> set4(E4 element4) {
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
    public @Nonnull Sextet<E0, E1, E2, E3, E4, E5> set5(E5 element5) {
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
    public static <E0, E1, E2, E3, E4, E5> @Nonnull Sextet<E0, E1, E2, E3, E4, E5> of(E0 element0, E1 element1, E2 element2, E3 element3, E4 element4, E5 element5) {
        return new Sextet<>(element0, element1, element2, element3, element4, element5);
    }
    
    /* -------------------------------------------------- Tuple -------------------------------------------------- */
    
    @Pure
    @Override
    public @NonNegative int size() {
        return 6;
    }
    
    @Pure
    @Override
    public Object get(@Index int index) {
        if (index == 5) { return element5; }
        else { return super.get(index); }
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    protected boolean elementEquals(@Nonnull Pair<?, ?> tuple) {
        return super.elementEquals(tuple) && Objects.equals(this.element5, ((Sextet) tuple).element5);
    }
    
    @Pure
    @Override
    public int hashCode() {
        return 83 * super.hashCode() + Objects.hashCode(element5);
    }
    
    @Pure
    @Override
    public @Nonnull String toStringWithoutParentheses() {
        return super.toStringWithoutParentheses() + ", " + element5;
    }
    
}
