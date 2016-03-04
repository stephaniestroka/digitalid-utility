package net.digitalid.utility.functional.iterable.filter.predicate;

import javax.annotation.Nonnull;

/**
 * A predicate that can only be applied on non-nullable objects.
 */
public abstract class NonNullPredicate<T> {
    
    /**
     * Combines two predicates using an AND operator.
     */
     public NonNullPredicate<T> and(@Nonnull final NonNullPredicate<T> predicate) {
        final @Nonnull NonNullPredicate<T> self = this;
        return new NonNullPredicate<T>() {
            
            @Override
            public boolean apply(@Nonnull T object) {
                return self.apply(object) && predicate.apply(object);
            }
            
        };
    }
    
    /**
     * Combines two predicates using an OR operator.
     */
    public NonNullPredicate<T> or(@Nonnull final NonNullPredicate<T> predicate) {
        final @Nonnull NonNullPredicate<T> self = this;
        return new NonNullPredicate<T>() {
            
            @Override
            public boolean apply(@Nonnull T object) {
                return self.apply(object) || predicate.apply(object);
            }
            
        };
    }
    
    /**
     * Combines two predicates using an OR operator.
     */
    public NonNullPredicate<T> negate() {
        final @Nonnull NonNullPredicate<T> self = this;
        return new NonNullPredicate<T>() {
            
            @Override public boolean apply(@Nonnull T object) {
                return !self.apply(object);
            }
            
        };
    }
 
    /* -------------------------------------------------- Apply -------------------------------------------------- */
    
    /**
     * Applies the predicate on a given object and returns the boolean result.
     */
    public abstract boolean apply(@Nonnull T object);
    
}
