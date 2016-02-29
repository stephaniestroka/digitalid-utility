package net.digitalid.utility.generator.information.filter;

import javax.annotation.Nonnull;

/**
 *
 */
public interface KeyExtractor<K, I> {
    
    public @Nonnull K getKey(I input);
    
}
