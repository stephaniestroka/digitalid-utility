package net.digitalid.utility.system.converter;

import java.lang.reflect.Field;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.digitalid.utility.system.converter.exceptions.FieldSerializerException;
import net.digitalid.utility.system.converter.exceptions.SerializerException;
import net.digitalid.utility.system.converter.exceptions.StoringException;
import net.digitalid.utility.collections.annotations.elements.NonNullableElements;
import net.digitalid.utility.collections.freezable.FreezableArray;
import net.digitalid.utility.collections.readonly.ReadOnlyList;

public class TupleConverter {
    
    public static @Nonnull <C> C convert(@Nonnull Object object, Format<C> format, @Nonnull @NonNullableElements ReadOnlyList<Field> fields, @Nullable String parentName) throws StoringException {
        C serializedObject;
       
        String className = object.getClass().getSimpleName();
        @Nonnull FreezableArray<C> elements = FreezableArray.get(fields.size());
        int i = 0;
        for (Field field : fields) {
            Object fieldValue;
            try {
                field.setAccessible(true);
                fieldValue = field.get(object);
            } catch (IllegalAccessException e) {
                throw StoringException.get(object, "The field '" + field.getName() + "' cannot be accessed.");
            }

            Serializer<C> fieldSerializer;
            try {
                fieldSerializer = Converter.getFieldSerializer(field, format);
            } catch (FieldSerializerException e) {
                throw StoringException.get(object, e.getMessage());
            }
            C serializedField = fieldSerializer.store(fieldValue, format, field.getName(), className);
            elements.set(i, serializedField);
            i++;
        }
        Serializer<C> tupleSerializer;
        try {
            tupleSerializer = format.getSerializer(Tuple.class);
        } catch (SerializerException e) {
            throw StoringException.get(object, "The format '" + format.getClass() + "' does not support the tuple type");
        }
        serializedObject = tupleSerializer.store(elements, format, className, parentName); 
        return serializedObject;
    }
    
}
