package net.digitalid.utility.system.converter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.TypeVariable;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.digitalid.utility.system.converter.annotations.Ignore;
import net.digitalid.utility.system.converter.exceptions.FieldSerializerException;
import net.digitalid.utility.system.converter.exceptions.SerializerException;
import net.digitalid.utility.system.converter.exceptions.StoringException;
import net.digitalid.utility.system.converter.exceptions.StructureException;
import net.digitalid.utility.collections.annotations.elements.NonNullableElements;
import net.digitalid.utility.collections.freezable.FreezableArrayList;
import net.digitalid.utility.collections.readonly.ReadOnlyList;

/**
 * 
 * @param <C> The type of content, which is used to restore the original object. For example, an SQLResultSet or an XDF block.
 */
public abstract class Converter<C> {
    
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface SerializedWith {
        Class<? extends Serializer<?>> serializer();
    }
    
    protected static <C> Serializer<C> getFieldSerializer(@Nonnull Field field, @Nonnull Format<C> format) throws FieldSerializerException {
        boolean isSerializedWithDeclared = field.isAnnotationPresent(SerializedWith.class);
        Serializer<C> fieldSerializer;
        if (isSerializedWithDeclared) {
            SerializedWith serializedWith = field.getAnnotation(SerializedWith.class);
            Class<? extends Serializer<?>> serializerClass = serializedWith.serializer();
            
            try {
                fieldSerializer = (Serializer<C>) serializerClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw FieldSerializerException.get("The serializer '" + serializerClass + "' for field '" + field.getName() + "' cannot be instantiated.", e);
            }
            
        } else {
            Class<?> fieldType = field.getType();
            try {
                fieldSerializer = format.getSerializer(fieldType);
            } catch (SerializerException e) {
                throw FieldSerializerException.get("The serializer for field '" + field.getName() + "', type '" + field.getType() + "' cannot be instantiated.", e);
            }
        }
        return fieldSerializer;
    }
    
    private static class Structure {
        
        private enum Type {
            TUPLE, LIST, SINGLETYPE;
        }
        
        final Type type;
        final ReadOnlyList<Field> fields;
        
        Structure(Type type, ReadOnlyList fields) {
            this.type = type;
            this.fields = fields;
        }
    }
    
    public static @Nonnull <C, T> C storeNonNullable(@Nonnull T object, @Nonnull Format<C> format, String parentName) throws StoringException {
        return storeNullable(object, object.getClass(), format, parentName);
    }

    public static @Nonnull <C, T> C storeNullable(@Nullable T object, @Nonnull Class<T> type, @Nonnull Format<C> format, String parentName) throws StoringException {
        // TODO: remove null initialization once the object was serialized
        C serializedObject = null;
        
        Structure structure = null;
        try {
            structure = inferStructure(type);
        } catch (StructureException e) {
            throw StoringException.get(object, e.getMessage(), e);
        }

        switch (structure.type) {
            case TUPLE:
                serializedObject = TupleConverter.convert(object, format, structure.fields, parentName);
                break;
            case LIST:
                serializedObject = ListConverter.convert(object, format, structure.fields.getNonNullable(0), parentName);
                break;
            case SINGLETYPE:
                serializedObject = SingleTypeConverter.convert(object, format, structure.fields.getNonNullable(0), parentName);
                break;
            default:
                throw new RuntimeException("Structure '" + structure + "' is unknown. Known types are: '" + Structure.Type.values() + "'.");
        }
        return serializedObject;
    }
    
    private static TypeVariable<? extends Constructor<?>>[] getTypeParameters(Class<?> clazz) throws StructureException {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        
        if (constructors.length > 1) {
            throw StructureException.get("The class has multiple constructors.");
        } else if (constructors.length == 0) {
            throw StructureException.get("The class does not have any declared constructors.");
        }
        
        Constructor<?> constructor = constructors[0];
        TypeVariable<? extends Constructor<?>>[] typeParameters = constructor.getTypeParameters();
        return typeParameters;
    }
    
     // Cases:
    // 1. no type parameters are present, thus the constructor does not hold any fields that can be stored. We throw an exception at that point.
    // 2. one type parameter is present, thus the type of the object is or can be converted to a primitive or structural type. The parameter may not be marked as a 'helper'.
    // 3. multiple type parameters are present, but only one of them is not marked as 'helper' type. The 'helper' (e.g. entity), is injected from outside, e.g. from the class that holds an object of this type. The type parameter that is not marked as a 'helper' is eventually converted into a primitive or structural type
    // 4. multiple type parameters are present and more than one are not marked as 'helper' types. The types not marked as helpers form a tuple.
    private static Structure inferStructure(Class<?> clazz) throws StructureException {
        
        TypeVariable<? extends Constructor<?>>[] typeParameters = getTypeParameters(clazz);
        if (typeParameters.length == 0) {
            throw StructureException.get("Cannot convert objects without values (empty constructor).");
        }
        @Nonnull @NonNullableElements ReadOnlyList<Field> fields = getStorableFields(clazz, typeParameters);
        Structure.Type structureType;
        if (fields.size() == 1) {
            Field field = fields.getNonNullable(0);
            Class<?> fieldType = field.getType();
            // TODO: checks should move outside, since they can get more complicated.
            if (List.class.isAssignableFrom(fieldType) || fieldType.isArray()) {
                structureType = Structure.Type.LIST;
            } else {
                structureType = Structure.Type.SINGLETYPE;
            }
        } else {
            structureType = Structure.Type.TUPLE;
        }
        Structure structure = new Structure(structureType, fields);
        return structure;
    }

    private static ReadOnlyList<Field> getStorableFields(Class<?> clazz, TypeVariable<? extends Constructor<?>>[] typeParameters) throws StructureException {
        FreezableArrayList<Field> fields = FreezableArrayList.get();
        for (TypeVariable<? extends Constructor<?>> typeParameter : typeParameters) {
            @Nonnull String name = typeParameter.getName();
            Field field;
            // TODO: what if the constructor parameter name is not equal to the types field name?
            try {
                field = clazz.getField(name);
            } catch (NoSuchFieldException e) {
                throw StructureException.get("The class does not have a field '" + name + "'.");
            }
            
            // TODO: discuss which annotations to use (e.g. @Ignore, @Helper or @RequiredForReconstruction)
            if (field.isAnnotationPresent(Ignore.class)) {
                continue;
            }
            fields.add(field);
        }
        return fields.freeze();
    }

}
