package net.digitalid.utility.system.converter;

import java.lang.reflect.Field;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.digitalid.utility.system.converter.exceptions.SerializerException;
import net.digitalid.utility.system.converter.exceptions.StoringException;
import net.digitalid.utility.collections.annotations.elements.NonNullableElements;
import net.digitalid.utility.collections.freezable.FreezableArrayList;

public class ListConverter {
    
    public static @Nonnull <C> C convert(@Nonnull Object object, Format<C> format, @Nonnull @NonNullableElements Field field, @Nullable String parentName) throws StoringException {
    
        C serializedObject;
       
        String className = object.getClass().getSimpleName();
        Object fieldValue;
        try {
            field.setAccessible(true);
            fieldValue = field.get(object);
        } catch (IllegalAccessException e) {
            throw StoringException.get(object, "The field '" + field.getName() + "' cannot be accessed.");
        }

        List<?> fieldValueAsList = (List<?>) fieldValue;
        FreezableArrayList<C> listOfConvertedElements = FreezableArrayList.get();
        for (Object element : fieldValueAsList) {
            Serializer<C> fieldSerializer;
            try {
                fieldSerializer = format.getSerializer(element.getClass());
            } catch (SerializerException e) {
                throw StoringException.get(object, e.getMessage());
            }
            C serializedField = fieldSerializer.store(fieldValue, format, field.getName(), className);
            listOfConvertedElements.add(serializedField);
        }
        Serializer<C> listSerializer;
        try {
            listSerializer = format.getSerializer(net.digitalid.utility.system.converter.List.class);
        } catch (SerializerException e) {
            throw StoringException.get(object, "The format '" + format.getClass() + "' does not support the list type");
        }
        serializedObject = listSerializer.store(listOfConvertedElements.freeze(), format, className, parentName); 
        return serializedObject;
    }
    
}
