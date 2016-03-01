package net.digitalid.utility.tuples;

import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.reference.Capturable;

/**
 * This class models a pair.
 * 
 * @see Triplet
 */
public class Pair<E0, E1> {
    
    /* -------------------------------------------------- Fields -------------------------------------------------- */
    
    /**
     * Stores the first element of this tuple.
     */
    private @Nullable E0 element0;
    
    /**
     * Stores the second element of this tuple.
     */
    private @Nullable E1 element1;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates a new pair with the given elements.
     * 
     * @param element0 the first element of this tuple.
     * @param element1 the second element of this tuple.
     */
    protected Pair(@Nullable E0 element0, @Nullable E1 element1) {
        this.element0 = element0;
        this.element1 = element1;
    }
    
    /**
     * Creates a new pair with the given elements.
     * 
     * @param element0 the first element of this tuple.
     * @param element1 the second element of this tuple.
     * 
     * @return a new pair with the given elements.
     */
    @Pure
    public static @Capturable @Nonnull <E0, E1> Pair<E0, E1> get(@Nullable E0 element0, @Nullable E1 element1) {
        return new Pair<>(element0, element1);
    }
    
    /**
     * Creates a new pair from the given pair.
     * 
     * @param pair the pair containing the elements.
     * 
     * @return a new pair from the given pair.
     */
    @Pure
    public static @Capturable @Nonnull <E0, E1> Pair<E0, E1> copy(@Nonnull @NullableElements Pair<E0, E1> pair) {
        return get(pair.getNullableElement0(), pair.getNullableElement1());
    }
    
    /**
     * Creates a new pair from the given pair.
     * 
     * @param pair the pair containing the elements.
     * 
     * @return a new pair from the given pair.
     */
    @Pure
    public static @Capturable @Nullable <E0, E1> Pair<E0, E1> copyNullable(@Nullable @NullableElements Pair<E0, E1> pair) {
        return pair == null ? null : copy(pair);
    }
    
    /* -------------------------------------------------- Getters -------------------------------------------------- */
    
    @Pure
    public final @Nullable E0 getNullableElement0() {
        return element0;
    }
    
    @Pure
    public final @Nonnull E0 getNonNullableElement0() {
        Require.that(element0 != null).orThrow("The element is not null.");
        
        return element0;
    }
    
    @Pure
    public final @Nullable E1 getNullableElement1() {
        return element1;
    }
    
    @Pure
    public final @Nonnull E1 getNonNullableElement1() {
        Require.that(element1 != null).orThrow("The element is not null.");
        
        return element1;
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    @SuppressWarnings("rawtypes")
    public boolean equals(@Nullable Object object) {
        if (object == this) { return true; }
        if (object == null) { return false; }
        if (!(object instanceof Pair)) { return object.equals(this); }
        final @Nonnull Pair other = (Pair) object;
        return Objects.equals(this.element0, other.element0) && Objects.equals(this.element1, other.element1);
    }
    
    @Pure
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(element0);
        hash = 83 * hash + Objects.hashCode(element1);
        return hash;
    }
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return element0 + ", " + element1;
    }
    
}
