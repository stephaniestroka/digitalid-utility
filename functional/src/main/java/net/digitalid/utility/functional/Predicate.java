package net.digitalid.utility.functional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Description.
 */
public abstract class Predicate<T> {
    
     public Predicate<T> and(@Nonnull final Predicate<T> predicate) {
        final @Nonnull Predicate<T> self = this;
        return new Predicate<T>() {
            
            @Override public boolean apply(@Nullable T object) {
                return self.apply(object) && predicate.apply(object);
            }
            
        };
    }   
    
    public Predicate<T> or(@Nonnull final Predicate<T> predicate) {
        final @Nonnull Predicate<T> self = this;
        return new Predicate<T>() {
            
            @Override public boolean apply(@Nullable T object) {
                return self.apply(object) || predicate.apply(object);
            }
            
        };
    }
    
    public abstract boolean apply(@Nullable T object);
    
}
