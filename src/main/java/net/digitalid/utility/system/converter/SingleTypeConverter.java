package net.digitalid.utility.system.converter;

import java.lang.reflect.Field;
import javax.annotation.Nonnull;
import net.digitalid.utility.system.converter.exceptions.FieldSerializerException;
import net.digitalid.utility.system.converter.exceptions.StoringException;
import net.digitalid.utility.collections.annotations.elements.NonNullableElements;

public class SingleTypeConverter {
    
    public static @Nonnull
    <C> C convert(@Nonnull Object object, Format<C> format, @Nonnull @NonNullableElements Field field, @Nonnull String parentName) throws StoringException {
    
        C serializedObject;
       
        String className = object.getClass().getSimpleName();
        Object fieldValue;
        try {
            field.setAccessible(true);
            fieldValue = field.get(object);
        } catch (IllegalAccessException e) {
            throw StoringException.get(object, "The field '" + field.getName() + "' cannot be accessed.");
        }

        Serializer<C> serializer;
        try {
            serializer = Converter.getFieldSerializer(field, format);
        } catch (FieldSerializerException e) {
            throw StoringException.get(object, e.getMessage(), e);
        }
        return serializer.store(fieldValue, format, field.getName(), parentName);   
    }
    
}
