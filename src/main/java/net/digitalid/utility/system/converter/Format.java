package net.digitalid.utility.system.converter;

import net.digitalid.utility.system.converter.exceptions.SerializerException;

/**
 * 
 * @param <C> The type of content, which is used to restore the original object. For example, an SQLResultSet or an XDF block.
 */
public interface Format<C> {
    
    public <T> Serializer<C> getSerializer(Class<T> clazz) throws SerializerException;

    public <T> Deserializer<C, T> getDeserializer(C encodedForm);
}