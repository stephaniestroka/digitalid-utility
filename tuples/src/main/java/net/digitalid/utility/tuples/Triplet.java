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
 * This class implements an immutable triplet.
 * 
 * @see Quartet
 */
@Immutable
@SuppressWarnings("EqualsAndHashcode")
public class Triplet<E0, E1, E2> extends Pair<E0, E1> {
    
    /* -------------------------------------------------- Element 0 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull Triplet<E0, E1, E2> set0(@Captured E0 element0) {
        return new Triplet<>(element0, element1, element2);
    }
    
    /* -------------------------------------------------- Element 1 -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull Triplet<E0, E1, E2> set1(@Captured E1 element1) {
        return new Triplet<>(element0, element1, element2);
    }
    
    /* -------------------------------------------------- Element 2 -------------------------------------------------- */
    
    protected final E2 element2;
    
    /**
     * Returns the third element of this tuple.
     */
    @Pure
    public @NonCapturable E2 get2() {
        return element2;
    }
    
    /**
     * Returns a new tuple with the third element set to the given object.
     */
    @Pure
    public @Nonnull Triplet<E0, E1, E2> set2(@Captured E2 element2) {
        return new Triplet<>(element0, element1, element2);
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected Triplet(@Captured E0 element0, @Captured E1 element1, @Captured E2 element2) {
        super(element0, element1);
        
        this.element2 = element2;
    }
    
    /**
     * Returns a new triplet with the given elements.
     */
    @Pure
    public static <E0, E1, E2> @Nonnull Triplet<E0, E1, E2> of(@Captured E0 element0, @Captured E1 element1, @Captured E2 element2) {
        return new Triplet<>(element0, element1, element2);
    }
    
    /* -------------------------------------------------- Tuple -------------------------------------------------- */
    
    @Pure
    @Override
    public @NonNegative int size() {
        return 3;
    }
    
    @Pure
    @Override
    public @NonCapturable Object get(@Index int index) {
        if (index == 2) { return element2; }
        else { return super.get(index); }
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    protected boolean elementEquals(@Nonnull Pair<?, ?> tuple) {
        return super.elementEquals(tuple) && Objects.equals(this.element2, ((Triplet) tuple).element2);
    }
    
    @Pure
    @Override
    public int hashCode() {
        return 83 * super.hashCode() + Objects.hashCode(element2);
    }
    
    @Pure
    @Override
    public @Nonnull String toStringWithoutParentheses() {
        return super.toStringWithoutParentheses() + ", " + Quotes.inCode(element2);
    }
    
}
