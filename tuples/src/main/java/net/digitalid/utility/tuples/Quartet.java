package net.digitalid.utility.tuples;

import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.reference.Capturable;

/**
 * This class models a quartet.
 */
public class Quartet<E0, E1, E2, E3> extends Triplet<E0, E1, E2> {
    
    /* -------------------------------------------------- Field -------------------------------------------------- */
    
    /**
     * Stores the fourth element of this tuple.
     */
    private @Nullable E3 element3;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates a new quartet with the given elements.
     * 
     * @param element0 the first element of this tuple.
     * @param element1 the second element of this tuple.
     * @param element2 the third element of this tuple.
     * @param element3 the fourth element of this tuple.
     */
    protected Quartet(@Nullable E0 element0, @Nullable E1 element1, @Nullable E2 element2, @Nullable E3 element3) {
        super(element0, element1, element2);
        
        this.element3 = element3;
    }
    
    /**
     * Creates a new quartet with the given elements.
     * 
     * @param element0 the first element of this tuple.
     * @param element1 the second element of this tuple.
     * @param element2 the third element of this tuple.
     * @param element3 the fourth element of this tuple.
     * 
     * @return a new quartet with the given elements.
     */
    @Pure
    public static @Capturable @Nonnull <E0, E1, E2, E3> Quartet<E0, E1, E2, E3> get(@Nullable E0 element0, @Nullable E1 element1, @Nullable E2 element2, @Nullable E3 element3) {
        return new Quartet<>(element0, element1, element2, element3);
    }
    
    /**
     * Creates a new quartet from the given quartet.
     * 
     * @param quartet the quartet containing the elements.
     * 
     * @return a new quartet from the given quartet.
     */
    @Pure
    public static @Capturable @Nonnull <E0, E1, E2, E3> Quartet<E0, E1, E2, E3> copy(@Nonnull @NullableElements Quartet<E0, E1, E2, E3> quartet) {
        return get(quartet.getNullableElement0(), quartet.getNullableElement1(), quartet.getNullableElement2(), quartet.getNullableElement3());
    }
    
    /* -------------------------------------------------- Getter -------------------------------------------------- */
    
    @Pure
    public final @Nullable E3 getNullableElement3() {
        return element3;
    }
    
    @Pure
    public final @Nonnull E3 getNonNullableElement3() {
        Require.that(element3 != null).orThrow("The element is not null.");
        
        return element3;
    }
    
    /* -------------------------------------------------- Cloneable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull Quartet<E0, E1, E2, E3> clone() {
        return Quartet.copy(this);
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    @SuppressWarnings("rawtypes")
    public boolean equals(@Nullable Object object) {
        if (object == this) { return true; }
        if (object == null) { return false; }
        if (!(object instanceof Quartet)) { return object.equals(this); }
        final @Nonnull Quartet other = (Quartet) object;
        return super.equals(object) && Objects.equals(this.element3, other.element3);
    }
    
    @Pure
    @Override
    public int hashCode() {
        return 83 * super.hashCode() + Objects.hashCode(element3);
    }
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return super.toString() + ", " + element3;
    }
    
}
