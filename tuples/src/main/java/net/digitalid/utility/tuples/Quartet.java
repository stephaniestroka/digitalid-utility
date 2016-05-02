package net.digitalid.utility.tuples;

import java.util.Objects;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.fixes.Quotes;
import net.digitalid.utility.validation.annotations.index.Index;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class implements an immutable quartet.
 * 
 * @see Quintet
 */
@Immutable
@SuppressWarnings("EqualsAndHashcode")
public class Quartet<E0, E1, E2, E3> extends Triplet<E0, E1, E2> {
    
    /* -------------------------------------------------- Element 0 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull Quartet<E0, E1, E2, E3> set0(@Captured E0 element0) {
        return new Quartet<>(element0, element1, element2, element3);
    }
    
    /* -------------------------------------------------- Element 1 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull Quartet<E0, E1, E2, E3> set1(@Captured E1 element1) {
        return new Quartet<>(element0, element1, element2, element3);
    }
    
    /* -------------------------------------------------- Element 2 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull Quartet<E0, E1, E2, E3> set2(@Captured E2 element2) {
        return new Quartet<>(element0, element1, element2, element3);
    }
    
    /* -------------------------------------------------- Element 3 -------------------------------------------------- */
    
    protected final E3 element3;
    
    /**
     * Returns the fourth element of this tuple.
     */
    @Pure
    public @NonCapturable E3 get3() {
        return element3;
    }
    
    /**
     * Returns a new tuple with the fourth element set to the given object.
     */
    @Pure
    public @Nonnull Quartet<E0, E1, E2, E3> set3(@Captured E3 element3) {
        return new Quartet<>(element0, element1, element2, element3);
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected Quartet(@Captured E0 element0, @Captured E1 element1, @Captured E2 element2, @Captured E3 element3) {
        super(element0, element1, element2);
        
        this.element3 = element3;
    }
    
    /**
     * Returns a new quartet with the given elements.
     */
    @Pure
    public static <E0, E1, E2, E3> @Nonnull Quartet<E0, E1, E2, E3> of(@Captured E0 element0, @Captured E1 element1, @Captured E2 element2, @Captured E3 element3) {
        return new Quartet<>(element0, element1, element2, element3);
    }
    
    /* -------------------------------------------------- Tuple -------------------------------------------------- */
    
    @Pure
    @Override
    public @NonNegative int size() {
        return 4;
    }
    
    @Pure
    @Override
    public @NonCapturable Object get(@Index int index) {
        if (index == 3) { return element3; }
        else { return super.get(index); }
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    protected boolean elementEquals(@Nonnull Pair<?, ?> tuple) {
        return super.elementEquals(tuple) && Objects.equals(this.element3, ((Quartet) tuple).element3);
    }
    
    @Pure
    @Override
    public int hashCode() {
        return 83 * super.hashCode() + Objects.hashCode(element3);
    }
    
    @Pure
    @Override
    public @Nonnull String toStringWithoutParentheses() {
        return super.toStringWithoutParentheses() + ", " + Quotes.inCode(element3);
    }
     
}
