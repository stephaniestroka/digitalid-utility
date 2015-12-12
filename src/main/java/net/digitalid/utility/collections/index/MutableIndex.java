package net.digitalid.utility.collections.index;

import javax.annotation.Nonnull;
import net.digitalid.utility.annotations.math.NonNegative;
import net.digitalid.utility.annotations.state.Mutable;
import net.digitalid.utility.annotations.state.Pure;

/**
 * This class represents a mutable index.
 */
@Mutable
@Deprecated // TODO: Might no longer be necessary if the ValueCollector and SelectionResult implement their own internal indexes.
public final class MutableIndex {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Stores the value of this index.
     */
    private @NonNegative int value;
    
    /**
     * Returns the value of this index.
     * 
     * @return the value of this index.
     */
    @Pure
    public @NonNegative int getValue() {
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
    public @NonNegative int getAndIncrementValue() {
        return value++;
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new index with the given value.
     * 
     * @param value the value of the new index.
     */
    private MutableIndex(@NonNegative int value) {
        assert value >= 0 : "The value is non-negative.";
        
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
    public static @Nonnull MutableIndex get(@NonNegative int value) {
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
