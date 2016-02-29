package net.digitalid.utility.generator.information.filter;

import javax.annotation.Nonnull;

/**
 *
 */
public interface FilterCondition<T> {
    
    public boolean filter(@Nonnull T type);
    
    static class AcceptAll<T> implements FilterCondition<T> {
        
        @Override public boolean filter(@Nonnull T type) {
            return true;
        }
        
        public static <T> AcceptAll<T> get() {
            return new AcceptAll<>();
        }
        
    }
    
}
