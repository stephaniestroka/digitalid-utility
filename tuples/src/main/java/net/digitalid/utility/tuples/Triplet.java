package net.digitalid.utility.tuples;

import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.reference.Capturable;

/**
 * This class models a triplet.
 * 
 * @see Quartet
 */
public class Triplet<E0, E1, E2> extends Pair<E0, E1> {
    
    /* -------------------------------------------------- Field -------------------------------------------------- */
    
    /**
     * Stores the third element of this tuple.
     */
    private @Nullable E2 element2;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates a new triplet with the given elements.
     * 
     * @param element0 the first element of this tuple.
     * @param element1 the second element of this tuple.
     * @param element2 the third element of this tuple.
     */
    protected Triplet(@Nullable E0 element0, @Nullable E1 element1, @Nullable E2 element2) {
        super(element0, element1);
        
        this.element2 = element2;
    }
    
    /**
     * Creates a new triplet with the given elements.
     * 
     * @param element0 the first element of this tuple.
     * @param element1 the second element of this tuple.
     * @param element2 the third element of this tuple.
     * 
     * @return a new triplet with the given elements.
     */
    @Pure
    public static @Capturable @Nonnull <E0, E1, E2> Triplet<E0, E1, E2> get(@Nullable E0 element0, @Nullable E1 element1, @Nullable E2 element2) {
        return new Triplet<>(element0, element1, element2);
    }
    
    /**
     * Creates a new triplet from the given triplet.
     * 
     * @param triplet the triplet containing the elements.
     * 
     * @return a new triplet from the given triplet.
     */
    @Pure
    public static @Capturable @Nonnull <E0, E1, E2> Triplet<E0, E1, E2> copy(@Nonnull @NullableElements Triplet<E0, E1, E2> triplet) {
        return get(triplet.getNullableElement0(), triplet.getNullableElement1(), triplet.getNullableElement2());
    }
    
    /**
     * Creates a new triplet from the given triplet.
     * 
     * @param triplet the triplet containing the elements.
     * 
     * @return a new triplet from the given triplet.
     */
    @Pure
    public static @Capturable @Nullable <E0, E1, E2> Triplet<E0, E1, E2> getNullable(@Nullable @NullableElements Triplet<E0, E1, E2> triplet) {
        return triplet == null ? null : copy(triplet);
    }
    
    /* -------------------------------------------------- Getter -------------------------------------------------- */
    
    @Pure
    public final @Nullable E2 getNullableElement2() {
        return element2;
    }
    
    @Pure
    public final @Nonnull E2 getNonNullableElement2() {
        Require.that(element2 != null).orThrow("The element is not null.");
        
        return element2;
    }
    
    /* -------------------------------------------------- Cloneable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull Triplet<E0, E1, E2> clone() {
        return Triplet.copy(this);
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    @SuppressWarnings("rawtypes")
    public boolean equals(@Nullable Object object) {
        if (object == this) { return true; }
        if (object == null) { return false; }
        if (!(object instanceof Triplet)) { return object.equals(this); }
        final @Nonnull Triplet other = (Triplet) object;
        return super.equals(object) && Objects.equals(this.element2, other.element2);
    }
    
    @Pure
    @Override
    public int hashCode() {
        return 83 * super.hashCode() + Objects.hashCode(element2);
    }
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return super.toString() + ", " + element2;
    }
    
}
