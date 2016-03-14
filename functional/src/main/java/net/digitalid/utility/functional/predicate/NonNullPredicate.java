package net.digitalid.utility.functional.predicate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * A predicate that can only be applied on non-nullable objects.
 */
@Stateless
public abstract class NonNullPredicate<T, A> implements Predicate<T, A> {
    
    /**
     * Combines two predicates using an AND operator.
     * Since the first predicate is non-null, the second predicate must also be non-null.
     */
    @Pure
    public NonNullPredicate<T, A> and(@Nonnull final NonNullPredicate<T, A> predicate) {
        final @Nonnull NonNullPredicate<T, A> self = this;
        return new NonNullPredicate<T, A>() {
            
            @Override
            public boolean apply(@Nonnull T object, @Nullable A additionalInformation) {
                return self.apply(object, additionalInformation) && predicate.apply(object, additionalInformation);
            }
            
        };
    }
    
    /**
     * Combines two predicates using an OR operator.
     */
    @Pure
    public NonNullPredicate<T, A> or(@Nonnull final NonNullPredicate<T, A> predicate) {
        final @Nonnull NonNullPredicate<T, A> self = this;
        return new NonNullPredicate<T, A>() {
            
            @Override
            public boolean apply(@Nonnull T object, @Nullable A additionalInformation) {
                return self.apply(object, additionalInformation) || predicate.apply(object, additionalInformation);
            }
            
        };
    }
    
    /**
     * Combines two predicates using an OR operator.
     */
    @Pure
    public NonNullPredicate<T, A> negate() {
        final @Nonnull NonNullPredicate<T, A> self = this;
        return new NonNullPredicate<T, A>() {
            
            @Override public boolean apply(@Nonnull T object, @Nullable A additionalInformation) {
                return !self.apply(object, additionalInformation);
            }
            
        };
    }
 
    /* -------------------------------------------------- Apply -------------------------------------------------- */
    
    /**
     * Applies the predicate on a given non-null object and returns the boolean result.
     */
    @Pure
    @Override
    public abstract boolean apply(@Nonnull T object, @Nullable A additionalInformation);
    
}
