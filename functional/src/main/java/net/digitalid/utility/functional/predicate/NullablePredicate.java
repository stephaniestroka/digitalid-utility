package net.digitalid.utility.functional.predicate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * A predicate that can be applied on nullable objects.
 */
@Stateless
public abstract class NullablePredicate<T, A> extends NonNullPredicate<T, A> {
    
    /* -------------------------------------------------- Combinations of Predicates -------------------------------------------------- */
    
    /**
     * Combines two predicates using an AND operator.
     * Since the first predicate is a nullable predicate, the second must also accept null values and the returned predicate is another nullable predicate.
     */
    @Pure
     public NullablePredicate<T, A> and(@Nonnull final NullablePredicate<T, A> predicate) {
        final @Nonnull NullablePredicate<T, A> self = this;
        return new NullablePredicate<T, A>() {
            
            @Override public boolean apply(@Nullable T object, @Nullable A additionalInformation) {
                return self.apply(object, additionalInformation) && predicate.apply(object, additionalInformation);
            }
            
        };
    }
    
    /**
     * Combines two predicates using an OR operator.
     */
    @Pure
    public NullablePredicate<T, A> or(@Nonnull final NullablePredicate<T, A> predicate) {
        final @Nonnull NullablePredicate<T, A> self = this;
        return new NullablePredicate<T, A>() {
            
            @Override public boolean apply(@Nullable T object, @Nullable A additionalInformation) {
                return self.apply(object, additionalInformation) || predicate.apply(object, additionalInformation);
            }
            
        };
    }
    
    /**
     * Combines two predicates using an OR operator.
     */
    @Pure
    public NullablePredicate<T, A> negate() {
        final @Nonnull NullablePredicate<T, A> self = this;
        return new NullablePredicate<T, A>() {
            
            @Override public boolean apply(@Nullable T object, @Nullable A additionalInformation) {
                return !self.apply(object, additionalInformation);
            }
            
        };
    }
    
    /* -------------------------------------------------- Apply -------------------------------------------------- */
    
    /**
     * Applies the predicate on a given object and returns the boolean result.
     */
    @Pure
    @Override
    public abstract boolean apply(@Nullable T object, @Nullable A additionalInformation);
    
}
