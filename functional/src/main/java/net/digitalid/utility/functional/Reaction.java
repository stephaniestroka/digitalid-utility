package net.digitalid.utility.functional;

/**
 *
 */
public interface Reaction<T> {
    
    public boolean skipFunctions();
    
    public T react();
}
