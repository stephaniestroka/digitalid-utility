package net.digitalid.utility.system.converter;

import net.digitalid.utility.system.converter.exceptions.StoringException;

public interface Serializer<C> {
    
    public C store(Object object, Format<C> format, String fieldName, String clazzName) throws StoringException;
    
}