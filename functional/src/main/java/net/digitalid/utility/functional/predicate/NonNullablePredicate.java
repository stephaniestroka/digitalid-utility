package net.digitalid.utility.functional.predicate;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * A predicate that can only be applied on non-nullable objects.
 */
@Stateless
public abstract class NonNullablePredicate<T> implements Predicate<T> {
    
    /**
     * Combines two predicates using an AND operator.
     * Since the first predicate is non-null, the second predicate must also be non-null.
     */
    @Pure
    public NonNullablePredicate<T> and(@Nonnull final NonNullablePredicate<T> predicate) {
        final @Nonnull NonNullablePredicate<T> self = this;
        return new NonNullablePredicate<T>() {
            
            @Override
            public boolean apply(@Nonnull T object) {
                return self.apply(object) && predicate.apply(object);
            }
            
        };
    }
    
    /**
     * Combines two predicates using an OR operator.
     */
    @Pure
    public NonNullablePredicate<T> or(@Nonnull final NonNullablePredicate<T> predicate) {
        final @Nonnull NonNullablePredicate<T> self = this;
        return new NonNullablePredicate<T>() {
            
            @Override
            public boolean apply(@Nonnull T object) {
                return self.apply(object) || predicate.apply(object);
            }
            
        };
    }
    
    /**
     * Combines two predicates using an OR operator.
     */
    @Pure
    public NonNullablePredicate<T> negate() {
        final @Nonnull NonNullablePredicate<T> self = this;
        return new NonNullablePredicate<T>() {
            
            @Override public boolean apply(@Nonnull T object) {
                return !self.apply(object);
            }
            
        };
    }
 
    /* -------------------------------------------------- Apply -------------------------------------------------- */
    
    /**
     * Applies the predicate on a given non-null object and returns the boolean result.
     */
    @Pure
    @Override
    public abstract boolean apply(@Nonnull T object);
    
}
