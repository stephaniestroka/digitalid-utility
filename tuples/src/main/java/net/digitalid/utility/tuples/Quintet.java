package net.digitalid.utility.tuples;

import java.util.Objects;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.circumfixes.Quotes;
import net.digitalid.utility.validation.annotations.index.Index;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class implements an immutable quintet.
 * 
 * @see Sextet
 */
@Immutable
@SuppressWarnings("EqualsAndHashcode")
public class Quintet<E0, E1, E2, E3, E4> extends Quartet<E0, E1, E2, E3> {
    
    /* -------------------------------------------------- Element 0 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull Quintet<E0, E1, E2, E3, E4> set0(@Captured E0 element0) {
        return new Quintet<>(element0, element1, element2, element3, element4);
    }
    
    /* -------------------------------------------------- Element 1 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull Quintet<E0, E1, E2, E3, E4> set1(@Captured E1 element1) {
        return new Quintet<>(element0, element1, element2, element3, element4);
    }
    
    /* -------------------------------------------------- Element 2 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull Quintet<E0, E1, E2, E3, E4> set2(@Captured E2 element2) {
        return new Quintet<>(element0, element1, element2, element3, element4);
    }
    
    /* -------------------------------------------------- Element 3 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull Quintet<E0, E1, E2, E3, E4> set3(@Captured E3 element3) {
        return new Quintet<>(element0, element1, element2, element3, element4);
    }
    
    /* -------------------------------------------------- Element 4 -------------------------------------------------- */
    
    protected final E4 element4;
    
    /**
     * Returns the fifth element of this tuple.
     */
    @Pure
    public @NonCapturable E4 get4() {
        return element4;
    }
    
    /**
     * Returns a new tuple with the fifth element set to the given object.
     */
    @Pure
    public @Nonnull Quintet<E0, E1, E2, E3, E4> set4(@Captured E4 element4) {
        return new Quintet<>(element0, element1, element2, element3, element4);
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected Quintet(@Captured E0 element0, @Captured E1 element1, @Captured E2 element2, @Captured E3 element3, @Captured E4 element4) {
        super(element0, element1, element2, element3);
        
        this.element4 = element4;
    }
    
    /**
     * Returns a new quintet with the given elements.
     */
    @Pure
    public static <E0, E1, E2, E3, E4> @Nonnull Quintet<E0, E1, E2, E3, E4> of(@Captured E0 element0, @Captured E1 element1, @Captured E2 element2, @Captured E3 element3, @Captured E4 element4) {
        return new Quintet<>(element0, element1, element2, element3, element4);
    }
    
    /* -------------------------------------------------- Tuple -------------------------------------------------- */
    
    @Pure
    @Override
    public @NonNegative int size() {
        return 5;
    }
    
    @Pure
    @Override
    public @NonCapturable Object get(@Index int index) {
        if (index == 4) { return element4; }
        else { return super.get(index); }
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    protected boolean elementEquals(@Nonnull Pair<?, ?> tuple) {
        return super.elementEquals(tuple) && Objects.equals(this.element4, ((Quintet) tuple).element4);
    }
    
    @Pure
    @Override
    public int hashCode() {
        return 83 * super.hashCode() + Objects.hashCode(element4);
    }
    
    @Pure
    @Override
    public @Nonnull String toStringWithoutParentheses() {
        return super.toStringWithoutParentheses() + ", " + Quotes.inCode(element4);
    }
    
}
