package net.digitalid.utility.rootclass;

/**
 * TODO: The interface is currently used in SQLValues, where the list elements are copied to a new SQLValues object. Maybe there is a more efficient way to implement the SQLCollectionConverter. If so, we can omit the Copyable interface.
 * Classes that implement this interface must implement copy.
 */
// TODO: Why not just use clone() and remove this interface?
// TODO: And why is such an interface declared in this project?
@Deprecated
public interface Copyable<T> {
    
    public T copy();
    
}
