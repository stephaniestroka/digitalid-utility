package net.digitalid.utility.system.converter;

public interface TypeToTypeMapper<T, F> {
    
    T mapTo(F from);
    
    F mapFrom(T to);
    
    Class<T> getMapType();
    
}
