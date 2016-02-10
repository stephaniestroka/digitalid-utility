package net.digitalid.net.root;

/**
 * TODO: The interface is currently used in SQLValues, where the list elements are copied to a new SQLValues object. Maybe there is a more efficient way to implement the SQLCollectionConverter. If so, we can omit the Copyable interface.
 * Classes that implement this interface must implement copy.
 */
public interface Copyable<T> {
    
    public T copy();
    
}
