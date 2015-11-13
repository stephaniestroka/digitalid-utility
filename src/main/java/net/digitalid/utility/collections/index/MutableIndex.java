package net.digitalid.utility.collections.index;

import javax.annotation.Nonnull;
import net.digitalid.utility.annotations.state.Mutable;
import net.digitalid.utility.annotations.state.Pure;

/**
 * This class represents a mutable index.
 */
@Mutable
public final class MutableIndex {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Stores the value of this index.
     */
    private int value;
    
    /**
     * Returns the value of this index.
     * 
     * @return the value of this index.
     */
    @Pure
    public int getValue() {
        return value;
    }
    
    /**
     * Increments the value of this index by one.
     */
    public void incrementValue() {
        value++;
    }
    
    /**
     * Returns the current value of this index and then increments it by one.
     * 
     * @return the current value of this index.
     */
    @Pure
    public int getAndIncrementValue() {
        return value++;
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new index with the given value.
     * 
     * @param value the value of the new index.
     */
    private MutableIndex(int value) {
        this.value = value;
    }
    
    /**
     * Creates a new index with the given value.
     * 
     * @param value the value of the new index.
     * 
     * @return a new index with the given value.
     */
    @Pure
    public static @Nonnull MutableIndex get(int value) {
        return new MutableIndex(value);
    }
    
    /**
     * Creates a new index with the value zero.
     * 
     * @return a new index with the value zero.
     */
    @Pure
    public static @Nonnull MutableIndex get() {
        return new MutableIndex(0);
    }
    
}
