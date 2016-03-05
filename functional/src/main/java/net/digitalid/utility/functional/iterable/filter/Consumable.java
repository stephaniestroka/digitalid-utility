package net.digitalid.utility.functional.iterable.filter;

import javax.annotation.Nullable;

import net.digitalid.utility.validation.annotations.method.Pure;

/**
 * Provides a wrapper for the element of the iterable that contains information about whether an element was 
 * returned by the iterator already, or in other words, whether it was consumed.
 */
class Consumable<E> {
    
    /* -------------------------------------------------- Element -------------------------------------------------- */
    
    /**
     * The current element of the iterable.
     */
    public final @Nullable E element;
    
    /* -------------------------------------------------- Consume Indicator -------------------------------------------------- */
    
    /**
     * A flag indicating whether the element was consumed yet.
     */
    private boolean consumed = false;
    
    /**
     * Returns true if the element was consumed.
     */
    @Pure
    public boolean isConsumed() {
        return consumed;
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new consumable element.
     */
    Consumable(@Nullable E element) {
        this.element = element;
    }
    
    /* -------------------------------------------------- Comsume -------------------------------------------------- */
    
    /**
     * Returns the element and flips the {@link Consumable#consumed} flag.
     * @return
     */
    public @Nullable E consume() {
        consumed = true;
        return element;
    }
    
    /* -------------------------------------------------- Initial Consumable -------------------------------------------------- */
    
    /**
     * The special consumable indicates that we have not yet retrieved an element from the iterator. 
     * We use it so that we can initialize @Nonnull fields with it instead of assigning null.
     * Thus, we can spare the null-checks whenever we call isConsumed() on a consumable object.
     */
    public static class InitialConsumable<E> extends Consumable<E> {
    
        /**
         * Creates a new consumable element.
         */
        public InitialConsumable() {
            super(null);
        }
    
        /**
         * The initial state is always marked as consumed, because we never actually want to consume it.
         */
        @Override
        public boolean isConsumed() {
            return true; 
        }
        
    }
    
}
