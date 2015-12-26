package net.digitalid.utility.system.converter;

public interface Deserializer<C, T> {
    
    public T restore(C encodedForm);
    
}